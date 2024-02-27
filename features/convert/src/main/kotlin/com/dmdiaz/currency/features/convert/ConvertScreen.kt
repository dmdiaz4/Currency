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

package com.dmdiaz.currency.features.convert


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmdiaz.currency.core.domain.models.Resource
import com.dmdiaz.currency.features.convert.ConvertEvent.AmountChanged
import com.dmdiaz.currency.libs.designsystem.components.MoneyTextField
import com.dmdiaz.currency.libs.designsystem.components.ThemePreviews
import com.dmdiaz.currency.libs.designsystem.icon.CurrencyIcons
import com.dmdiaz.currency.libs.util.extensions.toFormattedString
import org.joda.money.CurrencyUnit
import org.joda.money.Money

@Composable
internal fun ConvertRoute(
    modifier: Modifier = Modifier,
    viewModel: ConvertViewModel = hiltViewModel(),
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    ConvertScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@Composable
internal fun ConvertScreen(
    uiState: ConvertState,
    onEvent: (ConvertEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ){
        MoneyTextField(
            value = uiState.enteredAmount,
            textStyle = LocalTextStyle.current.copy(fontSize = 28.sp),
            onValueChange = {
                onEvent(AmountChanged(it))
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        when (uiState.convertedAmounts){
            is Resource.Failed -> {

            }
            Resource.Loading -> {


            }
            is Resource.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(
                        items = uiState.convertedAmounts.data,
                        key = { it.currencyUnit.code }
                    ){
                        ConvertValue(
                            money = it,
                            modifier = Modifier
                                .padding(top = 16.dp)
                        )
                    }
                }
            }
        }
        

    }

}

@Composable
internal fun ConvertValue(
    money: Money,
    modifier: Modifier = Modifier
){
    val flag = when(money.currencyUnit.code){
        "USD" -> CurrencyIcons.Flags.Us
        "CAD" -> CurrencyIcons.Flags.Ca
        "HKD" -> CurrencyIcons.Flags.Hk
        "ISK" -> CurrencyIcons.Flags.Is
        "PHP" -> CurrencyIcons.Flags.Ph
        "DKK" -> CurrencyIcons.Flags.Dk
        "GBP" -> CurrencyIcons.Flags.Gb
        "SEK" -> CurrencyIcons.Flags.Se
        else -> Icons.Default.Warning
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            imageVector = flag,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)                       // clip to the circle shape
        )

        Text(
            text = money.currencyUnit.code,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = money.toFormattedString(),
            fontSize = 28.sp,
            modifier = Modifier
        )
    }


}

@ThemePreviews
@Composable
fun ConvertScreenPopulated() {
    ConvertScreen(
        uiState = ConvertState(
            enteredAmount = Money.zero(CurrencyUnit.USD),
            convertedAmounts = Resource.Success(listOf(Money.zero(CurrencyUnit.CAD)))
        ),
        onEvent = {}
    )
}