/*
 * MIT License
 *
 * Copyright (c) 2021 David Diaz
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

package com.example.currency.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency.domain.models.Failure
import com.example.currency.domain.models.Rate
import com.example.currency.domain.usecases.GetLatestRatesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.launch
import org.joda.money.CurrencyUnit
import org.joda.money.CurrencyUnit.USD
import org.joda.money.Money

class MainViewModel(
    private val handle: SavedStateHandle,
    private val getLatestRates: GetLatestRatesUseCase,
) : ViewModel() {

    private val _enteredAmount = MutableStateFlow<Money>(handle.get("money") ?: Money.zero(USD))
    val enteredAmount = _enteredAmount.asStateFlow()

    private val currency = enteredAmount
        .map { it.currencyUnit }
        .stateIn(viewModelScope, Lazily, USD)

    private val _rates = MutableStateFlow(listOf<Rate>())
    val rates = _rates.asStateFlow()


    private val _failures = MutableSharedFlow<Failure>()
    val failures = _failures.asSharedFlow()


    fun onAmountChanged(money: Money) {
        _enteredAmount.value = money
        handle["money"] = money
    }

    init {
        viewModelScope.launch {
            currency.collect {
                fetchJob?.cancelAndJoin()
                fetchLatestRates(it)
            }
        }
    }

    private var fetchJob: Job? = null

    private fun fetchLatestRates(currencyUnit: CurrencyUnit) {
        fetchJob = viewModelScope.launch {
            getLatestRates(currencyUnit).collect { results ->
                results.fold(
                    ifLeft = { failure ->
                        _failures.emit(failure)
                        if (failure is Failure.UnknownError) failure.throwable.printStackTrace()
                    },
                    ifRight = { rates -> _rates.value = rates.rates }
                )
            }
        }
    }
}