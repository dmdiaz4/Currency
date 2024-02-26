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

public val Icons.SwapVertical: ImageVector
    get() {
        if (_swapVertical != null) {
            return _swapVertical!!
        }
        _swapVertical = imageVector(
        	name = "SwapVertical",
        	width = 24f,
        	height = 24f,
        	viewportWidth = 24.0f,
        	viewportHeight = 24.0f
        ) {
            path(
            	fill = SolidColor(Color(0xFF000000))
            ) {
                moveTo(9.0f, 3.0f)
                lineTo(5.0f, 7.0f)
                horizontalLineTo(8.0f)
                verticalLineTo(14.0f)
                horizontalLineTo(10.0f)
                verticalLineTo(7.0f)
                horizontalLineTo(13.0f)
                moveTo(16.0f, 17.0f)
                verticalLineTo(10.0f)
                horizontalLineTo(14.0f)
                verticalLineTo(17.0f)
                horizontalLineTo(11.0f)
                lineTo(15.0f, 21.0f)
                lineTo(19.0f, 17.0f)
                horizontalLineTo(16.0f)
                close()
            }
        }
        return _swapVertical!!
    }

private var _swapVertical: ImageVector? = null
