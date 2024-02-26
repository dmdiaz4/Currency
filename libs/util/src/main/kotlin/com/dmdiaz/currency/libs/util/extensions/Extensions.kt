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

package com.dmdiaz.currency.libs.util.extensions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.joda.money.format.MoneyAmountStyle
import org.joda.money.format.MoneyFormatter
import org.joda.money.format.MoneyFormatterBuilder
import java.util.Locale
import java.util.regex.Pattern


fun Job?.cancelIfActive() {
    if (this?.isActive == true) {
        cancel()
    }
}

suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
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

private val nonNumericPattern = Pattern.compile("[^0-9]")
private val leadingZeroPattern = Pattern.compile("^0+")
fun String.toMoney(currencyUnit: CurrencyUnit): Money{
    var cleaned = this
    cleaned = nonNumericPattern.matcher(cleaned).replaceAll("")
    cleaned = leadingZeroPattern.matcher(cleaned).replaceAll("")

    val money = if (TextUtils.isEmpty(cleaned)) {
        // If null or empty, set to zero
        Money.zero(currencyUnit)
    } else {
        // Set to entered amount
        try {
            val longValue = cleaned.toLong()
            Money.zero(currencyUnit).plusMinor(longValue)
        }catch (e : NumberFormatException){
            Money.zero(currencyUnit).apply {
                cleaned.chunked(5).forEach { valueChunk ->
                    plusMinor(valueChunk.toLong())
                }
            }
        }
    }
    return money
}

private fun cleanNumericString(input: String): String {
    if (TextUtils.isEmpty(input)) return input
    var cleaned = input
    cleaned = nonNumericPattern.matcher(cleaned).replaceAll("")
    cleaned = leadingZeroPattern.matcher(cleaned).replaceAll("")
    return cleaned
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

fun <A, B: Any> Flow<Either<A,B?>>.filterNotNullRight(): Flow<Either<A,B>> = transform<Either<A,B?>, Either<A,B>> { value ->
    value.fold(
        ifLeft = {
            return@transform emit(it.left())
        },
        ifRight = {
            if (it!= null){
                return@transform emit(it.right())
            }
        }
    )
}
fun <A,B> Flow<Either<A,B>>.onEachRight(action: suspend (B) -> Unit): Flow<Either<A, B>> = this.onEach { it.fold(ifRight = { action(it) }, ifLeft = {}) }
inline fun <A,B,C> Flow<Either<A, B>>.mapRight(crossinline transform: suspend (value: B) -> C): Flow<Either<A, C>> = this.map { it.map { transform(it) } }

suspend fun <A,B> FlowCollector<Either<A, B>>.emitLeft(left: A) = emit(left.left())

suspend fun <A,B> FlowCollector<Either<A, B>>.emitRight(right: B) = emit(right.right())

suspend fun <A,B> FlowCollector<Either<A, B>>.emitAllLeft(leftFlow: Flow<A>) = emitAll(leftFlow.map { it.left() })

suspend fun <A,B> FlowCollector<Either<A, B>>.emitAllRight(rightFlow: Flow<B>) = emitAll(rightFlow.map { it.right() })

@OptIn(ExperimentalCoroutinesApi::class)
inline fun <A,B,C> Flow<Either<A, B>>.flatMapRightLatest(crossinline transform: suspend (value: B) -> Flow<Either<A,C>>): Flow<Either<A,C>> =
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