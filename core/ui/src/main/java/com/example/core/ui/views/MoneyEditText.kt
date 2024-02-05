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

package com.example.core.ui.views

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import com.example.core.extensions.toFormattedString
import com.google.android.material.R
import com.google.android.material.textfield.TextInputEditText
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.regex.Pattern

class MoneyEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyleAttr) {


    interface MoneyUpdatedListener {
        fun onUpdated(total: Money)
    }

    var currencyUnit = CurrencyUnit.USD
        set(value) {
            if (field != value) {
                field = value
                money = Money.zero(value).plus(amount, RoundingMode.HALF_UP)
                refreshText()
            }
        }

    var amount: BigDecimal = BigDecimal.ZERO
        set(value) {
            if (field != value) {
                field = value
                money = Money.zero(currencyUnit).plus(value, RoundingMode.HALF_UP)
                refreshText()
            }
        }

    var money: Money = Money.zero(currencyUnit).plus(amount, RoundingMode.HALF_UP)
        set(value) {
            if (field != value) {
                field = value
                currencyUnit = value.currencyUnit
                amount = value.amount
                moneyUpdateListener?.onUpdated(value)
                refreshText()
            }
        }


    var moneyUpdateListener: MoneyUpdatedListener? = null


    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable) {
            removeTextChangedListener(this)
            val value = cleanNumericString(s.toString())
            money = if (TextUtils.isEmpty(value)) {
                // If null or empty, set to zero
                Money.zero(currencyUnit)
            } else {
                // Set to entered amount
                    try {
                        val longValue = value.toLong()
                        Money.zero(currencyUnit).plusMinor(longValue)
                    }catch (e : NumberFormatException){
                        Money.zero(currencyUnit).apply {
                            value.chunked(5).forEach { valueChunk ->
                                plusMinor(valueChunk.toLong())
                            }
                        }
                    }


            }
            refreshText()
            addTextChangedListener(this)
        }
    }

    init {
        isSingleLine = true
        setLines(1)
        refreshText()
        addTextChangedListener(textWatcher)
    }

    private fun refreshText() {
        val newText = money.toFormattedString()
        if (text.toString() != newText) {
            setText(newText)
            setSelection(newText.length)
        }
    }

    companion object {
        private val nonNumericPattern = Pattern.compile("[^0-9]")
        private val leadingZeroPattern = Pattern.compile("^0+")
        private fun cleanNumericString(input: String): String {
            if (TextUtils.isEmpty(input)) return input
            val matcher = nonNumericPattern.matcher(input)
            return stripLeadingZeros(matcher.replaceAll(""))
        }

        private fun stripLeadingZeros(input: String): String {
            if (TextUtils.isEmpty(input)) return input
            val matcher = leadingZeroPattern.matcher(input)
            return matcher.replaceAll("")
        }
    }
}