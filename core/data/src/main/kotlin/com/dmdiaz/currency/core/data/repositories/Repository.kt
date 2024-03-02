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

package com.dmdiaz.currency.core.data.repositories

import arrow.core.Either
import arrow.core.flatten
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.raise.either
import com.dmdiaz.currency.core.domain.models.Failure
import com.dmdiaz.currency.core.domain.models.Failure.UnknownError
import com.dmdiaz.currency.libs.util.extensions.filterNotNullRight
import com.dmdiaz.currency.libs.util.extensions.flatMapRightLatest
import com.dmdiaz.currency.libs.util.extensions.mapRight
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Suppress("unused", "MemberVisibilityCanBePrivate", "RedundantAsync")
abstract class Repository(
    protected val defaultDispatcher: CoroutineDispatcher
) {

    fun <FETCH, DOMAIN> get(
        domainFlow: suspend FlowCollector<Either<Failure, DOMAIN>>.() -> Unit,
        fetchFlow: suspend FlowCollector<Either<Failure, FETCH>>.(DOMAIN) -> Unit,
        saveFetchSuccess: suspend Raise<Failure>.(FETCH) -> Unit,
    ) = flow {

        val sharedFlow = MutableSharedFlow<Either<Failure, DOMAIN>>(replay = 1)

        coroutineScope {
            launch {
                sharedFlow.emitAll(flow(domainFlow))
            }

            launch {
                sharedFlow.flatMapRightLatest {
                    flow{fetchFlow.invoke(this,it)}.mapRight {
                        either { saveFetchSuccess.invoke(this, it) }
                    }.map { it.flatten() }
                }.collect {
                    val failure = it.leftOrNull()
                    if (failure != null) {
                        sharedFlow.emit(failure.left())
                    }
                }
            }

            emitAll(sharedFlow)  // Emit all the flow updates
        }
    }
        .catch { emit(UnknownError(it).left()) }
        .filterNotNullRight()
        .flowOn(defaultDispatcher)
        .cancellable()
        .conflate()
}