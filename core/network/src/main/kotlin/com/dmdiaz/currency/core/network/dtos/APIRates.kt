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

package com.dmdiaz.currency.core.network.dtos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal
import java.math.BigDecimal.ONE

// API DTO
@JsonClass(generateAdapter = true)
data class APIRates(
    @Json(name = "CAD")
    var CAD: BigDecimal = ONE,
    @Json(name = "HKD")
    var HKD: BigDecimal = ONE,
    @Json(name = "ISK")
    var ISK: BigDecimal = ONE,
    @Json(name = "PHP")
    var PHP: BigDecimal = ONE,
    @Json(name = "DKK")
    var DKK: BigDecimal = ONE,
    @Json(name = "HUF")
    var HUF: BigDecimal = ONE,
    @Json(name = "CZK")
    var CZK: BigDecimal = ONE,
    @Json(name = "GBP")
    var GBP: BigDecimal = ONE,
    @Json(name = "RON")
    var RON: BigDecimal = ONE,
    @Json(name = "SEK")
    var SEK: BigDecimal = ONE,
    @Json(name = "IDR")
    var IDR: BigDecimal = ONE,
    @Json(name = "INR")
    var INR: BigDecimal = ONE,
    @Json(name = "BRL")
    var BRL: BigDecimal = ONE,
    @Json(name = "RUB")
    var RUB: BigDecimal = ONE,
    @Json(name = "HRK")
    var HRK: BigDecimal = ONE,
    @Json(name = "JPY")
    var JPY: BigDecimal = ONE,
    @Json(name = "THB")
    var THB: BigDecimal = ONE,
    @Json(name = "CHF")
    var CHF: BigDecimal = ONE,
    @Json(name = "EUR")
    var EUR: BigDecimal = ONE,
    @Json(name = "MYR")
    var MYR: BigDecimal = ONE,
    @Json(name = "BGN")
    var BGN: BigDecimal = ONE,
    @Json(name = "TRY")
    var TRY: BigDecimal = ONE,
    @Json(name = "CNY")
    var CNY: BigDecimal = ONE,
    @Json(name = "NOK")
    var NOK: BigDecimal = ONE,
    @Json(name = "NZD")
    var NZD: BigDecimal = ONE,
    @Json(name = "ZAR")
    var ZAR: BigDecimal = ONE,
    @Json(name = "USD")
    var USD: BigDecimal = ONE,
    @Json(name = "MXN")
    var MXN: BigDecimal = ONE,
    @Json(name = "SGD")
    var SGD: BigDecimal = ONE,
    @Json(name = "AUD")
    var AUD: BigDecimal = ONE,
    @Json(name = "ILS")
    var ILS: BigDecimal = ONE,
    @Json(name = "KRW")
    var KRW: BigDecimal = ONE,
    @Json(name = "PLN")
    var PLN: BigDecimal = ONE
)