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

import org.gradle.api.Project
import org.gradle.api.artifacts.ResolvedDependency

@JvmInline
internal value class DotIdentifier(internal val value: String) {
  init {
    require(value.none(illegalChars))
  }
}

private val illegalChars: (Char) -> Boolean = { it == '-' || it == '.' || it.isWhitespace() }

internal val String.dotIdentifier get() = DotIdentifier(filterNot(illegalChars))

internal val Project.dotIdentifier get() = "$group$name".dotIdentifier

internal val ResolvedDependency.dotIdentifier get() = (moduleGroup + moduleName).dotIdentifier

internal val DependencyContainer.dotIdentifier get() = when (this) {
  is DependencyContainer.Project -> project.dotIdentifier
  is DependencyContainer.ResolvedDependency -> resolvedDependency.dotIdentifier
}
