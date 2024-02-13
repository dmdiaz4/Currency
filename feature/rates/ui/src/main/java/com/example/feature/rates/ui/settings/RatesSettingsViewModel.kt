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

package com.example.feature.rates.ui.settings

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.extensions.cancelIfActive
import com.example.core.ui.states.LoadingState
import com.example.feature.rates.domain.usecases.GetDisabledCurrencyFormatsUseCase
import com.example.feature.rates.domain.usecases.SaveCurrencyFormatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesSettingsViewModel @Inject constructor(
    private val getCurrencyFormatsUseCase: GetDisabledCurrencyFormatsUseCase,
    private val saveCurrencyFormatsUseCase: SaveCurrencyFormatsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RatesSettingsState())
    val state = _state.asStateFlow()

    init {
        getCurrencyFormats()
    }

    @MainThread
    fun onEvent(event: RatesSettingsEvent){
        when(event){
            is RatesSettingsEvent.FormatsChanged -> {
                viewModelScope.launch {
                    saveCurrencyFormatsUseCase(event.formats.toSet())
                }
            }
        }
    }


    private var getCurrencyFormatsJob: Job? = null
    private fun getCurrencyFormats(){
        getCurrencyFormatsJob.cancelIfActive()
        _state.update { it.copy(formats = LoadingState.Loading) }
        getCurrencyFormatsJob = viewModelScope.launch {
            getCurrencyFormatsUseCase().collect{ results ->
                results.fold(
                    ifLeft = { failure ->
                        _state.update { it.copy(formats = LoadingState.Failed(failure)) }
                    },
                    ifRight = { success ->
                        _state.update { it.copy(formats = LoadingState.Success(success.toList())) }
                    }
                )
            }
        }
    }

}