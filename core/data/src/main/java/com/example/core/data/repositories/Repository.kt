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
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@Suppress("unused", "MemberVisibilityCanBePrivate", "RedundantAsync")
abstract class Repository(
    protected val defaultDispatcher: CoroutineDispatcher
) {

    fun <FETCH, DOMAIN> get(
        domainFlow: suspend FlowCollector<Either<Failure, DOMAIN>>.() -> Unit,
        fetchFlow: suspend FlowCollector<Either<Failure, FETCH>>.() -> Unit,
        saveFetchSuccess: suspend Raise<Failure>.(FETCH) -> Unit,
    ) = flow {

        val sharedFlow = MutableSharedFlow<Either<Failure, DOMAIN>>(replay = 1)


        val domainFlowWrapped =
            flow(domainFlow).catch { emit(UnknownError(it).left()) }
        val fetchFlowWrapped =
            flow(fetchFlow).catch { emit(UnknownError(it).left()) }
        val saveFetchSuccessWrapped: suspend (FETCH) -> Either<Failure, Unit> = { success ->
            tryOrFailure { either { saveFetchSuccess(success)} }.flatten()
        }

        coroutineScope {
            launch {
                sharedFlow.emitAll(domainFlowWrapped)
            }

            launch {
                fetchFlowWrapped.collect{ fetch ->
                    either {
                        val success = fetch.bind()
                        saveFetchSuccessWrapped(success).bind()
                    }.fold(
                        ifLeft = {sharedFlow.emit(it.left()) },
                        ifRight = {}
                    )
                }
            }

            emitAll(sharedFlow)  // Emit all the flow updates
        }
    }
        .catch { emit(UnknownError(it).left()) }
        .flowOn(defaultDispatcher)
        .cancellable()
        .conflate()

}