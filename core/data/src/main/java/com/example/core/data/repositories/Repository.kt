/*
 * MIT License
 *
 * Copyright (c) 2024 David Diaz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.core.data.repositories

import arrow.core.Either
import arrow.core.computations.EitherEffect
import arrow.core.raise.either
import arrow.core.flatten
import arrow.core.left
import arrow.core.raise.Raise
import com.example.core.extensions.tryOrFailure
import com.example.core.models.Failure
import com.example.core.models.Failure.UnknownError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("unused", "MemberVisibilityCanBePrivate", "RedundantAsync")
abstract class Repository(
    protected val defaultDispatcher: CoroutineDispatcher
) {

    fun <FETCH, DOMAIN> get(
        domainFlow: suspend FlowCollector<Either<Failure, DOMAIN>>.() -> Unit,
        shouldFetchFlow: suspend FlowCollector<Either<Failure, Boolean>>.() -> Unit,
        fetchFlow: suspend FlowCollector<Either<Failure, FETCH>>.() -> Unit,
        saveFetchSuccess: suspend Raise<Failure>.(FETCH) -> Unit,
    ) = flow {

        val sharedFlow = MutableSharedFlow<Either<Failure, DOMAIN>>(replay = 1)

        val finishedSaving = MutableStateFlow(false)

        val domainFlowWrapped =
            flow(domainFlow).catch { emit(UnknownError(it).left()) }
        val shouldFetchFlowWrapped =
            flow(shouldFetchFlow).catch { emit(UnknownError(it).left()) }
        val fetchFlowWrapped =
            flow(fetchFlow).catch { emit(UnknownError(it).left()) }
        val saveFetchSuccessWrapped: suspend (FETCH) -> Either<Failure, Unit> = { success ->
            tryOrFailure {
                either<Failure, Unit> {
                    saveFetchSuccess(
                        success
                    )
                }
            }.flatten()
        }

        coroutineScope {
            launch {
                shouldFetchFlowWrapped
                    .catch { emit(UnknownError(it).left()) }
                    .collect { shouldFetch ->
                        shouldFetch.fold(
                            ifLeft = { sharedFlow.emit(it.left()) },
                            ifRight = { fetch ->
                                if (fetch) {
                                    fetchFlowWrapped
                                        .catch { emit(UnknownError(it).left()) }
                                        .map { it.map { success -> saveFetchSuccessWrapped(success) } }
                                        .map { it.flatten() }
                                        .collect { result ->
                                            result.fold(
                                                ifLeft = { sharedFlow.emit(it.left()) },
                                                ifRight = { finishedSaving.value = true }
                                            )
                                        }

                                } else {
                                    finishedSaving.value = true
                                }
                            }
                        )

                    }
            }
            launch {
                sharedFlow.collect {
                    finishedSaving.value = true
                }
            }
            launch {
                finishedSaving.collect { saved ->
                    if (saved) {
                        sharedFlow.emitAll(domainFlowWrapped)
                    }
                }
            }

            emitAll(sharedFlow)  // Emit all the flow updates
        }
    }
        .catch { emit(UnknownError(it).left()) }
        .flowOn(defaultDispatcher)
        .cancellable()
        .conflate()

    suspend fun <RESPONSE, DOMAIN> crud(
        operation: Raise<Failure>.() -> RESPONSE,
        saveOperationSuccess: Raise<Failure>.(RESPONSE) -> DOMAIN,
    ) = withContext(defaultDispatcher) {
        either<Failure, DOMAIN> {
            val success = tryOrFailure { either(operation) }.flatten().bind()
            tryOrFailure {
                either<Failure, DOMAIN> {
                    saveOperationSuccess(success)
                }
            }.flatten().bind()
        }
    }

}