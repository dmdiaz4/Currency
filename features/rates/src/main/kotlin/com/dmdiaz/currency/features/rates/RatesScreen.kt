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

package com.dmdiaz.currency.features.rates


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmdiaz.currency.core.domain.common.models.NoInternetError
import com.dmdiaz.currency.core.domain.rates.models.Rate
import com.dmdiaz.currency.core.domain.rates.models.RatesCommonError
import com.dmdiaz.currency.core.domain.rates.models.RatesError
import com.dmdiaz.currency.core.ui.ScreenPreviews
import com.dmdiaz.currency.core.ui.components.CurrencyUnitIcon
import com.dmdiaz.currency.core.ui.components.Error
import com.dmdiaz.currency.core.ui.components.SimpleList
import com.dmdiaz.currency.features.rates.RatesEvent.CurrencyUnitChanged
import com.dmdiaz.currency.features.rates.RatesEvent.Retry
import com.dmdiaz.currency.libs.designsystem.components.CurrencyBackground
import com.dmdiaz.currency.libs.designsystem.components.LceComponent
import com.dmdiaz.currency.libs.designsystem.components.OverlappingRow
import com.dmdiaz.currency.libs.designsystem.state.LCE
import com.dmdiaz.currency.libs.designsystem.theme.CurrencyTheme
import org.joda.money.CurrencyUnit
import java.math.BigDecimal
import java.util.Date

@Composable
internal fun RatesRoute(
    modifier: Modifier = Modifier,
    viewModel: RatesViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
    ){

        Rates(
            baseCurrencyUnit = uiState.baseCurrencyUnit,
            rates = uiState.rates,
            onEvent = onEvent
        )
    }
}

@Composable
internal fun Rates(
    baseCurrencyUnit: CurrencyUnit,
    rates: LCE<RatesError, List<Rate>>,
    onEvent: (RatesEvent) -> Unit,
    modifier: Modifier = Modifier
){

    LceComponent(
        state = rates,
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
                onRetryClicked = { onEvent(Retry) }
            )
        },
        content = { value ->
            SimpleList(
                modifier = Modifier.fillMaxWidth(),
                items = value,
                key = { _, item -> item.currencyUnit.code }
            ) { topExtraPadding, bottomExtraPadding, item ->
                RateListItem(
                    baseCurrencyUnit = baseCurrencyUnit,
                    rate = item,
                    modifier = Modifier
                        .padding(
                            top = topExtraPadding,
                            bottom = bottomExtraPadding
                        )
                        .clickable { onEvent(CurrencyUnitChanged(item.currencyUnit))}
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun RateListItem(
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

@ScreenPreviews
@Composable
fun RatesScreenFailure() {
    CurrencyTheme {
        CurrencyBackground {
            RatesScreen(uiState = RatesState(
                baseCurrencyUnit = CurrencyUnit.USD,
                rates = LCE.Error(RatesCommonError(NoInternetError))
            ), onEvent = {})
        }
    }
}

@ScreenPreviews
@Composable
fun RatesScreenLoading() {
    CurrencyTheme {
        CurrencyBackground {
            RatesScreen(uiState = RatesState(
                baseCurrencyUnit = CurrencyUnit.USD,
                rates = LCE.Loading
            ), onEvent = {})
        }
    }
}

@ScreenPreviews
@Composable
fun RatesScreenContent() {
    CurrencyTheme {
        CurrencyBackground {
            RatesScreen(uiState = RatesState(
                baseCurrencyUnit = CurrencyUnit.USD,
                rates = LCE.Content(
                    value = listOf(
                        Rate(
                            currencyUnit = CurrencyUnit.CAD,
                            date = Date(),
                            rate = BigDecimal.valueOf(1.3595972658414928)
                        ),
                        Rate(
                            currencyUnit = CurrencyUnit.EUR,
                            date = Date(),
                            rate = BigDecimal.valueOf(0.9237021984112322)
                        ),
                    )
                )
            ), onEvent = {})
        }
    }
}