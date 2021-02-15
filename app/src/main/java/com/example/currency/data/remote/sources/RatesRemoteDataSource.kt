/*
 * MIT License
 *
 * Copyright (c) 2021 David Diaz
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

package com.example.currency.data.remote.sources

import arrow.core.left
import com.example.currency.data.remote.handlers.NetworkHandler
import com.example.currency.data.remote.services.APIRatesService
import com.example.currency.domain.models.Failure.NetworkUnavailable
import com.example.currency.domain.models.Failure.UnknownError
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.joda.money.CurrencyUnit


class RatesRemoteDataSource(
    private val service: APIRatesService,
    private val networkHandler: NetworkHandler,
    private val refreshIntervalMs: Long = 5000L
): RemoteDataSource(){
    fun getLatestRates(currencyUnit: CurrencyUnit, hasCache: Boolean) = flow {
        if (hasCache) delay(refreshIntervalMs)  // if cached don't immediately call service
        while (true){
            if (networkHandler.isNetworkAvailable()){
                val rates = service.getLatestRates(base = currencyUnit.code)
                emit(processRemoteResponse(rates))
            }else{
                emit(NetworkUnavailable.left())
            }
            delay(refreshIntervalMs)
        }
    }
        .catch { cause -> emit(UnknownError(cause).left()) }
        .cancellable()
        .buffer()
}