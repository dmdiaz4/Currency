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

package com.dmdiaz.currency.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.money.CurrencyUnit
import java.math.BigDecimal
import java.util.Date

// Database DTO
@Entity(tableName = "rates")
data class DBRates(
    @PrimaryKey
    @ColumnInfo(name = "base")
    val base: CurrencyUnit,
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "CAD")
    val CAD: BigDecimal,
    @ColumnInfo(name = "HKD")
    val HKD: BigDecimal,
    @ColumnInfo(name = "ISK")
    val ISK: BigDecimal,
    @ColumnInfo(name = "PHP")
    val PHP: BigDecimal,
    @ColumnInfo(name = "DKK")
    val DKK: BigDecimal,
    @ColumnInfo(name = "HUF")
    val HUF: BigDecimal,
    @ColumnInfo(name = "CZK")
    val CZK: BigDecimal,
    @ColumnInfo(name = "GBP")
    val GBP: BigDecimal,
    @ColumnInfo(name = "RON")
    val RON: BigDecimal,
    @ColumnInfo(name = "SEK")
    val SEK: BigDecimal,
    @ColumnInfo(name = "IDR")
    val IDR: BigDecimal,
    @ColumnInfo(name = "INR")
    val INR: BigDecimal,
    @ColumnInfo(name = "BRL")
    val BRL: BigDecimal,
    @ColumnInfo(name = "RUB")
    val RUB: BigDecimal,
    @ColumnInfo(name = "HRK")
    val HRK: BigDecimal,
    @ColumnInfo(name = "JPY")
    val JPY: BigDecimal,
    @ColumnInfo(name = "THB")
    val THB: BigDecimal,
    @ColumnInfo(name = "CHF")
    val CHF: BigDecimal,
    @ColumnInfo(name = "EUR")
    val EUR: BigDecimal,
    @ColumnInfo(name = "MYR")
    val MYR: BigDecimal,
    @ColumnInfo(name = "BGN")
    val BGN: BigDecimal,
    @ColumnInfo(name = "TRY")
    val TRY: BigDecimal,
    @ColumnInfo(name = "CNY")
    val CNY: BigDecimal,
    @ColumnInfo(name = "NOK")
    val NOK: BigDecimal,
    @ColumnInfo(name = "NZD")
    val NZD: BigDecimal,
    @ColumnInfo(name = "ZAR")
    val ZAR: BigDecimal,
    @ColumnInfo(name = "USD")
    val USD: BigDecimal,
    @ColumnInfo(name = "MXN")
    val MXN: BigDecimal,
    @ColumnInfo(name = "SGD")
    val SGD: BigDecimal,
    @ColumnInfo(name = "AUD")
    val AUD: BigDecimal,
    @ColumnInfo(name = "ILS")
    val ILS: BigDecimal,
    @ColumnInfo(name = "KRW")
    val KRW: BigDecimal,
    @ColumnInfo(name = "PLN")
    val PLN: BigDecimal
)