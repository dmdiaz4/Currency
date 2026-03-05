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

package com.dmdiaz.currency.graph

import guru.nidi.graphviz.attribute.Color

internal enum class ProjectTarget(
  val ids: Set<String>,
  val color: Color,
) {
  ANDROID(
    ids = setOf("com.android.library", "com.android.application", "com.android.test", "com.android.dynamic-feature", "com.android.instantapp"),
    color = Color.rgb("#66BB6A").fill(),
  ),
  JVM(
    ids = setOf("java-library", "java", "java-gradle-plugin", "application", "org.jetbrains.kotlin.jvm"),
    color = Color.rgb("#FF7043").fill(),
  ),
  IOS(
    ids = setOf("org.jetbrains.kotlin.native.cocoapods"),
    color = Color.rgb("#42A5F5").fill(),
  ),
  JS(
    ids = setOf("com.eriwen.gradle.js"),
    color = Color.rgb("#FFCA28").fill(),
  ),
  MULTIPLATFORM(
    ids = setOf("org.jetbrains.kotlin.multiplatform"),
    color = Color.rgb("#A280FF").fill(),
  ),
  OTHER(
    ids = emptySet(),
    color = Color.rgb("#BDBDBD").fill(),
  ),
  ;
}
