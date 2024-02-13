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

package com.example.feature.rates.data.mappers

import com.example.core.data.local.entities.DBRates
import com.example.feature.rates.data.remote.dtos.APIRatesResponse
import com.example.feature.rates.domain.models.Rate
import com.example.feature.rates.domain.models.Rates
import org.joda.money.CurrencyUnit


fun APIRatesResponse.toDBRates() = DBRates(
    base = base,
    CAD = rates.CAD,
    HKD = rates.HKD,
    ISK = rates.ISK,
    PHP = rates.PHP,
    DKK = rates.DKK,
    HUF = rates.HUF,
    CZK = rates.CZK,
    GBP = rates.GBP,
    RON = rates.RON,
    SEK = rates.SEK,
    IDR = rates.IDR,
    INR = rates.INR,
    BRL = rates.BRL,
    RUB = rates.RUB,
    HRK = rates.HRK,
    JPY = rates.JPY,
    THB = rates.THB,
    CHF = rates.CHF,
    EUR = rates.EUR,
    MYR = rates.MYR,
    BGN = rates.BGN,
    TRY = rates.TRY,
    CNY = rates.CNY,
    NOK = rates.NOK,
    NZD = rates.NZD,
    ZAR = rates.ZAR,
    USD = rates.USD,
    MXN = rates.MXN,
    SGD = rates.SGD,
    AUD = rates.AUD,
    ILS = rates.ILS,
    KRW = rates.KRW,
    PLN = rates.PLN,
)

fun DBRates.toRates() = com.example.feature.rates.domain.models.Rates(
    base = base,
    rates = listOf(
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("CAD"),
            rate = CAD
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("HKD"),
            rate = HKD
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("ISK"),
            rate = ISK
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("PHP"),
            rate = PHP
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("DKK"),
            rate = DKK
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("HUF"),
            rate = HUF
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("CZK"),
            rate = CZK
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("GBP"),
            rate = GBP
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("RON"),
            rate = RON
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("SEK"),
            rate = SEK
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("IDR"),
            rate = IDR
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("INR"),
            rate = INR
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("BRL"),
            rate = BRL
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("RUB"),
            rate = RUB
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("HRK"),
            rate = HRK
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("JPY"),
            rate = JPY
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("THB"),
            rate = THB
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("CHF"),
            rate = CHF
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("EUR"),
            rate = EUR
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("MYR"),
            rate = MYR
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("BGN"),
            rate = BGN
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("TRY"),
            rate = TRY
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("CNY"),
            rate = CNY
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("NOK"),
            rate = NOK
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("NZD"),
            rate = NZD
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("ZAR"),
            rate = ZAR
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("USD"),
            rate = USD
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("MXN"),
            rate = MXN
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("SGD"),
            rate = SGD
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("AUD"),
            rate = AUD
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("ILS"),
            rate = ILS
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("KRW"),
            rate = KRW
        ),
        com.example.feature.rates.domain.models.Rate(
            currencyUnit = CurrencyUnit.of("PLN"),
            rate = PLN
        ),
    )
)

