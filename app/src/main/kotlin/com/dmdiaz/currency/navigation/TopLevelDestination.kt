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

package com.dmdiaz.currency.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.dmdiaz.currency.libs.designsystem.icon.CurrencyIcons
import com.dmdiaz.currency.features.convert.R as convert
import com.dmdiaz.currency.features.rates.R as rates


/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    RATES(
        selectedIcon = CurrencyIcons.ChartLine,
        unselectedIcon = CurrencyIcons.ChartLine,
        iconTextId = rates.string.feature_rates_title,
        titleTextId = rates.string.feature_rates_title,
    ),

    CONVERT(
        selectedIcon = CurrencyIcons.ArrowLeftRight,
        unselectedIcon = CurrencyIcons.ArrowLeftRight,
        iconTextId = convert.string.feature_convert_title,
        titleTextId = convert.string.feature_convert_title,
    ),

}
