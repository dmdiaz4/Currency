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

import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmdiaz.currency.core.domain.usecases.GetCurrentRatesUseCase
import com.dmdiaz.currency.core.domain.usecases.RefreshRatesUseCase
import com.dmdiaz.currency.core.ui.state.Lce
import com.dmdiaz.currency.core.ui.state.bind
import com.dmdiaz.currency.core.ui.state.lce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.joda.money.CurrencyUnit.USD
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RatesViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val getRatesUseCase: GetCurrentRatesUseCase,
    private val refreshRatesUseCase: RefreshRatesUseCase
) : ViewModel() {

    private val baseCurrency = handle.getStateFlow("currency_unit", USD)

    private val rates = baseCurrency.flatMapLatest { currencyUnit ->
        flow {
            emit(Lce.Loading)
            emitAll(getRatesUseCase(currencyUnit).map { lce { it.bind() } })
        }
    }

    val state = combine(baseCurrency, rates, ::RatesState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RatesState(baseCurrency.value),
        )


    @MainThread
    fun onEvent(event: RatesEvent) {
        when (event) {
            is RatesEvent.CurrencyUnitChanged -> {
                handle["currency_unit"] = event.currencyUnit
            }

            RatesEvent.Retry -> {
                viewModelScope.launch {
                    refreshRatesUseCase(baseCurrency.value)
                }
            }
        }
    }
}