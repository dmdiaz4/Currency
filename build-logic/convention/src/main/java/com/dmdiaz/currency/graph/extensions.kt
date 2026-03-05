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

import com.dmdiaz.currency.graph.ProjectTarget.ANDROID
import com.dmdiaz.currency.graph.ProjectTarget.IOS
import com.dmdiaz.currency.graph.ProjectTarget.JS
import com.dmdiaz.currency.graph.ProjectTarget.JVM
import com.dmdiaz.currency.graph.ProjectTarget.MULTIPLATFORM
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ProjectDependency

internal fun String.nonEmptyPrepend(prepend: String) =
    if (isNotEmpty()) prepend + this else this

internal fun String.nonEmptySuffix(suffix: String) =
    if (isNotEmpty()) this + suffix else this

internal fun String.toHyphenCase(): String {
    if (isBlank()) return this

    return this[0].lowercase().toString() + toCharArray()
        .map { it.toString() }
        .drop(1)
        .joinToString(separator = "") { if (it[0].isUpperCase()) "-${it[0].lowercase()}" else it }
}

fun Project.isDependingOnOtherProject() =
    configurations.any { configuration -> configuration.dependencies.any { it is ProjectDependency } }

fun Project.isCommonsProject() = plugins.hasPlugin("org.jetbrains.kotlin.platform.common")

internal fun Project.target(): ProjectTarget {
    val targets = ProjectTarget.values()
        .filter { target -> target.ids.any { plugins.hasPlugin(it) } }

    return when {
        targets.contains(MULTIPLATFORM) -> MULTIPLATFORM
        targets.contains(ANDROID) -> ANDROID
        targets.contains(JVM) -> JVM
        targets.contains(IOS) -> IOS
        targets.contains(JS) -> JS
        else -> ProjectTarget.OTHER
    }
}

internal fun Configuration.isImplementation() = name.lowercase().endsWith("implementation")
