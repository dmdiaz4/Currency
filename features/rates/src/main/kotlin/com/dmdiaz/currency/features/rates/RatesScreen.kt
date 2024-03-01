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


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmdiaz.currency.core.domain.models.Failure
import com.dmdiaz.currency.core.domain.models.Resource
import com.dmdiaz.currency.features.rates.RatesEvent.CurrencyUnitChanged
import com.dmdiaz.currency.features.rates.RatesEvent.Retry
import com.dmdiaz.currency.libs.designsystem.R
import com.dmdiaz.currency.libs.designsystem.icon.CurrencyIcons
import com.dmdiaz.currency.libs.designsystem.theme.LocalTintTheme
import org.joda.money.CurrencyUnit

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
    ){

        when (uiState.rates){
            is Resource.Failed -> {
                ErrorState(
                    error = uiState.rates.exception,
                    onRetryClicked = { onEvent(Retry) }
                )
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
                RatesLists(
                    baseCurrencyUnit = uiState.baseCurrencyUnit,
                    list = uiState.rates.data,
                    onCurrencyUnitClicked = { onEvent(CurrencyUnitChanged(it))}
                )
            }
        }
        

    }

}

@Composable
private fun ErrorState(
    error: Failure,
    onRetryClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
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
            text = stringResource(id = R.string.error),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        val errorRes = when(error){
            is Failure.NetworkError -> R.string.network_error
            Failure.NetworkUnavailable -> R.string.network_unavailable
            else -> R.string.unknown_error
        }

        Text(
            text = stringResource(id = errorRes),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            onRetryClicked()
        }) {
            Text(
                text = stringResource(id = R.string.retry),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
fun RatesScreenPopulated() {
    RatesScreen(
        uiState = RatesState(
            baseCurrencyUnit = CurrencyUnit.USD,
            rates = Resource.Loading
        ),
        onEvent = {}
    )
}