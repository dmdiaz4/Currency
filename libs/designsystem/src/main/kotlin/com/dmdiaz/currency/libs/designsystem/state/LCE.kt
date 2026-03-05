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

package com.dmdiaz.currency.libs.designsystem.state

import arrow.core.Nel
import arrow.core.raise.Raise
import arrow.core.raise.RaiseDSL
import arrow.core.raise.recover
import kotlin.experimental.ExperimentalTypeInference


sealed interface LCE<out E, out C> {
    data object Loading : LCE<Nothing, Nothing>
    data class Content<C>(val value: C) : LCE<Nothing, C>
    data class Error<E>(val error: E) : LCE<E, Nothing>
}

inline fun <E, A, B> LCE<E, A>.mapContent(f: (content: A) -> B): LCE<E, B> = when (this) {
    is LCE.Content -> LCE.Content(f(value))
    is LCE.Error -> LCE.Error(error)
    LCE.Loading -> LCE.Loading
}

inline fun <C, A, B> LCE<A, C>.mapError(f: (error: A) -> B): LCE<B, C> = when (this) {
    is LCE.Content -> LCE.Content(value)
    is LCE.Error -> LCE.Error(f(error))
    LCE.Loading -> LCE.Loading
}

fun <E, C> LCE<E, C>.getOrNull(): C? = when (this) {
    is LCE.Content -> value
    else -> null
}

fun <E, C> LCE<Nel<E>, C>?.hasErrors(): Boolean = isError()

fun <E, C> LCE<Nel<E>, C>?.errors(): List<E> = when (this) {
    is LCE.Error<Nel<E>> -> this.error
    else -> emptyList()
}

fun <E, C> LCE<E, C>?.isLoading(): Boolean = when (this) {
    is LCE.Loading -> true
    else -> false
}

fun <E, C> LCE<E, C>?.isContent(): Boolean = when (this) {
    is LCE.Content -> true
    else -> false
}

fun <E, C> LCE<E, C>?.isError(): Boolean = when (this) {
    is LCE.Error -> true
    else -> false
}

@JvmInline
value class LceRaise<E>(internal val raise: Raise<LCE<E, Nothing>>) : Raise<LCE<E, Nothing>> by raise {
    @RaiseDSL
    fun <A> LCE<E, A>.bind(): A = when (this) {
        is LCE.Content -> value
        is LCE.Error -> raise.raise(this)
        LCE.Loading -> raise.raise(LCE.Loading)
    }
}

@OptIn(ExperimentalTypeInference::class)
inline fun <E, A> lce(@BuilderInference block: LceRaise<E>.() -> A): LCE<E, A> =
    recover({ LCE.Content(block(LceRaise(this))) }) { e: LCE<E, Nothing> -> e }
