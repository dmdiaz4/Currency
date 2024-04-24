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

package com.dmdiaz.currency.core.ui

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.recover
import kotlin.experimental.ExperimentalTypeInference


sealed interface Lce<out E, out C> {
    data object Loading : Lce<Nothing, Nothing>
    data class Content<C>(val value: C) : Lce<Nothing, C>
    data class Failure<E>(val error: E) : Lce<E, Nothing>
}


context(Raise<Lce<E, Nothing>>)
fun <E, C> Lce<E, C>.bind(): C = when (this) {
    is Lce.Content -> value
    is Lce.Failure -> raise(this)
    Lce.Loading -> raise(Lce.Loading)
}

context(Raise<Lce<E, Nothing>>)
fun <E, C> Either<E, C>.bind(): C = fold(
    ifLeft = { raise(Lce.Failure(it)) },
    ifRight = { it }
)

@OptIn(ExperimentalTypeInference::class)
inline fun <E, C> lce(@BuilderInference block: Raise<Lce<E, Nothing>>.() -> C): Lce<E, C> =
    recover({ Lce.Content(block(this)) }) { e: Lce<E, Nothing> -> e }






