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

package com.example.feature.rates.data


import com.example.feature.rates.data.local.RatesLocalDataSource
import com.example.feature.rates.data.mappers.toDBRates
import com.example.feature.rates.data.mappers.toRates
import com.example.feature.rates.data.remote.dtos.APIRatesResponse
import com.example.feature.rates.domain.models.Rates
import com.example.core.data.repositories.Repository
import com.example.core.extensions.mapRight
import com.example.feature.rates.data.remote.RatesRemoteDataSource
import com.example.currency.di.qualifiers.Default
import com.example.feature.rates.domain.RatesRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.joda.money.CurrencyUnit
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val remoteSource: RatesRemoteDataSource,
    private val localSource: RatesLocalDataSource,
    @Default defaultDispatcher: CoroutineDispatcher
) : RatesRepository, Repository(defaultDispatcher) {

    override fun getLatestRates(
        currencyUnit: CurrencyUnit
    ) = get<APIRatesResponse, Rates>(
        domainFlow = {
            val domainFlow =
                localSource
                .getLatestRates(currencyUnit)
                .mapRight { it.toRates() }

            emitAll(domainFlow)
        },
        fetchFlow = {
            emit(remoteSource.getLatestRates(currencyUnit))
        },
        saveFetchSuccess = { fetch ->
            localSource.saveLatestRates(fetch.toDBRates())
        }
    )

    override fun getDisabledCurrencyFormats(
    ) = localSource
        .getDisabledCurrencyFormats()
        .mapRight {set -> set.map { CurrencyUnit.of(it) }.toSet()}


    override suspend fun saveDisabledCurrencyFormats(
        values: Set<CurrencyUnit>
    ) = localSource
        .saveDisabledCurrencyFormats(values.map { it.code }.toSet())
}