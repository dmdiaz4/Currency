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

package com.dmdiaz.currency.libs.designsystem.components

import androidx.annotation.FloatRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy

/**
 * overlappingFactor decides how much of each child composable will be
 * visible before next one overlaps it 1.0 means completely visible
 * 0.7 means 70% 0.5 means 50% visible and so on
 */
@Composable
fun OverlappingRow(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.1,to = 1.0) widthOverlapFactor:Float = 0.5f,
    @FloatRange(from = 0.1,to = 1.0) heightOverlapFactor:Float = 0.5f,
    content: @Composable () -> Unit,
){
    val measurePolicy = overlappingRowMeasurePolicy(
        widthOverlapFactor = widthOverlapFactor,
        heightOverlapFactor = heightOverlapFactor
    )
    Layout(measurePolicy = measurePolicy,
        content = content,
        modifier = modifier )
}


private fun overlappingRowMeasurePolicy(widthOverlapFactor: Float, heightOverlapFactor: Float) = MeasurePolicy { measurables, constraints ->
    val placeables = measurables.map { measurable -> measurable.measure(constraints)}
    val height = (placeables.subList(1,placeables.size).sumOf { it.height  }* heightOverlapFactor + placeables[0].height).toInt()
    val width = (placeables.subList(1,placeables.size).sumOf { it.width  }* widthOverlapFactor + placeables[0].width).toInt()
    layout(width,height) {
        var xPos = 0
        var yPos = 0
        for (placeable in placeables) {
            placeable.placeRelative(xPos, yPos, 0f)
            xPos += (placeable.width * widthOverlapFactor).toInt()
            yPos += (placeable.height * heightOverlapFactor).toInt()
        }
    }
}