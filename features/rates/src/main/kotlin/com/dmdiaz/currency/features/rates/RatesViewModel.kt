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
import com.dmdiaz.currency.core.domain.usecases.GetConvertedAmountsUseCase
import com.dmdiaz.currency.libs.util.extensions.cancelIfActive
import com.dmdiaz.currency.core.domain.models.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.joda.money.CurrencyUnit.USD
import org.joda.money.Money
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val getConvertedAmountsUseCase: GetConvertedAmountsUseCase,
) : ViewModel() {


    private val _state = MutableStateFlow(RatesState(enteredAmount = handle["money"] ?: Money.zero(USD)))
    val state = _state.asStateFlow()

    init {
        getConvertedAmounts(state.value.enteredAmount)
    }

    @MainThread
    fun onEvent(event: RatesEvent){
        when(event){
            is RatesEvent.AmountChanged -> {
                val money = event.money
                val previousState = _state.getAndUpdate { it.copy(enteredAmount = money) }
                handle["money"] = money

                if (previousState.enteredAmount != money){
                    getConvertedAmounts(money)
                }
            }
        }
    }

    private var getConvertedAmountsJob: Job? = null
    private fun getConvertedAmounts(amount: Money) {
        getConvertedAmountsJob.cancelIfActive()
        _state.update { it.copy(convertedAmounts = Resource.Loading) }
        getConvertedAmountsJob = viewModelScope.launch {
            getConvertedAmountsUseCase(amount).collect { results ->
                results.fold(
                    ifLeft = { failure ->
                        _state.update { it.copy(convertedAmounts = Resource.Failed(failure)) }
                    },
                    ifRight = { success ->
                        _state.update { it.copy(convertedAmounts = Resource.Success(success)) }
                    }
                )
            }
        }
    }
}