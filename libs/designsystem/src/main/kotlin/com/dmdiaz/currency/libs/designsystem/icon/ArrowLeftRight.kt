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

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val CurrencyIcons.ArrowLeftRight: ImageVector
    get() {
        if (_arrowLeftRight != null) {
            return _arrowLeftRight!!
        }
        _arrowLeftRight = Builder(
            name = "ArrowLeftRight",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000))
            ) {
                moveTo(6.45f, 17.45f)
                lineTo(1.0f, 12.0f)
                lineTo(6.45f, 6.55f)
                lineTo(7.86f, 7.96f)
                lineTo(4.83f, 11.0f)
                horizontalLineTo(19.17f)
                lineTo(16.14f, 7.96f)
                lineTo(17.55f, 6.55f)
                lineTo(23.0f, 12.0f)
                lineTo(17.55f, 17.45f)
                lineTo(16.14f, 16.04f)
                lineTo(19.17f, 13.0f)
                horizontalLineTo(4.83f)
                lineTo(7.86f, 16.04f)
                lineTo(6.45f, 17.45f)
                close()
            }
        }.build()
        return _arrowLeftRight!!
    }

private var _arrowLeftRight: ImageVector? = null
