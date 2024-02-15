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

package com.dmdiaz.currency.libs.ui.extensions

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Fragment.viewBinding(crossinline bind: (View) -> T): ReadOnlyProperty<Fragment, T> {
    return object : ReadOnlyProperty<Fragment, T> {
        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            (requireView().getTag(property.name.hashCode()) as? T)?.let { return it }
            return bind(requireView()).also {
                requireView().setTag(property.name.hashCode(), it)
            }
        }
    }
}

inline fun <reified T : ViewBinding> FragmentActivity.viewBinding(crossinline bind: (View) -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        val getContentView: FragmentActivity.() -> View = {
            checkNotNull(findViewById<ViewGroup>(android.R.id.content).getChildAt(0)) {
                "Call setContentView or Use Activity's secondary constructor passing layout res id."
            }
        }
        bind(getContentView())
    }
}