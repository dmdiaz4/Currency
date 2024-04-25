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

package com.dmdiaz.currency.core.data.rates.mappers

import com.dmdiaz.currency.core.data.rates.local.db.entities.DBRates
import com.dmdiaz.currency.core.data.rates.remote.network.dtos.APIRatesResponse
import com.dmdiaz.currency.core.domain.rates.models.Rate
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
        date = date,
        rate = CAD
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("HKD"),
        date = date,
        rate = HKD
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("ISK"),
        date = date,
        rate = ISK
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("PHP"),
        date = date,
        rate = PHP
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("DKK"),
        date = date,
        rate = DKK
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("HUF"),
        date = date,
        rate = HUF
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("CZK"),
        date = date,
        rate = CZK
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("GBP"),
        date = date,
        rate = GBP
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("RON"),
        date = date,
        rate = RON
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("SEK"),
        date = date,
        rate = SEK
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("IDR"),
        date = date,
        rate = IDR
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("INR"),
        date = date,
        rate = INR
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("BRL"),
        date = date,
        rate = BRL
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("JPY"),
        date = date,
        rate = JPY
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("THB"),
        date = date,
        rate = THB
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("CHF"),
        date = date,
        rate = CHF
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("EUR"),
        date = date,
        rate = EUR
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("MYR"),
        date = date,
        rate = MYR
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("BGN"),
        date = date,
        rate = BGN
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("TRY"),
        date = date,
        rate = TRY
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("CNY"),
        date = date,
        rate = CNY
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("NOK"),
        date = date,
        rate = NOK
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("NZD"),
        date = date,
        rate = NZD
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("ZAR"),
        date = date,
        rate = ZAR
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("USD"),
        date = date,
        rate = USD
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("MXN"),
        date = date,
        rate = MXN
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("SGD"),
        date = date,
        rate = SGD
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("AUD"),
        date = date,
        rate = AUD
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("ILS"),
        date = date,
        rate = ILS
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("KRW"),
        date = date,
        rate = KRW
    ),
    Rate(
        currencyUnit = CurrencyUnit.of("PLN"),
        date = date,
        rate = PLN
    ),
)

