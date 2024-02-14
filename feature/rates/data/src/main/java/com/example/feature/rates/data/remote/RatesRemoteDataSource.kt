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

package com.example.feature.rates.data.remote

import arrow.core.raise.either
import com.example.core.data.remote.sources.RemoteDataSource
import com.example.core.data.remote.handlers.NetworkHandler
import com.example.feature.rates.data.remote.services.APIRatesService
import com.example.currency.di.qualifiers.Network
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.joda.money.CurrencyUnit
import retrofit2.Retrofit
import java.util.Date
import javax.inject.Inject


class RatesRemoteDataSource @Inject constructor(
    retrofit: Retrofit,
    networkHandler: NetworkHandler,
    @Network private val networkDispatcher: CoroutineDispatcher
): RemoteDataSource<APIRatesService>(retrofit.create(APIRatesService::class.java), networkHandler){

    suspend fun getRates(
        date: Date,
        currencyUnit: CurrencyUnit,
    ) = either {
        val service = getAvailableService().bind()
        val rates = withContext(networkDispatcher) {
            service.getRates(base = currencyUnit.code)
        }
        processRemoteResponse(rates).bind()
    }
}