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

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.example.currency.databinding.ItemRateBinding
import com.example.currency.domain.models.Rate
import com.example.currency.ui.BaseAdapter.BaseHolder
import com.example.currency.util.createSortedList
import com.example.currency.util.singleClickListener
import com.example.currency.util.toFormattedString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP

class RateAdapter(
    private val amount: StateFlow<Money>,
    private val list: Flow<List<Rate>>,
    private val onClick: (Money) -> Unit
) : BaseAdapter<BaseHolder<ItemRateBinding>>() {


    private val data = createSortedList(object : SortedListAdapterCallback<Rate>(this) {
        override fun compare(o1: Rate, o2: Rate): Int {
            return o1.currencyUnit.code.compareTo(o2.currencyUnit.code)
        }

        override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(item1: Rate, item2: Rate): Boolean {
            return TextUtils.equals(item1.currencyUnit.code, item2.currencyUnit.code)
        }

    })

    init {
        lifecycleScope.launchWhenStarted {
            amount.collect { notifyDataSetChanged() }
        }
        lifecycleScope.launchWhenStarted {
            list.collect { newList ->
                data.replaceAll(newList)
            }
        }
    }


    override fun getItemCount(): Int = data.size()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ItemRateBinding> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRateBinding.inflate(inflater, parent, false)
        return BaseHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder<ItemRateBinding>, position: Int) {
        val binding = holder.binding
        val currency: CurrencyUnit = data.get(position).currencyUnit
        val rate: BigDecimal = data.get(position).rate

        binding.currency.text = currency.code

        val converted = Money.zero(currency).plus(amount.value.multipliedBy(rate, HALF_UP).amount, HALF_UP)

        binding.amount.text = converted.toFormattedString()

        binding.root.singleClickListener(hideKeyboardAfterClick = true) { onClick(converted) }
    }

}