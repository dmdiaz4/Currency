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

package com.dmdiaz.currency.integration

import com.dmdiaz.currency.core.domain.usecases.GetConvertedAmountsUseCase
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.math.BigDecimal
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class RatesIntegrationTests {

//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//    @Inject
//    lateinit var testDispatcher: TestDispatcher
//
//    @Inject
//    lateinit var getConvertedAmountsUseCase: GetConvertedAmountsUseCase
//
//
//    @Before
//    fun setup() {
//        hiltRule.inject()
//
//        Dispatchers.setMain(testDispatcher)
//    }
//
//    @After
//    fun tearDown() {
//
//        Dispatchers.resetMain()
//
//        testDispatcher.cancel()
//    }
//
//
//    @Test
//    fun test_case() = runBlocking {
//        val result = getConvertedAmountsUseCase.invoke(Money.zero(CurrencyUnit.USD)).first()
//
//        assertThat(result.isRight()).isTrue()
//        assertThat(result.getOrNull()!!.first().amount).isEqualTo(BigDecimal.ZERO)
//    }

}