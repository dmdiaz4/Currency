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

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.currency.databinding.ActivityMainBinding
import com.example.currency.domain.models.Failure.NetworkError
import com.example.currency.domain.models.Failure.NetworkUnavailable
import com.example.currency.ui.MoneyEditText.MoneyUpdatedListener
import kotlinx.coroutines.flow.collect
import org.joda.money.Money
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<MainViewModel>()

    private lateinit var adapter: RateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter =
            RateAdapter(amount = viewModel.enteredAmount, list = viewModel.rates) { converted ->
                viewModel.onAmountChanged(converted)
            }

        binding.rateList.setHasFixedSize(true)
        binding.rateList.adapter = adapter

        binding.priceInput.moneyUpdateListener = object : MoneyUpdatedListener {
            override fun onUpdated(total: Money) {
                viewModel.onAmountChanged(total)
            }
        }

        setUpObservers()

    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.enteredAmount.collect { money ->
                binding.priceInputLayout.hint =
                    "Enter Currency Amount in " + money.currencyUnit.code
                binding.priceInput.money = money
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.failures.collect { failure ->
                val msg = when (failure) {
                    is NetworkError -> {
                        "Network error, HTTP code: " + failure.code
                    }
                    is NetworkUnavailable -> {
                        "Network unavailable: Trying again soon"
                    }
                    else -> {
                        "Unknown error"
                    }
                }
                Toast.makeText(this@MainActivity, msg, Toast.LENGTH_LONG).show()
            }
        }
    }
}