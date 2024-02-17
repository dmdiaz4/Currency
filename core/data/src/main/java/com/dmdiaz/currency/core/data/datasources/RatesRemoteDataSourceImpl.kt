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

package com.dmdiaz.currency.core.data.datasources

import arrow.core.raise.either
import com.dmdiaz.currency.libs.util.di.qualifiers.Dispatcher
import com.dmdiaz.currency.libs.util.di.qualifiers.Dispatchers.IO
import com.dmdiaz.currency.core.network.handlers.NetworkHandler
import com.dmdiaz.currency.core.network.services.APIRatesService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.joda.money.CurrencyUnit
import java.util.Date
import javax.inject.Inject

class RatesRemoteDataSourceImpl @Inject constructor(
    service: APIRatesService,
    networkHandler: NetworkHandler,
    @Dispatcher(IO) private val networkDispatcher: CoroutineDispatcher
):RatesRemoteDataSource, RemoteDataSource<APIRatesService>(service, networkHandler){

    override suspend fun getRates(
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