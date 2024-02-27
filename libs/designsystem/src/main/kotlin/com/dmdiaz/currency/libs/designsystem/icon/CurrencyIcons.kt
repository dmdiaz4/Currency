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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.dmdiaz.currency.libs.designsystem.icon.flags.FlagCa
import com.dmdiaz.currency.libs.designsystem.icon.flags.FlagDk
import com.dmdiaz.currency.libs.designsystem.icon.flags.FlagGb
import com.dmdiaz.currency.libs.designsystem.icon.flags.FlagHk
import com.dmdiaz.currency.libs.designsystem.icon.flags.FlagIs
import com.dmdiaz.currency.libs.designsystem.icon.flags.FlagPh
import com.dmdiaz.currency.libs.designsystem.icon.flags.FlagSe
import com.dmdiaz.currency.libs.designsystem.icon.flags.FlagUs

/**
 * Now in Android icons. Material icons are [ImageVector]s, custom icons are drawable resource IDs.
 */
object CurrencyIcons {
    val Add = Icons.Rounded.Add
    val ChartLine = Icons.ChartLine
    val ArrowLeftRight = Icons.ArrowLeftRight
    val SwapHorizontal = Icons.SwapHorizontal
    val SwapVertical = Icons.SwapVertical
    object Flags {
        val Us = Icons.FlagUs
        val Ca = Icons.FlagCa
        val Hk = Icons.FlagHk
        val Is = Icons.FlagIs
        val Ph = Icons.FlagPh
        val Dk = Icons.FlagDk
        val Gb = Icons.FlagGb
        val Se = Icons.FlagSe
    }
}

internal inline fun imageVector(
    name: String,
    width: Float,
    height: Float,
    viewportWidth: Float,
    viewportHeight: Float,
    block: ImageVector.Builder.() -> ImageVector.Builder,
): ImageVector = ImageVector.Builder(
    name = name,
    defaultWidth = width.dp,
    defaultHeight = height.dp,
    viewportWidth = viewportWidth,
    viewportHeight = viewportHeight,
).block().build()
