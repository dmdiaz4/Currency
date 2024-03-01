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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmdiaz.currency.core.domain.models.Failure
import com.dmdiaz.currency.core.domain.models.Resource
import com.dmdiaz.currency.features.convert.ConvertEvent.AmountChanged
import com.dmdiaz.currency.libs.designsystem.components.CurrencyBackground
import com.dmdiaz.currency.libs.designsystem.components.CurrencyUnitIcon
import com.dmdiaz.currency.libs.designsystem.components.MoneyTextField
import com.dmdiaz.currency.libs.designsystem.components.ThemePreviews
import com.dmdiaz.currency.libs.designsystem.icon.CurrencyIcons
import com.dmdiaz.currency.libs.designsystem.theme.CurrencyTheme
import com.dmdiaz.currency.libs.designsystem.theme.LocalTintTheme
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
    ){
        MoneyTextField(
            value = uiState.enteredAmount,
            textStyle = MaterialTheme.typography.headlineMedium.copy(
                textAlign = TextAlign.End
            ),
            onValueChange = {
                onEvent(AmountChanged(it))
            },
            leadingIcon = {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CurrencyUnitIcon(
                        currencyUnit = uiState.enteredAmount.currencyUnit,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .padding(horizontal = 8.dp)
                    )

                    Text(
                        text = uiState.enteredAmount.currencyUnit.code,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                }
            },
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        )

        when (uiState.convertedAmounts){
            is Resource.Failed -> {
                ErrorState(error = uiState.convertedAmounts.exception)
            }
            Resource.Loading -> {
                Spacer(Modifier.weight(1f))
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                )
                Spacer(Modifier.weight(1f))
            }
            is Resource.Success -> {
                ConvertedList(
                    list = uiState.convertedAmounts.data,
                    onMoneyClicked = {
                        onEvent(AmountChanged(it))
                    }
                )
            }
        }
        

    }

}



@Composable
private fun ErrorState(
    error: Failure,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .testTag("bookmarks:empty"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val iconTint = LocalTintTheme.current.iconTint

        Image(
            imageVector = CurrencyIcons.Warning,
            colorFilter = if (iconTint != Color.Unspecified) ColorFilter.tint(iconTint) else null,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(64.dp)
            ,
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Error",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "This is an error",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@ThemePreviews
@Composable
fun ConvertScreenFailure() {
    CurrencyTheme {
        CurrencyBackground {
            ConvertScreen(
                uiState = ConvertState(
                    enteredAmount = Money.zero(CurrencyUnit.USD),
                    convertedAmounts = Resource.Failed(Failure.NetworkUnavailable)
                ),
                onEvent = {}
            )
        }
    }
}