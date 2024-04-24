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

package com.dmdiaz.currency.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dmdiaz.currency.core.ui.R
import org.joda.money.CurrencyUnit

@Composable
fun CurrencyUnitIcon(
    currencyUnit: CurrencyUnit,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    borderColor: Color = MaterialTheme.colorScheme.onSurface
){


    val flag = when (currencyUnit.code) {
        "CAD" -> R.drawable.flag_ca
        "HKD" -> R.drawable.flag_hk
        "ISK" -> R.drawable.flag_is
        "PHP" -> R.drawable.flag_ph
        "DKK" -> R.drawable.flag_dk
        "HUF" -> R.drawable.flag_hu
        "CZK" -> R.drawable.flag_cz
        "GBP" -> R.drawable.flag_gb
        "RON" -> R.drawable.flag_ro
        "SEK" -> R.drawable.flag_se
        "IDR" -> R.drawable.flag_id
        "INR" -> R.drawable.flag_in
        "BRL" -> R.drawable.flag_br
        "RUB" -> R.drawable.flag_ru
        "HRK" -> R.drawable.flag_hr
        "JPY" -> R.drawable.flag_jp
        "THB" -> R.drawable.flag_th
        "CHF" -> R.drawable.flag_ch
        "EUR" -> R.drawable.flag_eu
        "MYR" -> R.drawable.flag_my
        "BGN" -> R.drawable.flag_bg
        "TRY" -> R.drawable.flag_tr
        "CNY" -> R.drawable.flag_cn
        "NOK" -> R.drawable.flag_no
        "NZD" -> R.drawable.flag_nz
        "ZAR" -> R.drawable.flag_za
        "USD" -> R.drawable.flag_us
        "MXN" -> R.drawable.flag_mx
        "SGD" -> R.drawable.flag_sg
        "AUD" -> R.drawable.flag_au
        "ILS" -> R.drawable.flag_is
        "KRW" -> R.drawable.flag_kr
        "PLN" -> R.drawable.flag_pl
        else -> R.drawable.flag__unknown
    }

    Image(
        painter = painterResource(id = flag),
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        modifier = modifier
            .size(size)
            .clip(CircleShape) // clip to the circle shape
            .border(2.dp, borderColor, CircleShape)   // add a border (optional)
    )
}



@Preview("Top App Bar")
@Composable
fun CurrencyUnitIconPreview(){
    CurrencyUnitIcon(
        currencyUnit = CurrencyUnit.USD
    )
}