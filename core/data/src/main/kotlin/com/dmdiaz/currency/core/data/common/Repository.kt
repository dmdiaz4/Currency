/*
 * MIT License
 *
 * Copyright (c) 2026 David Diaz
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

package com.dmdiaz.currency.core.data.common

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.raise.catch
import arrow.core.raise.either
import arrow.core.right
import com.dmdiaz.currency.core.data.common.Repository.FetchStrategy.NoFetch
import com.dmdiaz.currency.core.data.common.Repository.FetchStrategy.RemoteFetch
import com.dmdiaz.currency.core.data.common.Repository.FetchStrategy.RemoteFetch.BlockingFetch
import com.dmdiaz.currency.core.domain.common.models.CommonError
import com.dmdiaz.currency.core.domain.common.models.UnknownError
import com.dmdiaz.currency.libs.util.extensions.emitEither
import com.dmdiaz.currency.libs.util.extensions.flatMapRightLatest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

@Suppress("unused", "MemberVisibilityCanBePrivate", "RedundantAsync")
abstract class Repository(
    protected val defaultDispatcher: CoroutineDispatcher
) {

    sealed interface FetchStrategy {
        data object NoFetch : FetchStrategy
        sealed interface RemoteFetch : FetchStrategy{
            data object BackgroundFetch: RemoteFetch
            data object BlockingFetch : RemoteFetch
        }
    }



    fun <LOCAL> getLocal(
        localFlow: suspend FlowCollector<Either<CommonError, LOCAL>>.() -> Unit,
    ) = get<LOCAL, Nothing>(
        localFlow = localFlow,
        fetchPolicy = { _, _ -> NoFetch },
        fetchFlow = { _, _ -> },
        saveFetchSuccess = {_, _, _ ->}
    )


    fun <LOCAL, REMOTE> get(
        localFlow: suspend FlowCollector<Either<CommonError, LOCAL>>.() -> Unit,
        fetchPolicy: suspend Raise<CommonError>.(previousFetchPolicy: FetchStrategy?, local: LOCAL) -> FetchStrategy,
        fetchFlow: suspend FlowCollector<Either<CommonError, REMOTE>>.(local: LOCAL, fetchMode: RemoteFetch) -> Unit,
        saveFetchSuccess: suspend Raise<CommonError>.(local: LOCAL, fetchMode: RemoteFetch, remote: REMOTE) -> Unit,
    ): Flow<Either<CommonError, LOCAL>> {
        var previousFetchPolicy: FetchStrategy? = null
        return flow(localFlow).flatMapRightLatest { local ->
            flow<Either<CommonError, LOCAL>> {
                 either {
                    val policy = fetchPolicy(previousFetchPolicy, local)
                    previousFetchPolicy = policy
                    if (policy !is BlockingFetch) emit(local.right())
                    if (policy is RemoteFetch) {
                        flow { fetchFlow(local, policy) }.collect{ remote ->
                            emitEither{
                                saveFetchSuccess(local, policy, remote.bind())
                                local
                            }
                        }
                    }
                }.onLeft {
                    emit(it.left())
                 }
            }
        }
            .catch { emit(UnknownError(it).left()) }
            .flowOn(defaultDispatcher)
            .cancellable()
            .conflate()
    }



    suspend fun <T> crud(
        operation: suspend Raise<CommonError>.() -> T,
    ) = withContext(defaultDispatcher) {
        either<CommonError, Unit> {
            catch(
                block = {
                    operation()
                },
                catch = {
                    raise(UnknownError(it))
                }
            )
        }
    }

}