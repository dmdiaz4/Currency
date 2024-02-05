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

package com.example.feature.rates.data.local

import arrow.core.Either
import arrow.core.raise.either
import com.example.core.extensions.emitAllRight
import com.example.core.extensions.emitLeft
import com.example.core.extensions.tryOrFailure
import com.example.core.data.local.daos.DBRatesDao
import com.example.core.data.local.entities.DBRates
import com.example.core.models.Failure

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import org.joda.money.CurrencyUnit
import javax.inject.Inject

class RatesLocalDataSource @Inject constructor(
    private val ratesDao: DBRatesDao
) {

    fun getLatestRates(
        currencyUnit: CurrencyUnit,
    ) = flow<Either<Failure, DBRates>> {
        emitAllRight(
            ratesDao
                .get(currencyUnit)
                .filterNotNull()
        )
    }.catch { throwable ->
        emitLeft(Failure.UnknownError(throwable))
    }


    suspend fun saveLatestRates(
        rates: DBRates
    ) = either<Failure, Unit> {
        tryOrFailure {
            ratesDao.upsert(rates)
        }.bind()
    }


}