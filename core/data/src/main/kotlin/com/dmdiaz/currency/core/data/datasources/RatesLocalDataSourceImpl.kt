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

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.dmdiaz.currency.core.database.daos.DBRatesDao
import com.dmdiaz.currency.core.database.entities.DBRates
import com.dmdiaz.currency.libs.util.extensions.emitAllRight
import com.dmdiaz.currency.libs.util.extensions.emitLeft
import com.dmdiaz.currency.core.domain.models.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.joda.money.CurrencyUnit
import javax.inject.Inject

class RatesLocalDataSourceImpl @Inject constructor(
    private val ratesDao: DBRatesDao,
): RatesLocalDataSource {

    override fun getLatestRates(
        currencyUnit: CurrencyUnit,
    ) = flow<Either<Failure, DBRates?>> {
        emitAllRight(
            ratesDao
                .get(currencyUnit)
                .distinctUntilChanged()
        )
    }.catch { throwable ->
        emitLeft(Failure.UnknownError(throwable))
    }

    override suspend fun saveLatestRates(
        rates: DBRates
    ) = try {
        withContext(Dispatchers.IO){
            ratesDao.upsert(rates).right()
        }
    } catch (ex: Exception){
        Failure.UnknownError(ex).left()
    }
}