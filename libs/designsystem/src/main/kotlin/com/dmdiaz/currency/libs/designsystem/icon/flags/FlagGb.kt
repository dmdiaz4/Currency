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

val Icons.FlagGb: ImageVector
    get() {
        if (_gb != null) {
            return _gb!!
        }
        _gb = Builder(name = "Gb", defaultWidth = 512.0.dp, defaultHeight = 512.0.dp, viewportWidth
        = 512.0f, viewportHeight = 512.0f).apply {
            path(fill = SolidColor(Color(0xFF012169)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(0.0f, 0.0f)
                horizontalLineToRelative(512.0f)
                verticalLineToRelative(512.0f)
                horizontalLineTo(0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(512.0f, 0.0f)
                verticalLineToRelative(64.0f)
                lineTo(322.0f, 256.0f)
                lineToRelative(190.0f, 187.0f)
                verticalLineToRelative(69.0f)
                horizontalLineToRelative(-67.0f)
                lineTo(254.0f, 324.0f)
                lineTo(68.0f, 512.0f)
                horizontalLineTo(0.0f)
                verticalLineToRelative(-68.0f)
                lineToRelative(186.0f, -187.0f)
                lineTo(0.0f, 74.0f)
                verticalLineTo(0.0f)
                horizontalLineToRelative(62.0f)
                lineToRelative(192.0f, 188.0f)
                lineTo(440.0f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFC8102E)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveToRelative(184.0f, 324.0f)
                lineToRelative(11.0f, 34.0f)
                lineTo(42.0f, 512.0f)
                lineTo(0.0f, 512.0f)
                verticalLineToRelative(-3.0f)
                close()
                moveTo(308.0f, 312.0f)
                lineTo(362.0f, 320.0f)
                lineTo(512.0f, 467.0f)
                verticalLineToRelative(45.0f)
                close()
                moveTo(512.0f, 0.0f)
                lineTo(320.0f, 196.0f)
                lineToRelative(-4.0f, -44.0f)
                lineTo(466.0f, 0.0f)
                close()
                moveTo(0.0f, 1.0f)
                lineToRelative(193.0f, 189.0f)
                lineToRelative(-59.0f, -8.0f)
                lineTo(0.0f, 49.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(176.0f, 0.0f)
                verticalLineToRelative(512.0f)
                horizontalLineToRelative(160.0f)
                verticalLineTo(0.0f)
                close()
                moveTo(0.0f, 176.0f)
                verticalLineToRelative(160.0f)
                horizontalLineToRelative(512.0f)
                verticalLineTo(176.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFC8102E)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(0.0f, 208.0f)
                verticalLineToRelative(96.0f)
                horizontalLineToRelative(512.0f)
                verticalLineToRelative(-96.0f)
                close()
                moveTo(208.0f, 0.0f)
                verticalLineToRelative(512.0f)
                horizontalLineToRelative(96.0f)
                verticalLineTo(0.0f)
                close()
            }
        }
            .build()
        return _gb!!
    }

private var _gb: ImageVector? = null

@Preview
@Composable
fun FlagGbPreview() {
    Image(imageVector = Icons.FlagGb, contentDescription = null)
}
