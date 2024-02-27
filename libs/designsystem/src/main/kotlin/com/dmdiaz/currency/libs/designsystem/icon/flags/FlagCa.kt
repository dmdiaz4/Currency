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

val Icons.FlagCa: ImageVector
    get() {
        if (_ca != null) {
            return _ca!!
        }
        _ca = Builder(name = "Ca", defaultWidth = 512.0.dp, defaultHeight = 512.0.dp, viewportWidth
        = 512.0f, viewportHeight = 512.0f).apply {
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(81.1f, 0.0f)
                horizontalLineToRelative(362.3f)
                verticalLineToRelative(512.0f)
                horizontalLineTo(81.1f)
                close()
            }
            path(fill = SolidColor(Color(0xFFd52b1e)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(-100.0f, 0.0f)
                lineTo(81.1f, 0.0f)
                verticalLineToRelative(512.0f)
                lineTo(-100.0f, 512.0f)
                close()
                moveTo(443.4f, 0.0f)
                horizontalLineToRelative(181.1f)
                verticalLineToRelative(512.0f)
                lineTo(443.4f, 512.0f)
                close()
                moveTo(135.3f, 247.4f)
                lineToRelative(-14.0f, 4.8f)
                lineToRelative(65.4f, 57.5f)
                curveToRelative(5.0f, 14.8f, -1.7f, 19.1f, -6.0f, 26.9f)
                lineToRelative(71.0f, -9.0f)
                lineToRelative(-1.8f, 71.5f)
                lineToRelative(14.8f, -0.5f)
                lineToRelative(-3.3f, -70.9f)
                lineToRelative(71.2f, 8.4f)
                curveToRelative(-4.4f, -9.3f, -8.3f, -14.2f, -4.3f, -29.0f)
                lineToRelative(65.4f, -54.5f)
                lineToRelative(-11.4f, -4.1f)
                curveToRelative(-9.4f, -7.3f, 4.0f, -34.8f, 6.0f, -52.2f)
                curveToRelative(0.0f, 0.0f, -38.1f, 13.1f, -40.6f, 6.2f)
                lineToRelative(-9.9f, -18.5f)
                lineToRelative(-34.6f, 38.0f)
                curveToRelative(-3.8f, 1.0f, -5.4f, -0.6f, -6.3f, -3.8f)
                lineToRelative(16.0f, -79.7f)
                lineToRelative(-25.4f, 14.3f)
                curveToRelative(-2.1f, 0.9f, -4.2f, 0.0f, -5.6f, -2.4f)
                lineToRelative(-24.5f, -49.0f)
                lineToRelative(-25.2f, 50.9f)
                curveToRelative(-1.9f, 1.8f, -3.8f, 2.0f, -5.4f, 0.8f)
                lineToRelative(-24.2f, -13.6f)
                lineToRelative(14.5f, 79.2f)
                curveToRelative(-1.1f, 3.0f, -3.9f, 4.0f, -7.1f, 2.3f)
                lineToRelative(-33.3f, -37.8f)
                curveToRelative(-4.3f, 7.0f, -7.3f, 18.4f, -13.0f, 21.0f)
                curveToRelative(-5.7f, 2.3f, -25.0f, -4.9f, -37.9f, -7.7f)
                curveToRelative(4.4f, 15.9f, 18.2f, 42.3f, 9.5f, 51.0f)
                close()
            }
        }
            .build()
        return _ca!!
    }

private var _ca: ImageVector? = null

@Preview
@Composable
fun FlagCaPreview() {
    Image(imageVector = Icons.FlagCa, contentDescription = null)
}
