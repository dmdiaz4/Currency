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

import com.dmdiaz.currency.core.data.network.handlers.NetworkHandler
import com.dmdiaz.currency.core.data.rates.remote.datasource.RatesRemoteDataSource
import com.dmdiaz.currency.core.data.rates.remote.datasource.RatesRemoteDataSourceImpl
import com.dmdiaz.currency.core.data.rates.remote.network.APIRatesService
import com.dmdiaz.currency.core.domain.common.models.Failure
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.joda.money.CurrencyUnit
import org.junit.Before
import org.junit.Test
import java.util.Date


class RatesRemoteDataSourceTests {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private val testScope = TestScope(testDispatcher)

    private lateinit var networkHandler: NetworkHandler

    private lateinit var service: APIRatesService

    private lateinit var subject: RatesRemoteDataSource

    @Before
    fun before(){

        networkHandler = mockk<NetworkHandler>()
        service = mockk<APIRatesService>()

        subject = RatesRemoteDataSourceImpl(
            service = service,
            networkHandler = networkHandler,
            networkDispatcher = testDispatcher
        )
    }

    @Test
    fun get_rates_no_network() = testScope.runTest{

        every { networkHandler.isNetworkAvailable() } returns false

        val response = subject.getRates(Date(), CurrencyUnit.USD)

        assertThat(response.isLeft()).isTrue()
        assertThat(response.leftOrNull()).isEqualTo(Failure.NetworkUnavailable)

    }
}