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

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.PreferenceDataStore
import androidx.preference.PreferenceFragmentCompat
import com.example.core.models.Failure
import com.example.core.ui.states.LoadingState
import com.example.feature.rates.ui.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.joda.money.CurrencyUnit


@AndroidEntryPoint
class RatesSettingsFragment: PreferenceFragmentCompat() {


    private val viewModel by viewModels<RatesSettingsViewModel>()

    private val dataStore = object :PreferenceDataStore(){

        override fun putStringSet(key: String, values: MutableSet<String>?) {
            viewModel.onEvent(RatesSettingsEvent.FormatsChanged(values?.map { CurrencyUnit.of(it) }?: emptyList()))
        }

        override fun getStringSet(key: String, defValues: MutableSet<String>?): MutableSet<String>? {
            return runBlocking {
                (viewModel.state
                    .first { it.formats is LoadingState.Success<*> }
                    .formats as LoadingState.Success<List<CurrencyUnit>>)
                    .data
                    .map { it.code }
                    .toMutableSet()
            }
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = dataStore
        setPreferencesFromResource(R.xml.rates_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers(viewLifecycleOwner)
    }

    private fun setUpObservers(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when(state.formats){
                        is LoadingState.Failed -> {
                            val failure = state.formats.exception
                            val msg = when(failure){
                                is Failure.NetworkError -> {
                                    "Network error, HTTP code: " + failure.code
                                }
                                is Failure.NetworkUnavailable -> {
                                    "Network unavailable: Trying again soon"
                                }
                                else -> {
                                    "Unknown error"
                                }
                            }
                            Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                        }
                        is LoadingState.Success -> {
                            dataStore.putStringSet("", state.formats.data.map { it.code }.toMutableSet())
                        }
                        else -> {}
                    }
                }
            }
        }
    }

}


