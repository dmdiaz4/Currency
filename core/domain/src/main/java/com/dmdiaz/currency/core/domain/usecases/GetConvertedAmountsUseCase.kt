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

package com.dmdiaz.currency.core.domain.usecases

import arrow.core.Either
import com.dmdiaz.currency.libs.extensions.mapRight
import com.dmdiaz.currency.libs.models.Failure
import com.dmdiaz.currency.core.domain.repositories.RatesRepository
import kotlinx.coroutines.flow.Flow
import org.joda.money.Money
import java.math.RoundingMode.HALF_UP
import java.util.Date
import javax.inject.Inject


class GetConvertedAmountsUseCase @Inject constructor(
    private val repository: RatesRepository,
) {
    operator fun invoke(amount: Money): Flow<Either<Failure, List<Money>>> {

        return repository
            .getRates(Date(), currencyUnit = amount.currencyUnit)
            .mapRight { rates ->
                rates.rates.map { rate ->
                    Money.zero(rate.currencyUnit)
                        .plus(amount.multipliedBy(rate.rate, HALF_UP).amount, HALF_UP)
                }
            }
    }

}