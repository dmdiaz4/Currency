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


import com.dmdiaz.currency.core.data.datasources.RatesLocalDataSource
import com.dmdiaz.currency.core.data.datasources.RatesRemoteDataSource
import com.dmdiaz.currency.core.data.mappers.toDBRates
import com.dmdiaz.currency.core.data.mappers.toRates
import com.dmdiaz.currency.core.database.converters.DateConverter
import com.dmdiaz.currency.core.domain.models.rates.Rate
import com.dmdiaz.currency.core.domain.repositories.RatesRepository
import com.dmdiaz.currency.core.network.dtos.APIRatesResponse
import com.dmdiaz.currency.libs.util.di.qualifiers.Dispatcher
import com.dmdiaz.currency.libs.util.di.qualifiers.Dispatchers.Default
import com.dmdiaz.currency.libs.util.extensions.mapRight
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.emitAll
import org.joda.money.CurrencyUnit
import java.util.Date
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val remoteSource: RatesRemoteDataSource,
    private val localSource: RatesLocalDataSource,
    @Dispatcher(Default) defaultDispatcher: CoroutineDispatcher
) : RatesRepository, Repository(defaultDispatcher) {


    override fun getRates(
        date: Date,
        currencyUnit: CurrencyUnit
    ) = get<APIRatesResponse, Pair<Date, List<Rate>>?>(
        domainFlow = {
            val domainFlow =
                localSource
                    .getLatestRates(currencyUnit)
                    .mapRight { dbRates -> dbRates?.let { it.date to it.toRates() } }

            emitAll(domainFlow)
        },
        fetchFlow = {
            val localDate = DateConverter.dateToString(it?.first)
            val nowDate = DateConverter.dateToString(date)

            if(localDate != nowDate){
                emit(remoteSource.getRates(date, currencyUnit))
            }
        },
        saveFetchSuccess = { fetch ->
            val save = fetch.toDBRates().copy(date = date)
            localSource.saveLatestRates(save).bind()
        }
    )
        .mapRight { pair ->
            pair.second.filterNot { it.currencyUnit == currencyUnit }
        }

}