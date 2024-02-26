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

package com.dmdiaz.currency.libs.designsystem.icon

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

val Icons.SwapHorizontal: ImageVector
    get() {
        if (_swapHorizontal != null) {
            return _swapHorizontal!!
        }
        _swapHorizontal = imageVector(
        	name = "SwapHorizontal",
        	width = 24f,
        	height = 24f,
        	viewportWidth = 24.0f,
        	viewportHeight = 24.0f
        ) {
            path(
            	fill = SolidColor(Color(0xFF000000))
            ) {
                moveTo(21.0f, 9.0f)
                lineTo(17.0f, 5.0f)
                verticalLineTo(8.0f)
                horizontalLineTo(10.0f)
                verticalLineTo(10.0f)
                horizontalLineTo(17.0f)
                verticalLineTo(13.0f)
                moveTo(7.0f, 11.0f)
                lineTo(3.0f, 15.0f)
                lineTo(7.0f, 19.0f)
                verticalLineTo(16.0f)
                horizontalLineTo(14.0f)
                verticalLineTo(14.0f)
                horizontalLineTo(7.0f)
                verticalLineTo(11.0f)
                close()
            }
        }
        return _swapHorizontal!!
    }

private var _swapHorizontal: ImageVector? = null
