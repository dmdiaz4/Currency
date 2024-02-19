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

import arrow.core.right
import com.dmdiaz.currency.core.domain.models.rates.Rate
import com.dmdiaz.currency.core.domain.models.rates.Rates
import com.dmdiaz.currency.core.domain.repositories.RatesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.joda.money.CurrencyUnit
import org.joda.money.CurrencyUnit.CAD
import org.joda.money.CurrencyUnit.USD
import org.joda.money.Money
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.Date

class GetConvertedAmountsUseCaseTests {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private val testScope = TestScope(testDispatcher)

    private lateinit var repository: RatesRepository

    private lateinit var subject: GetConvertedAmountsUseCase

    @Before
    fun before(){

        repository = mockk<RatesRepository>()

        subject = GetConvertedAmountsUseCase(
            repository = repository,
            dispatcher = testDispatcher
        )
    }


    @Test
    fun convert_math_working () = testScope.runTest {
        every {
            repository.getRates(any(), any())
        } answers {
            flow{
                val rates = Rates(
                    base = USD,
                    date = Date(),
                    rates = listOf(Rate(CurrencyUnit.CAD, BigDecimal("0.5")))
                )
                emit(rates.right())
            }
        }

        val twoUSDDollars = Money.zero(USD).plusMajor(2)
        val oneCADDollar = Money.zero(CAD).plusMajor(1)
        val converted = subject.invoke(twoUSDDollars).first()


        assertThat(converted.isRight()).isTrue()
        assertThat(converted.getOrNull()).hasSize(1)
        assertThat(converted.getOrNull()!!.first()).isEqualTo(oneCADDollar)
    }
}