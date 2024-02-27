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

val Icons.FlagPh: ImageVector
    get() {
        if (_ph != null) {
            return _ph!!
        }
        _ph = Builder(name = "Ph", defaultWidth = 512.0.dp, defaultHeight = 512.0.dp, viewportWidth
        = 512.0f, viewportHeight = 512.0f).apply {
            path(fill = SolidColor(Color(0xFF0038a8)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(0.0f, 0.0f)
                horizontalLineToRelative(512.0f)
                verticalLineToRelative(256.0f)
                horizontalLineTo(0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFce1126)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(0.0f, 256.0f)
                horizontalLineToRelative(512.0f)
                verticalLineToRelative(256.0f)
                horizontalLineTo(0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(443.4f, 256.0f)
                lineTo(0.0f, 512.0f)
                verticalLineTo(0.0f)
            }
            path(fill = SolidColor(Color(0xFFfcd116)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveToRelative(25.2f, 44.4f)
                lineToRelative(15.4f, 13.3f)
                lineToRelative(17.9f, -9.8f)
                lineToRelative(-8.0f, 18.7f)
                lineToRelative(15.0f, 14.0f)
                lineTo(45.0f, 78.9f)
                lineToRelative(-8.6f, 18.4f)
                lineToRelative(-4.7f, -19.8f)
                lineToRelative(-20.2f, -2.6f)
                lineTo(29.0f, 64.4f)
                close()
                moveTo(372.1f, 229.0f)
                lineToRelative(0.4f, 20.3f)
                lineToRelative(19.3f, 6.7f)
                lineToRelative(-19.3f, 6.7f)
                lineToRelative(-0.4f, 20.3f)
                lineToRelative(-12.3f, -16.2f)
                lineToRelative(-19.5f, 6.0f)
                lineTo(352.0f, 256.0f)
                lineToRelative(-11.7f, -16.7f)
                lineToRelative(19.5f, 5.9f)
                close()
                moveTo(36.5f, 414.7f)
                lineToRelative(8.6f, 18.4f)
                lineToRelative(20.3f, -1.7f)
                lineToRelative(-14.8f, 14.0f)
                lineToRelative(7.9f, 18.7f)
                lineToRelative(-17.9f, -9.8f)
                lineToRelative(-15.4f, 13.3f)
                lineToRelative(3.9f, -20.0f)
                lineToRelative(-17.5f, -10.5f)
                lineToRelative(20.2f, -2.6f)
                close()
                moveTo(158.9f, 148.0f)
                lineToRelative(-6.6f, 6.6f)
                lineToRelative(3.2f, 50.3f)
                lineToRelative(-3.3f, 0.3f)
                lineToRelative(-6.0f, -45.9f)
                lineToRelative(-5.5f, 5.4f)
                lineToRelative(8.2f, 41.0f)
                arcToRelative(51.0f, 51.0f, 0.0f, false, false, -18.4f, 7.7f)
                lineToRelative(-23.3f, -34.8f)
                horizontalLineToRelative(-7.7f)
                lineToRelative(28.2f, 36.8f)
                lineToRelative(-2.5f, 2.1f)
                lineToRelative(-33.3f, -38.0f)
                horizontalLineToRelative(-9.4f)
                verticalLineToRelative(9.5f)
                lineToRelative(38.0f, 33.3f)
                lineToRelative(-2.2f, 2.5f)
                lineToRelative(-36.8f, -28.2f)
                verticalLineToRelative(7.7f)
                lineToRelative(34.8f, 23.3f)
                arcToRelative(50.9f, 50.9f, 0.0f, false, false, -7.6f, 18.4f)
                lineToRelative(-41.0f, -8.2f)
                lineToRelative(-5.5f, 5.5f)
                lineToRelative(46.0f, 6.0f)
                lineToRelative(-0.4f, 3.4f)
                lineToRelative(-50.3f, -3.3f)
                lineToRelative(-6.7f, 6.6f)
                lineToRelative(6.7f, 6.6f)
                lineToRelative(50.3f, -3.2f)
                lineToRelative(0.3f, 3.3f)
                lineToRelative(-45.9f, 6.0f)
                lineToRelative(5.4f, 5.5f)
                lineToRelative(41.0f, -8.2f)
                arcToRelative(51.0f, 51.0f, 0.0f, false, false, 7.7f, 18.4f)
                lineToRelative(-34.8f, 23.3f)
                verticalLineToRelative(7.7f)
                lineToRelative(36.8f, -28.2f)
                lineToRelative(2.1f, 2.5f)
                lineToRelative(-38.0f, 33.3f)
                verticalLineToRelative(9.4f)
                horizontalLineTo(92.0f)
                lineToRelative(33.3f, -38.0f)
                lineToRelative(2.5f, 2.2f)
                lineToRelative(-28.2f, 36.8f)
                horizontalLineToRelative(7.7f)
                lineToRelative(23.3f, -34.8f)
                arcToRelative(50.8f, 50.8f, 0.0f, false, false, 18.4f, 7.6f)
                lineToRelative(-8.2f, 41.0f)
                lineToRelative(5.5f, 5.5f)
                lineToRelative(6.0f, -46.0f)
                lineToRelative(3.3f, 0.4f)
                lineToRelative(-3.2f, 50.3f)
                lineToRelative(6.6f, 6.7f)
                lineToRelative(6.6f, -6.7f)
                lineToRelative(-3.2f, -50.3f)
                lineToRelative(3.3f, -0.3f)
                lineToRelative(6.0f, 45.9f)
                lineToRelative(5.5f, -5.4f)
                lineToRelative(-8.2f, -41.0f)
                arcToRelative(51.0f, 51.0f, 0.0f, false, false, 18.4f, -7.7f)
                lineToRelative(23.3f, 34.8f)
                horizontalLineToRelative(7.7f)
                lineTo(190.0f, 296.6f)
                lineToRelative(2.5f, -2.1f)
                lineToRelative(33.3f, 38.0f)
                horizontalLineToRelative(9.4f)
                verticalLineTo(323.0f)
                lineToRelative(-38.0f, -33.3f)
                lineToRelative(2.2f, -2.5f)
                lineToRelative(36.8f, 28.2f)
                verticalLineToRelative(-7.7f)
                lineToRelative(-34.8f, -23.3f)
                arcTo(50.9f, 50.9f, 0.0f, false, false, 209.0f, 266.0f)
                lineToRelative(41.0f, 8.2f)
                lineToRelative(5.5f, -5.5f)
                lineToRelative(-46.0f, -6.0f)
                lineToRelative(0.4f, -3.3f)
                lineToRelative(50.3f, 3.2f)
                lineToRelative(6.7f, -6.6f)
                lineToRelative(-6.7f, -6.6f)
                lineToRelative(-50.3f, 3.3f)
                curveToRelative(0.0f, -1.2f, -0.2f, -2.3f, -0.3f, -3.4f)
                lineToRelative(45.9f, -6.0f)
                lineToRelative(-5.4f, -5.5f)
                lineToRelative(-41.0f, 8.2f)
                arcToRelative(51.0f, 51.0f, 0.0f, false, false, -7.7f, -18.4f)
                lineToRelative(34.8f, -23.3f)
                verticalLineToRelative(-7.7f)
                lineToRelative(-36.8f, 28.2f)
                lineToRelative(-2.1f, -2.5f)
                lineToRelative(38.0f, -33.3f)
                verticalLineToRelative(-9.4f)
                horizontalLineToRelative(-9.5f)
                lineToRelative(-33.3f, 38.0f)
                lineToRelative(-2.5f, -2.2f)
                lineToRelative(28.2f, -36.8f)
                horizontalLineToRelative(-7.7f)
                lineToRelative(-23.3f, 34.8f)
                arcToRelative(50.9f, 50.9f, 0.0f, false, false, -18.4f, -7.6f)
                lineToRelative(8.2f, -41.0f)
                lineToRelative(-5.5f, -5.5f)
                lineToRelative(-6.0f, 46.0f)
                lineToRelative(-3.3f, -0.4f)
                lineToRelative(3.2f, -50.3f)
                close()
            }
        }
            .build()
        return _ph!!
    }

private var _ph: ImageVector? = null


@Preview
@Composable
fun FlagPhpreview() {
    Image(imageVector = Icons.FlagPh, contentDescription = null)
}