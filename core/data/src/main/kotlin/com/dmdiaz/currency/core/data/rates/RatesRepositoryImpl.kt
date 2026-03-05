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

package com.dmdiaz.currency.core.data.rates


import com.dmdiaz.currency.core.data.common.Repository
import com.dmdiaz.currency.core.data.common.Repository.FetchStrategy.NoFetch
import com.dmdiaz.currency.core.data.common.Repository.FetchStrategy.RemoteFetch.BackgroundFetch
import com.dmdiaz.currency.core.data.common.Repository.FetchStrategy.RemoteFetch.BlockingFetch
import com.dmdiaz.currency.core.data.common.serializers.DateSerializer
import com.dmdiaz.currency.core.data.rates.local.datasource.RatesLocalDataSource
import com.dmdiaz.currency.core.data.rates.local.db.entities.DBRates
import com.dmdiaz.currency.core.data.rates.mappers.toDBRates
import com.dmdiaz.currency.core.data.rates.mappers.toRates
import com.dmdiaz.currency.core.data.rates.remote.datasource.RatesRemoteDataSource
import com.dmdiaz.currency.core.data.rates.remote.network.dtos.APIRatesResponse
import com.dmdiaz.currency.core.domain.rates.RatesRepository
import com.dmdiaz.currency.core.domain.rates.models.RatesCommonError
import com.dmdiaz.currency.libs.util.di.qualifiers.Dispatcher
import com.dmdiaz.currency.libs.util.di.qualifiers.Dispatchers.Default
import com.dmdiaz.currency.libs.util.extensions.mapLeft
import com.dmdiaz.currency.libs.util.extensions.mapRight
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import org.joda.money.CurrencyUnit
import java.util.Date
import javax.inject.Inject

@Suppress("RemoveExplicitTypeArguments")
class RatesRepositoryImpl @Inject constructor(
    private val remoteSource: RatesRemoteDataSource,
    private val localSource: RatesLocalDataSource,
    @Dispatcher(Default) defaultDispatcher: CoroutineDispatcher
) : RatesRepository, Repository(defaultDispatcher) {


    override fun getRates(
        date: Date,
        currencyUnit: CurrencyUnit
    ) = get<DBRates?, APIRatesResponse>(
        localFlow = {
            emitAll(localSource.getLatestRates(currencyUnit))
        },
        fetchPolicy = { _, dbRates ->
            val localDate = DateSerializer.serialize(dbRates?.date)
            val nowDate = DateSerializer.serialize(date)

            if (dbRates == null){
                BlockingFetch
            } else if(localDate != nowDate){
                BackgroundFetch
            } else {
                NoFetch
            }
        },
        fetchFlow = { _, _ ->
            emitAll(remoteSource.getRates(date, currencyUnit))
        },
        saveFetchSuccess = { _, _, remote ->
            val save = remote.toDBRates().copy(date = date)
            localSource.saveLatestRates(save).bind()
        }
    )
        .mapRight { dbRates ->
            dbRates?.toRates()?.filterNot { it.currencyUnit == currencyUnit }?: emptyList()
        }
        .mapLeft {
            RatesCommonError(it)
        }


    override suspend fun refreshRates(
        date: Date,
        currencyUnit: CurrencyUnit,
    ) = crud {
            val response = remoteSource.getRates(date, currencyUnit).first().bind()
            val save = response.toDBRates().copy(date = date)
            localSource.saveLatestRates(save).bind()
        }
        .mapLeft { RatesCommonError(it) }

}