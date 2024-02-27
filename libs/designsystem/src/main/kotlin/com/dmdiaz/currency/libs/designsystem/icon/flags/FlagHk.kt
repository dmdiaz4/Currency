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


val Icons.FlagHk: ImageVector
    get() {
        if (_hk != null) {
            return _hk!!
        }
        _hk = Builder(
            name = "Hk", defaultWidth = 512.0.dp, defaultHeight = 512.0.dp, viewportWidth
            = 512.0f, viewportHeight = 512.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFEC1B2E)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(0.0f, 0.0f)
                horizontalLineToRelative(512.0f)
                verticalLineToRelative(512.0f)
                horizontalLineTo(0.0f)
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(282.3f, 119.2f)
                curveTo(203.0f, 114.0f, 166.6f, 218.0f, 241.6f, 256.4f)
                curveTo(215.6f, 234.0f, 221.0f, 201.0f, 231.5f, 184.0f)
                lineToRelative(1.9f, 1.0f)
                curveToRelative(-13.8f, 23.6f, -11.2f, 52.8f, 11.0f, 71.0f)
                curveToRelative(-12.6f, -12.2f, -9.4f, -39.0f, 12.2f, -48.8f)
                reflectiveCurveToRelative(23.6f, -39.3f, 16.4f, -49.1f)
                quadToRelative(-14.7f, -25.6f, 9.3f, -39.0f)
                close()
                moveTo(243.9f, 180.0f)
                lineToRelative(-4.7f, 7.4f)
                lineToRelative(-1.8f, -8.6f)
                lineToRelative(-8.6f, -2.3f)
                lineToRelative(7.8f, -4.3f)
                lineToRelative(-0.6f, -9.0f)
                lineToRelative(6.5f, 6.2f)
                lineToRelative(8.3f, -3.3f)
                lineToRelative(-3.7f, 8.0f)
                lineToRelative(5.6f, 6.9f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(392.378f, 248.291f)
                curveTo(372.818f, 171.265f, 262.66f, 168.784f, 249.316f, 251.98f)
                curveTo(262.585f, 220.33f, 295.639f, 215.268f, 315.051f, 220.001f)
                lineToRelative(-0.364f, 2.116f)
                curveToRelative(-26.709f, -5.832f, -53.677f, 5.664f, -64.126f, 32.402f)
                curveToRelative(7.709f, -15.753f, 34.186f, -20.992f, 50.182f, -3.477f)
                reflectiveCurveToRelative(44.669f, 10.301f, 51.765f, 0.425f)
                quadToRelative(19.804f, -21.891f, 39.965f, -3.207f)
                close()
                moveTo(322.688f, 230.558f)
                lineToRelative(-8.49f, -2.183f)
                lineToRelative(7.623f, -4.369f)
                lineToRelative(-0.47f, -8.89f)
                lineToRelative(6.5f, 6.089f)
                lineToRelative(8.374f, -3.352f)
                lineToRelative(-3.888f, 8.098f)
                lineToRelative(5.703f, 6.874f)
                lineToRelative(-8.752f, -1.047f)
                lineToRelative(-4.832f, 7.458f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(303.622f, 392.872f)
                curveTo(370.833f, 350.468f, 339.152f, 244.935f, 255.904f, 257.952f)
                curveTo(290.105f, 260.792f, 305.133f, 290.663f, 306.631f, 310.588f)
                lineToRelative(-2.125f, 0.308f)
                curveToRelative(-2.707f, -27.204f, -21.974f, -49.299f, -50.632f, -50.975f)
                curveToRelative(17.365f, 2.464f, 30.528f, 26.026f, 18.814f, 46.651f)
                reflectiveCurveToRelative(4.007f, 45.666f, 15.592f, 49.362f)
                quadToRelative(26.94f, 12.07f, 15.4f, 37.018f)
                close()
                moveTo(298.95f, 321.113f)
                lineToRelative(-0.547f, -8.749f)
                lineToRelative(6.511f, 5.9f)
                lineToRelative(8.309f, -3.194f)
                lineToRelative(-3.783f, 8.063f)
                lineToRelative(5.775f, 6.928f)
                lineToRelative(-8.903f, -1.195f)
                lineToRelative(-4.775f, 7.548f)
                lineToRelative(-1.709f, -8.647f)
                lineToRelative(-8.586f, -2.291f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(138.689f, 353.138f)
                curveTo(199.788f, 403.956f, 290.365f, 341.214f, 252.26f, 266.064f)
                curveTo(260.128f, 299.468f, 236.363f, 322.992f, 217.876f, 330.573f)
                lineToRelative(-0.949f, -1.926f)
                curveToRelative(25.036f, -10.981f, 40.096f, -36.133f, 32.834f, -63.906f)
                curveToRelative(3.023f, 17.276f, -15.319f, 37.077f, -38.554f, 32.309f)
                reflectiveCurveToRelative(-42.193f, 17.923f, -42.128f, 30.083f)
                quadToRelative(-3.155f, 29.351f, -30.447f, 26.085f)
                close()
                moveTo(205.493f, 326.521f)
                lineToRelative(8.152f, -3.224f)
                lineToRelative(-3.599f, 8.016f)
                lineToRelative(5.606f, 6.916f)
                lineToRelative(-8.838f, -1.106f)
                lineToRelative(-4.805f, 7.634f)
                lineToRelative(-1.614f, -8.837f)
                lineToRelative(-8.655f, -2.209f)
                lineToRelative(7.696f, -4.297f)
                lineToRelative(-0.475f, -8.874f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(125.512f, 183.999f)
                curveTo(96.061f, 257.811f, 183.723f, 324.567f, 243.42f, 265.104f)
                curveTo(214.081f, 282.91f, 184.365f, 267.577f, 171.442f, 252.337f)
                lineToRelative(1.538f, -1.498f)
                curveToRelative(18.181f, 20.417f, 46.755f, 26.968f, 70.924f, 11.479f)
                curveToRelative(-15.497f, 8.213f, -39.996f, -3.112f, -42.642f, -26.683f)
                reflectiveCurveToRelative(-30.084f, -34.589f, -41.629f, -30.77f)
                quadToRelative(-28.89f, 6.07f, -34.217f, -20.896f)
                close()
                moveTo(171.47f, 239.308f)
                lineToRelative(5.585f, 6.757f)
                lineToRelative(-8.735f, -0.946f)
                lineToRelative(-4.845f, 7.468f)
                lineToRelative(-1.679f, -8.747f)
                lineToRelative(-8.745f, -2.211f)
                lineToRelative(7.905f, -4.266f)
                lineToRelative(-0.574f, -8.914f)
                lineToRelative(6.465f, 5.991f)
                lineToRelative(8.293f, -3.194f)
                close()
            }
        }
            .build()
        return _hk!!
    }

private var _hk: ImageVector? = null

@Preview
@Composable
fun FlagHkPreview() {
    Image(imageVector = Icons.FlagHk, contentDescription = null)
}
