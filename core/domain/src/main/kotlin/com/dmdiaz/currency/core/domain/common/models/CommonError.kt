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

package com.dmdiaz.currency.core.domain.common.models

import java.io.IOException
import java.sql.SQLException

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature/domain specific error should extend have a [CommonError].
 */
sealed interface CommonError

sealed interface CommonLocalError: CommonError

sealed interface CommonRemoteError: CommonError

data class UnknownError(
    val cause: Throwable
): CommonError, CommonLocalError, CommonRemoteError

data object NoInternetError : CommonError, CommonRemoteError

data class DatabaseError(
    val cause: SQLException
)

data class HttpError(
    val code: Int,
    val message: String,
    val body: String
): CommonError, CommonRemoteError

data class IOError(
    val cause: IOException
):CommonError, CommonLocalError, CommonRemoteError


