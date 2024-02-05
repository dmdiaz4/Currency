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

package com.example.feature.rates.ui

import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.rates.domain.usecases.GetLatestRatesUseCase
import com.example.core.extensions.cancelIfActive
import com.example.core.models.Rates
import com.example.core.ui.states.LoadingUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.joda.money.CurrencyUnit
import org.joda.money.CurrencyUnit.USD
import org.joda.money.Money
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val getLatestRates: GetLatestRatesUseCase,
) : ViewModel() {


    private val _state = MutableStateFlow(RatesUIState(enteredAmount = handle.get("money") ?: Money.zero(USD)))
    val state = _state.asStateFlow()

    @MainThread
    fun onAmountChanged(money: Money) {
        val previousState = _state.getAndUpdate { it.copy(enteredAmount = money) }
        handle["money"] = money

        if (!previousState.currencyUnit.equals(money.currencyUnit)){
            fetchLatestRates(money.currencyUnit)
        }
    }

    init {
        fetchLatestRates(state.value.currencyUnit)
    }

    private var fetchJob: Job? = null
    private fun fetchLatestRates(currencyUnit: CurrencyUnit) {
        fetchJob.cancelIfActive()
        _state.update { it.copy(rates = LoadingUIState.Loading) }
        fetchJob = viewModelScope.launch {
            getLatestRates(currencyUnit).collect { results ->
                results.fold(
                    ifLeft = { failure ->
                        _state.update { it.copy(rates = LoadingUIState.Failed(failure)) }
                    },
                    ifRight = { success ->
                        _state.update { it.copy(rates = LoadingUIState.Success(success.rates)) }
                    }
                )
            }
        }
    }
}