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

package com.dmdiaz.currency.core.data.rates.local.datasource

import arrow.core.Either
import arrow.core.raise.catch
import arrow.core.raise.either
import com.dmdiaz.currency.core.data.rates.local.db.DBRatesDao
import com.dmdiaz.currency.core.data.rates.local.db.entities.DBRates
import com.dmdiaz.currency.core.domain.common.models.CommonLocalError
import com.dmdiaz.currency.core.domain.common.models.UnknownError
import com.dmdiaz.currency.libs.util.extensions.emitAllRight
import com.dmdiaz.currency.libs.util.extensions.emitLeft
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
    ) = flow<Either<CommonLocalError, DBRates?>> {
        emitAllRight(
            ratesDao
                .get(currencyUnit)
                .distinctUntilChanged()
        )
    }.catch { throwable ->
        emitLeft(UnknownError(throwable))
    }

    override suspend fun saveLatestRates(
        rates: DBRates
    ) = either<CommonLocalError, Unit> {
        catch(
            block = {
                withContext(Dispatchers.IO){
                    ratesDao.upsert(rates)
                }
            },
            catch = {
                raise(UnknownError(it))
            }
        )
    }
}