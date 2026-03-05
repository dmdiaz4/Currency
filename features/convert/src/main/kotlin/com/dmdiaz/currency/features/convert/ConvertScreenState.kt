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

package com.dmdiaz.currency.features.convert


import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmdiaz.currency.features.convert.ConvertScreenEvent.AmountChanged
import com.dmdiaz.currency.features.convert.ConvertScreenEvent.AmountUpdated
import com.dmdiaz.currency.features.convert.ConvertScreenEvent.Retry
import com.dmdiaz.currency.features.convert.viewModel.ConvertViewModelEvent
import com.dmdiaz.currency.features.convert.viewModel.ConvertViewModelUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun rememberConvertScreenState(
    uiState: StateFlow<ConvertViewModelUIState>,
    onEvent: (ConvertViewModelEvent) -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): ConvertScreenState{

    val uiStateCompose = uiState.collectAsStateWithLifecycle()

    return remember(
        uiStateCompose, onEvent, coroutineScope
    ){
        ConvertScreenState(
            uiState = uiStateCompose,
            onEvent = onEvent,
            coroutineScope = coroutineScope
        )
    }
}

@Stable
class ConvertScreenState(
    uiState: State<ConvertViewModelUIState>,
    private val onEvent: (ConvertViewModelEvent) -> Unit,
    private val coroutineScope: CoroutineScope
){

    private var enteredAmount by mutableStateOf(uiState.value.amount)

    val uiState by derivedStateOf {
        ConvertScreenUIState(
            enteredAmount = this.enteredAmount,
            convertedAmounts = uiState.value.convertedAmounts
        )
    }

    private var amountChJob: Job? = null
    fun onEvent(event: ConvertScreenEvent){
        when(event){
            is AmountChanged -> {
                enteredAmount = event.money
                onEvent(ConvertViewModelEvent.AmountChanged(event.money))
            }
            is AmountUpdated -> {
                enteredAmount = event.money

                // Cancel the previous job if it exists
                amountChJob?.cancel()
                amountChJob = coroutineScope.launch {
                    delay(333)
                    onEvent(ConvertViewModelEvent.AmountChanged(event.money))
                }
            }
            Retry -> {
                onEvent(ConvertViewModelEvent.Retry)
            }
        }
    }
}