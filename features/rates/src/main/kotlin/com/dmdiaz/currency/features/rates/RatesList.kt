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

package com.dmdiaz.currency.features.rates

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmdiaz.currency.core.domain.models.rates.Rate
import com.dmdiaz.currency.libs.designsystem.components.CurrencyBackground
import com.dmdiaz.currency.libs.designsystem.components.CurrencyUnitIcon
import com.dmdiaz.currency.libs.designsystem.components.OverlappingRow
import com.dmdiaz.currency.libs.designsystem.components.ThemePreviews
import com.dmdiaz.currency.libs.designsystem.theme.CurrencyTheme
import com.example.compose_recyclerview.ComposeRecyclerView
import org.joda.money.CurrencyUnit
import java.math.BigDecimal

@Composable
fun RatesLists(
    baseCurrencyUnit: CurrencyUnit,
    list: List<Rate>,
    onCurrencyUnitClicked: (CurrencyUnit) -> Unit,
    modifier: Modifier = Modifier
){
    ComposeRecyclerView(
        modifier = modifier
            .fillMaxWidth(),
        itemCount = list.size,
        itemBuilder = {index ->
            val item = list[index]

            val topExtraPadding = if (index == 0) 8.dp else 0.dp

            //if this is the last item add bottom padding
            val bottomExtraPadding = if (list.size == (index +1)) 8.dp else 0.dp

            RateListItem(
                baseCurrencyUnit = baseCurrencyUnit,
                rate = item,
                modifier = Modifier
                    .padding(
                        top = topExtraPadding,
                        bottom = bottomExtraPadding
                    )
                    .clickable { onCurrencyUnitClicked(item.currencyUnit) }
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
            )
        }){

        it.layoutManager = LinearLayoutManager(it.context, RecyclerView.VERTICAL, false)

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RateListItem(
    baseCurrencyUnit: CurrencyUnit,
    rate: Rate,
    modifier: Modifier = Modifier
){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {

        OverlappingRow(
            widthOverlapFactor = 0.5f,
            heightOverlapFactor = 0.3f
        ){
            CurrencyUnitIcon(
                currencyUnit = baseCurrencyUnit,
                size = 36.dp

            )
            CurrencyUnitIcon(
                currencyUnit = rate.currencyUnit,
                size = 36.dp,
            )
        }


        Text(
            text = "${baseCurrencyUnit.code} to ${rate.currencyUnit.code}" ,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = "1 ${baseCurrencyUnit.code} = ${rate.rate} ${rate.currencyUnit.code}",
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            modifier = Modifier.basicMarquee(),
        )
    }
}

@ThemePreviews
@Composable
fun RatesListsPreview(){
    CurrencyTheme {
        CurrencyBackground {
            RatesLists(
                baseCurrencyUnit = CurrencyUnit.USD,
                list = listOf(
                    Rate(
                        currencyUnit = CurrencyUnit.CAD,
                        rate = BigDecimal.valueOf(1.3595972658414928)
                    ),
                    Rate(
                        currencyUnit = CurrencyUnit.EUR,
                        rate = BigDecimal.valueOf(0.9237021984112322)
                    ),
                ),
                onCurrencyUnitClicked = {}
            )
        }
    }
}