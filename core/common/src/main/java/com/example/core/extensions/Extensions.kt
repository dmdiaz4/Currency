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

package com.example.core.extensions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.core.models.Failure
import com.example.core.models.Failure.UnknownError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.joda.money.Money
import org.joda.money.format.MoneyAmountStyle
import org.joda.money.format.MoneyFormatter
import org.joda.money.format.MoneyFormatterBuilder
import java.util.*

// Helper extensions
inline fun <T, R> T.tryOrFailure(action: T.() -> R): Either<Failure, R> = try {
    action(this).right()
} catch (t: Throwable) {
    UnknownError(t).left()
}

fun Job?.cancelIfActive() {
    if (this?.isActive == true) {
        cancel()
    }
}


private val moneyFormatterMap = HashMap<Int, MoneyFormatter>()

fun Money.toFormattedString(): String {
    if (!moneyFormatterMap.containsKey(currencyUnit.numericCode)) {
        var formatBuilder = MoneyFormatterBuilder()
        formatBuilder = formatBuilder.appendLiteral(currencyUnit.symbol)
            .appendAmount(MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA)
        val newFormatter = formatBuilder.toFormatter()
        moneyFormatterMap[currencyUnit.numericCode] = newFormatter
    }

    val currentLocale = Locale.getDefault()
    val formatter = moneyFormatterMap[currencyUnit.numericCode]!!.withLocale(currentLocale)

    return formatter.print(this)
}

inline fun <reified T> RecyclerView.Adapter<*>.createSortedList(
    callback: SortedList.Callback<T>
) = SortedList(T::class.java, callback)


private fun hideKeyboard(context: Context, view: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        view.windowInsetsController?.hide(WindowInsets.Type.ime())
    } else {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}


fun View.singleClickListener(hideKeyboardAfterClick: Boolean? = false, onClick: (View) -> Unit) {
    setOnClickListener {
        onClick(it)
        isClickable = false // avoid duplicate clicks
        when (hideKeyboardAfterClick) {
            true -> hideKeyboard(context, it)
            else -> {}
        }

        postDelayed({
            isClickable = true // enable click after delay
        }, 300L)
    }
}

inline fun <A,B,C> Flow<Either<A, B>>.mapRight(crossinline transform: suspend (value: B) -> C): Flow<Either<A, C>> = this.map { it.map { transform(it) } }


suspend fun <A,B> FlowCollector<Either<A, B>>.emitLeft(left: A) = emit(left.left())

suspend fun <A,B> FlowCollector<Either<A, B>>.emitRight(right: B) = emit(right.right())

suspend fun <A,B> FlowCollector<Either<A, B>>.emitAllLeft(leftFlow: Flow<A>) = emitAll(leftFlow.map { it.left() })

suspend fun <A,B> FlowCollector<Either<A, B>>.emitAllRight(rightFlow: Flow<B>) = emitAll(rightFlow.map { it.right() })

@OptIn(ExperimentalCoroutinesApi::class)
inline fun <A,B,C> Flow<Either<A, B>>.eitherFlatMapLatest(crossinline transform: suspend (value: B) -> Flow<Either<A,C>>): Flow<Either<A,C>> =
    flatMapLatest {latest ->
        flow {
            latest.fold(
                ifLeft ={ left -> emitLeft(left)},
                ifRight = { right -> emitAll(transform(right)) }
            )
        }
    }

val Context.connectivityManager: ConnectivityManager
    get() = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager