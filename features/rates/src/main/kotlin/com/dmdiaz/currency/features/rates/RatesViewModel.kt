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
import com.dmdiaz.currency.core.domain.models.Resource
import com.dmdiaz.currency.core.domain.usecases.GetRatesUseCase
import com.dmdiaz.currency.libs.util.extensions.cancelIfActive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.joda.money.CurrencyUnit
import org.joda.money.CurrencyUnit.USD
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val getRatesUseCase: GetRatesUseCase,
) : ViewModel() {


    private val _state = MutableStateFlow(RatesState(baseCurrencyUnit = handle["currency_unit"] ?: USD))
    val state = _state.asStateFlow()

    init {
        getRates(state.value.baseCurrencyUnit)
    }

    @MainThread
    fun onEvent(event: RatesEvent){
        when(event){
            is RatesEvent.CurrencyUnitChanged -> {
                val currencyUnit = event.currencyUnit
                val previousState = _state.getAndUpdate { it.copy(baseCurrencyUnit = currencyUnit) }

                if (previousState.baseCurrencyUnit != currencyUnit){
                    handle["currency_unit"] = currencyUnit
                    getRates(currencyUnit)
                }
            }

            RatesEvent.Retry -> {
                getRates(state.value.baseCurrencyUnit)
            }
        }
    }

    private var getRatesJob: Job? = null
    private fun getRates(currencyUnit: CurrencyUnit) {
        getRatesJob.cancelIfActive()
        _state.update { it.copy(rates = Resource.Loading) }
        getRatesJob = viewModelScope.launch {
            getRatesUseCase(currencyUnit).collect { results ->
                results.fold(
                    ifLeft = { failure ->
                        _state.update { it.copy(rates = Resource.Failed(failure)) }
                    },
                    ifRight = { success ->
                        _state.update { it.copy(rates = Resource.Success(success)) }
                    }
                )
            }
        }
    }
}