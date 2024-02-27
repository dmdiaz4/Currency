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
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Icons.FlagUs: ImageVector
    get() {
        if (_us != null) {
            return _us!!
        }
        _us = Builder(name = "Us", defaultWidth = 512.0.dp, defaultHeight = 512.0.dp, viewportWidth
        = 512.0f, viewportHeight = 512.0f).apply {
            path(fill = SolidColor(Color(0xFFbd3d44)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(0.0f, 0.0f)
                horizontalLineToRelative(512.0f)
                verticalLineToRelative(512.0f)
                horizontalLineTo(0.0f)
            }
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = SolidColor(Color(0xFFFFFFFF)),
                strokeLineWidth = 40.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(0.0f, 58.0f)
                horizontalLineToRelative(512.0f)
                moveTo(0.0f, 137.0f)
                horizontalLineToRelative(512.0f)
                moveTo(0.0f, 216.0f)
                horizontalLineToRelative(512.0f)
                moveTo(0.0f, 295.0f)
                horizontalLineToRelative(512.0f)
                moveTo(0.0f, 374.0f)
                horizontalLineToRelative(512.0f)
                moveTo(0.0f, 453.0f)
                horizontalLineToRelative(512.0f)
            }
            path(fill = SolidColor(Color(0xFF192f5d)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(0.0f, 0.0f)
                horizontalLineToRelative(390.0f)
                verticalLineToRelative(275.0f)
                horizontalLineTo(0.0f)
                close()
            }
        }
            .build()
        return _us!!
    }

private var _us: ImageVector? = null

@Preview
@Composable
fun FlagUsPreview() {
    Image(imageVector = Icons.FlagUs, contentDescription = null)
}
