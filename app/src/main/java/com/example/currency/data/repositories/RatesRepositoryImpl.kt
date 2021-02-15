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

package com.example.currency.data.repositories

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.currency.data.local.daos.DBRatesDao
import com.example.currency.data.local.entities.DBRates
import com.example.currency.data.remote.dtos.APIRatesResponse
import com.example.currency.data.remote.sources.RatesRemoteDataSource
import com.example.currency.domain.models.Failure
import com.example.currency.domain.models.Rates
import com.example.currency.domain.repositories.RatesRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.joda.money.CurrencyUnit

class RatesRepositoryImpl(
    private val remoteSource: RatesRemoteDataSource,
    private val localSource: DBRatesDao,
    private val mapAPItoDB: (APIRatesResponse) -> DBRates,
    private val mapDBtoDomain: (DBRates) -> Rates,
) : RatesRepository {


    override fun getLatestRates(
        currencyUnit: CurrencyUnit
    ): Flow<Either<Failure, Rates>> = flow {

        val dbRates = withContext(IO) { localSource.get(currencyUnit) }
        dbRates?.let { emit(mapDBtoDomain(it).right()) } // Emit the mapped local

        val remoteFlow = remoteSource.getLatestRates(currencyUnit, dbRates != null)
            .flowOn(IO)
            .onEach { processed ->
                withContext(IO) {
                    processed.map { response ->
                        localSource.upsert(mapAPItoDB(response))
                    }
                }
            }
            .map { response -> response.map { mapDBtoDomain(mapAPItoDB(it)) } }
            .catch { cause -> emit(Failure.UnknownError(cause).left()) }

        emitAll(remoteFlow)
    }
        .catch { cause -> emit(Failure.UnknownError(cause).left()) }
        .cancellable()
        .buffer()





}