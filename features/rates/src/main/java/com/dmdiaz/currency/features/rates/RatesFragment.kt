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

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dmdiaz.currency.features.rates.databinding.FragmentRatesBinding
import com.dmdiaz.currency.libs.models.Failure
import com.dmdiaz.currency.libs.ui.extensions.viewBinding
import com.dmdiaz.currency.libs.ui.states.LoadingState
import com.dmdiaz.currency.libs.ui.views.MoneyEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.joda.money.Money

@AndroidEntryPoint
class RatesFragment : Fragment(R.layout.fragment_rates) {

    private val binding by viewBinding (FragmentRatesBinding::bind)

    private val viewModel by viewModels<RatesViewModel>()

    private lateinit var adapter: RateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RateAdapter { converted ->
            viewModel.onEvent(RatesEvent.AmountChanged(converted))
        }

        binding.rateList.adapter = adapter

        binding.priceInput.moneyUpdateListener = object : MoneyEditText.MoneyUpdatedListener {
            override fun onUpdated(total: Money) {
                viewModel.onEvent(RatesEvent.AmountChanged(total))
            }
        }

        setUpObservers(viewLifecycleOwner)
    }

    private fun setUpObservers(lifecycleOwner: LifecycleOwner) {

        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect { state ->
                    binding.priceInputLayout.hint = "Enter Currency Amount in " + state.enteredAmount.currencyUnit.code
                    binding.priceInput.money = state.enteredAmount

                    when(state.convertedAmounts){
                        is LoadingState.Failed -> {
                            val failure = state.convertedAmounts.exception
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
                            adapter.updateList(state.convertedAmounts.data)
                        }
                        else -> {
                            adapter.updateList(emptyList())
                        }
                    }
                }
            }
        }
    }
}