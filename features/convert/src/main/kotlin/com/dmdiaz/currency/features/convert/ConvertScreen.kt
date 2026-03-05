/*
 * MIT License
 *
 * Copyright (c) 2026 David Diaz
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


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dmdiaz.currency.core.domain.common.models.NoInternetError
import com.dmdiaz.currency.core.domain.rates.models.RatesCommonError
import com.dmdiaz.currency.core.domain.rates.models.RatesError
import com.dmdiaz.currency.core.ui.ScreenPreviews
import com.dmdiaz.currency.core.ui.components.CurrencyUnitIcon
import com.dmdiaz.currency.core.ui.components.Error
import com.dmdiaz.currency.core.ui.components.SimpleList
import com.dmdiaz.currency.features.convert.ConvertScreenEvent.AmountChanged
import com.dmdiaz.currency.features.convert.ConvertScreenEvent.AmountUpdated
import com.dmdiaz.currency.features.convert.ConvertScreenEvent.Retry
import com.dmdiaz.currency.features.convert.viewModel.ConvertViewModel
import com.dmdiaz.currency.libs.designsystem.components.CurrencyBackground
import com.dmdiaz.currency.libs.designsystem.components.LceComponent
import com.dmdiaz.currency.libs.designsystem.components.MoneyTextField
import com.dmdiaz.currency.libs.designsystem.state.LCE
import com.dmdiaz.currency.libs.designsystem.theme.CurrencyTheme
import com.dmdiaz.currency.libs.util.extensions.toFormattedString
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.math.BigDecimal

@Composable
internal fun ConvertRoute(
    modifier: Modifier = Modifier,
    viewModel: ConvertViewModel = hiltViewModel(),
) {


    val screenState = rememberConvertScreenState(
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent
    )

    ConvertScreen(
        uiState = screenState.uiState,
        onEvent = screenState::onEvent,
        modifier = modifier,
    )
}

@Composable
internal fun ConvertScreen(
    uiState: ConvertScreenUIState,
    onEvent: (ConvertScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        ConvertMoneyTextField(
            convertValue =  uiState.enteredAmount,
            onConvertValueChanged = { onEvent(AmountUpdated(it)) }
        )

        ConvertedAmounts(
            modifier = Modifier.fillMaxSize(),
            convertedAmounts = uiState.convertedAmounts ,
            onRetry = { onEvent(Retry) },
            onConvertedAmountClicked = { onEvent(AmountChanged(it)) }
        )
    }
}

@Composable
internal fun ConvertedAmounts(
    convertedAmounts: LCE<RatesError, List<Money>>,
    onRetry: () -> Unit,
    onConvertedAmountClicked: (Money) -> Unit,
    modifier: Modifier = Modifier
){

    LceComponent(
        state = convertedAmounts,
        modifier = modifier,
        loading = {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(Modifier.weight(1f))
                CircularProgressIndicator(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )
                Spacer(Modifier.weight(1f))
            }
        },
        error = { error ->
            Error(
                error = (error as RatesCommonError).error,
                onRetryClicked = onRetry
            )
        },
        content = { value ->
            SimpleList(
                modifier = Modifier.fillMaxWidth(),
                items = value,
                key = { _, item -> item.currencyUnit.code }
            ) { topExtraPadding, bottomExtraPadding, item ->
                ConvertedListItem(
                    money = item,
                    modifier = Modifier
                        .padding(
                            top = topExtraPadding,
                            bottom = bottomExtraPadding
                        )
                        .clickable { onConvertedAmountClicked(item) }
                        .padding(
                            top = 8.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp
                        )
                )
            }
        }
    )
}

@Composable
internal fun ConvertMoneyTextField(
    convertValue: Money,
    onConvertValueChanged: (Money) -> Unit
) {
    MoneyTextField(
        value = convertValue,
        textStyle = MaterialTheme.typography.headlineMedium.copy(
            textAlign = TextAlign.End
        ),
        onValueChange = onConvertValueChanged,
        leadingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CurrencyUnitIcon(
                    currencyUnit = convertValue.currencyUnit,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .padding(horizontal = 8.dp)
                )

                Text(
                    text = convertValue.currencyUnit.code,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        },
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    )
}

@Composable
internal fun ConvertedListItem(
    money: Money,
    modifier: Modifier = Modifier
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        CurrencyUnitIcon(currencyUnit = money.currencyUnit)

        Text(
            text = money.currencyUnit.code,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = money.toFormattedString(),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
        )
    }
}

@ScreenPreviews
@Composable
fun ConvertScreenFailure() {
    CurrencyTheme {
        CurrencyBackground {
            ConvertScreen(uiState = ConvertScreenUIState(
                enteredAmount = Money.zero(CurrencyUnit.USD),
                convertedAmounts = LCE.Error(RatesCommonError(NoInternetError))
            ), onEvent = {})
        }
    }
}

@ScreenPreviews
@Composable
fun ConvertedScreenLoading() {
    CurrencyTheme {
        CurrencyBackground {
            ConvertScreen(uiState = ConvertScreenUIState(
                enteredAmount = Money.zero(CurrencyUnit.USD),
                convertedAmounts = LCE.Loading
            ), onEvent = {})
        }
    }
}

@ScreenPreviews
@Composable
fun ConvertedScreenContent() {
    CurrencyTheme {
        CurrencyBackground {
            ConvertScreen(uiState = ConvertScreenUIState(
                enteredAmount = Money.zero(CurrencyUnit.USD),
                convertedAmounts = LCE.Content(
                    value = listOf(
                        Money.of(CurrencyUnit.CAD, BigDecimal.valueOf(1.35)),

                        Money.of(CurrencyUnit.EUR, BigDecimal.valueOf(0.95))
                    )
                )
            ), onEvent = {})
        }
    }
}