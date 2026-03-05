/*
 * MIT License
 *
 * Copyright (c) 2026 David Diaz
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

package com.dmdiaz.currency.core.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * Multipreview annotation that represents various device sizes and themes. Add this annotation to a composable
 * to render various devices.
 */
@Preview(name = "phone - light", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "phone - dark", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "landscape - light", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "landscape - dark", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "foldable - light", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "foldable - dark", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "tablet - light", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "tablet - dark", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
annotation class ScreenPreviews
