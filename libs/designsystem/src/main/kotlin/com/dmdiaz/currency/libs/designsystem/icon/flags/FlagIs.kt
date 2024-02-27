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

package com.dmdiaz.currency.libs.designsystem.icon.flags

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Icons.FlagIs: ImageVector
    get() {
        if (_is != null) {
            return _is!!
        }
        _is = Builder(name = "Is", defaultWidth = 512.0.dp, defaultHeight = 512.0.dp, viewportWidth
        = 512.0f, viewportHeight = 512.0f).apply {
            group {
                path(fill = SolidColor(Color(0xFF003897)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                    moveTo(-90.0f, 0.0f)
                    lineToRelative(711.113f, 0.0f)
                    lineToRelative(0.0f, 512.001f)
                    lineTo(-90.0f, 512.001f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                    moveTo(-90.0f, 199.112f)
                    lineToRelative(199.112f, 0.0f)
                    lineTo(109.112f, 0.0f)
                    lineToRelative(113.778f, 0.0f)
                    lineToRelative(0.0f, 199.112f)
                    lineToRelative(398.223f, 0.0f)
                    lineToRelative(0.0f, 113.778f)
                    lineTo(222.89f, 312.89f)
                    lineToRelative(0.0f, 199.112f)
                    lineTo(109.112f, 512.001f)
                    lineTo(109.112f, 312.89f)
                    lineTo(-90.0f, 312.89f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFd72828)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                    moveTo(-90.0f, 227.556f)
                    lineToRelative(227.556f, 0.0f)
                    lineTo(137.556f, 0.0f)
                    lineToRelative(56.889f, 0.0f)
                    lineToRelative(0.0f, 227.556f)
                    lineToRelative(426.668f, 0.0f)
                    lineToRelative(0.0f, 56.889f)
                    lineTo(194.445f, 284.445f)
                    lineToRelative(0.0f, 227.556f)
                    lineToRelative(-56.889f, 0.0f)
                    lineTo(137.556f, 284.445f)
                    lineTo(-90.0f, 284.445f)
                    close()
                }
            }
        }
            .build()
        return _is!!
    }

private var _is: ImageVector? = null


@Preview
@Composable
fun FlagIsPreview() {
    Image(imageVector = Icons.FlagIs, contentDescription = null)
}