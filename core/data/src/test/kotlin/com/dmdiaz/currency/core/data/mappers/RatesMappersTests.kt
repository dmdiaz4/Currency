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

package com.dmdiaz.currency.core.data.mappers

import com.dmdiaz.currency.core.database.entities.DBRates
import com.dmdiaz.currency.core.network.dtos.APIRates
import com.dmdiaz.currency.core.network.dtos.APIRatesResponse
import org.joda.money.CurrencyUnit
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import java.math.BigDecimal
import java.util.Date

class RatesMappersTests {

    @Test
    fun map_API_response_to_db() {

        val response = APIRatesResponse(
            base = CurrencyUnit.USD,
            date = Date(1),
            rates = APIRates(
                CAD = BigDecimal.ONE,
                HKD = BigDecimal.ONE,
                ISK = BigDecimal.ONE,
                PHP = BigDecimal.ONE,
                DKK = BigDecimal.ONE,
                HUF = BigDecimal.ONE,
                CZK = BigDecimal.ONE,
                GBP = BigDecimal.ONE,
                RON = BigDecimal.ONE,
                SEK = BigDecimal.ONE,
                IDR = BigDecimal.ONE,
                INR = BigDecimal.ONE,
                BRL = BigDecimal.ONE,
                RUB = BigDecimal.ONE,
                HRK = BigDecimal.ONE,
                JPY = BigDecimal.ONE,
                THB = BigDecimal.ONE,
                CHF = BigDecimal.ONE,
                EUR = BigDecimal.ONE,
                MYR = BigDecimal.ONE,
                BGN = BigDecimal.ONE,
                TRY = BigDecimal.ONE,
                CNY = BigDecimal.ONE,
                NOK = BigDecimal.ONE,
                NZD = BigDecimal.ONE,
                ZAR = BigDecimal.ONE,
                USD = BigDecimal.ONE,
                MXN = BigDecimal.ONE,
                SGD = BigDecimal.ONE,
                AUD = BigDecimal.ONE,
                ILS = BigDecimal.ONE,
                KRW = BigDecimal.ONE,
                PLN = BigDecimal.ONE
            )
        )
        val mapped = response.toDBRates()

        assertThat(mapped.base).isEqualTo(CurrencyUnit.USD)
        assertThat(mapped.date).isEqualTo(Date(1))
        assertThat(mapped.CAD).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.HKD).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.ISK).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.PHP).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.DKK).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.HUF).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.CZK).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.GBP).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.RON).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.SEK).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.IDR).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.INR).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.BRL).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.RUB).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.HRK).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.JPY).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.THB).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.CHF).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.EUR).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.MYR).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.BGN).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.TRY).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.CNY).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.NOK).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.NZD).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.ZAR).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.USD).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.MXN).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.SGD).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.AUD).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.ILS).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.KRW).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.PLN).isEqualTo(BigDecimal.ONE)
    }

    @Test
    fun map_db_to_domain_model() {

        val dbRates = DBRates(
            base = CurrencyUnit.USD,
            date = Date(1),
            CAD = BigDecimal.ONE,
            HKD = BigDecimal.ONE,
            ISK = BigDecimal.ONE,
            PHP = BigDecimal.ONE,
            DKK = BigDecimal.ONE,
            HUF = BigDecimal.ONE,
            CZK = BigDecimal.ONE,
            GBP = BigDecimal.ONE,
            RON = BigDecimal.ONE,
            SEK = BigDecimal.ONE,
            IDR = BigDecimal.ONE,
            INR = BigDecimal.ONE,
            BRL = BigDecimal.ONE,
            RUB = BigDecimal.ONE,
            HRK = BigDecimal.ONE,
            JPY = BigDecimal.ONE,
            THB = BigDecimal.ONE,
            CHF = BigDecimal.ONE,
            EUR = BigDecimal.ONE,
            MYR = BigDecimal.ONE,
            BGN = BigDecimal.ONE,
            TRY = BigDecimal.ONE,
            CNY = BigDecimal.ONE,
            NOK = BigDecimal.ONE,
            NZD = BigDecimal.ONE,
            ZAR = BigDecimal.ONE,
            USD = BigDecimal.ONE,
            MXN = BigDecimal.ONE,
            SGD = BigDecimal.ONE,
            AUD = BigDecimal.ONE,
            ILS = BigDecimal.ONE,
            KRW = BigDecimal.ONE,
            PLN = BigDecimal.ONE
        )
        val mapped = dbRates.toRates()

        assertThat(mapped.base).isEqualTo(CurrencyUnit.USD)
        assertThat(mapped.date).isEqualTo(Date(1))
        assertThat(mapped.rates).hasSize(33)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("CAD")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("HKD")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("ISK")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("PHP")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("DKK")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("HUF")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("CZK")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("GBP")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("RON")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("SEK")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("IDR")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("INR")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("BRL")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("RUB")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("HRK")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("JPY")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("THB")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("CHF")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("EUR")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("MYR")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("BGN")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("TRY")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("CNY")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("NOK")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("NZD")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("ZAR")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("USD")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("MXN")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("SGD")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("AUD")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("ILS")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("KRW")}?.rate).isEqualTo(BigDecimal.ONE)
        assertThat(mapped.rates.find { it.currencyUnit == CurrencyUnit.of("PLN")}?.rate).isEqualTo(BigDecimal.ONE)
    }
}