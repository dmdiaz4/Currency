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

package com.dmdiaz.currency.core.data.rates.remote.datasource

import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.retrofit.adapter.either.networkhandling.HttpError
import arrow.retrofit.adapter.either.networkhandling.IOError
import arrow.retrofit.adapter.either.networkhandling.UnexpectedCallError
import com.dmdiaz.currency.core.data.rates.remote.network.APIRatesService
import com.dmdiaz.currency.core.data.util.NetworkMonitor
import com.dmdiaz.currency.core.domain.common.models.Failure
import com.dmdiaz.currency.libs.util.di.qualifiers.Dispatcher
import com.dmdiaz.currency.libs.util.di.qualifiers.Dispatchers.IO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.joda.money.CurrencyUnit
import java.util.Date
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class RatesRemoteDataSourceImpl @Inject constructor(
    private val service: APIRatesService,
    private val networkMonitor: NetworkMonitor,
    @Dispatcher(IO) private val networkDispatcher: CoroutineDispatcher
) : RatesRemoteDataSource {

    override fun getRates(
        date: Date,
        currencyUnit: CurrencyUnit,
    ) = networkMonitor.isOnline.flatMapLatest { online ->
        flow {
            emit(
                either {
                    ensure(online) { Failure.NetworkUnavailable }
                    withContext(networkDispatcher) {
                        service.getRates(base = currencyUnit.code)
                    }
                        .mapLeft { error ->
                            when (error) {
                                is HttpError -> Failure.NetworkError(error.code)
                                is IOError -> Failure.NetworkUnavailable
                                is UnexpectedCallError -> Failure.UnknownError(error.cause)
                            }
                        }.bind()
                }
            )
        }
    }
}