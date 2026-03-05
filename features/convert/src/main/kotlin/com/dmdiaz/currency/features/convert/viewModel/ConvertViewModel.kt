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

package com.dmdiaz.currency.features.convert.viewModel

import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmdiaz.currency.core.domain.rates.usecases.GetCurrentRatesUseCase
import com.dmdiaz.currency.core.domain.rates.usecases.RefreshRatesUseCase
import com.dmdiaz.currency.libs.designsystem.extensions.toLCE
import com.dmdiaz.currency.libs.designsystem.state.LCE
import com.dmdiaz.currency.libs.util.di.qualifiers.Dispatcher
import com.dmdiaz.currency.libs.util.di.qualifiers.Dispatchers.Default
import com.dmdiaz.currency.libs.util.extensions.mapRight
import com.dmdiaz.currency.libs.util.extensions.pmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.money.CurrencyUnit.USD
import org.joda.money.Money
import java.math.RoundingMode.HALF_UP
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ConvertViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val getCurrentRatesUseCase: GetCurrentRatesUseCase,
    private val refreshRatesUseCase: RefreshRatesUseCase,
    @Dispatcher(Default) defaultDispatcher: CoroutineDispatcher
) : ViewModel() {


    private val enteredAmount = handle.getStateFlow("money", Money.zero(USD))

    private val convertedAmounts = enteredAmount.flatMapLatest { amount ->
        flow {
            emit(LCE.Loading)
            emitAll(getCurrentRatesUseCase(amount.currencyUnit).mapRight { rates ->
                withContext(defaultDispatcher){
                    rates.pmap { rate ->
                        Money.zero(rate.currencyUnit)
                            .plus(amount.multipliedBy(rate.rate, HALF_UP).amount, HALF_UP)
                    }
                }
            }.map { it.toLCE() })
        }
    }


    val uiState = combine(enteredAmount, convertedAmounts, ::ConvertViewModelUIState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ConvertViewModelUIState(enteredAmount.value),
        )


    @MainThread
    fun onEvent(event: ConvertViewModelEvent) {
        when (event) {
            is ConvertViewModelEvent.AmountChanged -> {
                handle["money"] = event.money
            }

            ConvertViewModelEvent.Retry -> {
                viewModelScope.launch {
                    refreshRatesUseCase(uiState.value.amount.currencyUnit)
                }
            }
        }
    }
}