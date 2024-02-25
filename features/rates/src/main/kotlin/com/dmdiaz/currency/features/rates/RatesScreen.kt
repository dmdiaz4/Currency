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


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmdiaz.currency.core.domain.models.Resource
import com.dmdiaz.currency.features.rates.RatesEvent.AmountChanged
import com.dmdiaz.currency.libs.designsystem.components.MoneyTextField
import com.dmdiaz.currency.libs.util.extensions.toFormattedString
import org.joda.money.CurrencyUnit
import org.joda.money.Money

@Composable
internal fun RatesRoute(
    modifier: Modifier = Modifier,
    viewModel: RatesViewModel = hiltViewModel(),
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    RatesScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@Composable
internal fun RatesScreen(
    uiState: RatesState,
    onEvent: (RatesEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ){
        MoneyTextField(
            value = uiState.enteredAmount,
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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(uiState.convertedAmounts.data){
                        Text(
                            text = it.toFormattedString(),
                        )
                    }
                }
            }
        }
        

    }

}

@Preview
@Composable
fun RatesScreenPopulated() {
    RatesScreen(
        uiState = RatesState(
            enteredAmount = Money.zero(CurrencyUnit.USD),
            convertedAmounts = Resource.Loading
        ),
        onEvent = {}
    )
}