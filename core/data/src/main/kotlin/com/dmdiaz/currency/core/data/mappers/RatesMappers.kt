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
import com.dmdiaz.currency.core.domain.models.rates.Rate
import com.dmdiaz.currency.core.network.dtos.APIRatesResponse
import org.joda.money.CurrencyUnit


fun APIRatesResponse.toDBRates() = DBRates(
    base = base,
    date = date,
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

fun DBRates.toRates() = listOf(
    Rate(
        currencyUnit = CurrencyUnit.of("CAD"),
        rate = CAD
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("HKD"),
        rate = HKD
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("ISK"),
        rate = ISK
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("PHP"),
        rate = PHP
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("DKK"),
        rate = DKK
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("HUF"),
        rate = HUF
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("CZK"),
        rate = CZK
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("GBP"),
        rate = GBP
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("RON"),
        rate = RON
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("SEK"),
        rate = SEK
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("IDR"),
        rate = IDR
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("INR"),
        rate = INR
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("BRL"),
        rate = BRL
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("RUB"),
        rate = RUB
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("HRK"),
        rate = HRK
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("JPY"),
        rate = JPY
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("THB"),
        rate = THB
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("CHF"),
        rate = CHF
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("EUR"),
        rate = EUR
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("MYR"),
        rate = MYR
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("BGN"),
        rate = BGN
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("TRY"),
        rate = TRY
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("CNY"),
        rate = CNY
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("NOK"),
        rate = NOK
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("NZD"),
        rate = NZD
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("ZAR"),
        rate = ZAR
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("USD"),
        rate = USD
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("MXN"),
        rate = MXN
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("SGD"),
        rate = SGD
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("AUD"),
        rate = AUD
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("ILS"),
        rate = ILS
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("KRW"),
        rate = KRW
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("PLN"),
        rate = PLN
    ),
)

