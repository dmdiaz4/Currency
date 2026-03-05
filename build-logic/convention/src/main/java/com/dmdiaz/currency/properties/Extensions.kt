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

package com.dmdiaz.currency.properties

import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.BuildConfigField
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.api.variant.Variant
import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.core.InternalBaseVariant
import com.dmdiaz.currency.BuildTypes
import com.dmdiaz.currency.Config
import org.gradle.api.Project
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.Properties

fun Project.androidAppComponent(): ApplicationAndroidComponentsExtension? =
    extensions.findByType(ApplicationAndroidComponentsExtension::class.java)

fun Project.androidLibraryComponent(): LibraryAndroidComponentsExtension? =
    extensions.findByType(LibraryAndroidComponentsExtension::class.java)

fun Project.androidProject(): AppExtension? =
    extensions.findByType(AppExtension::class.java)

fun Project.libraryProject(): LibraryExtension? =
    extensions.findByType(LibraryExtension::class.java)

fun Project.loadPropertiesFile(fileName: String): Properties {
    // Load file
    val propertiesFile = file(fileName)
    if (!propertiesFile.exists()) {
        throw FileNotFoundException(
            "The file '${propertiesFile.absolutePath}' could not be found"
        )
    }

    // Load contents into properties object
    return Properties().apply {
        propertiesFile.inputStream().use(::load)
    }
}

fun Project.loadDefaultPropertiesFile(fileName: String): Properties? {
    val properties = try {
        loadPropertiesFile(fileName)
    } catch (e: FileNotFoundException) {
        null
    }
    return properties
}

private val javaVarRegexp = Regex(pattern = "((?![a-zA-Z_\$0-9]).)")

fun Variant.inject(properties: Properties, ignore: List<String>) {
    val ignoreRegexs = ignore.map { Regex(pattern = it) }
    properties.keys.map { key ->
        key as String
    }.filter { key ->
        key.isNotEmpty() && !ignoreRegexs.any { it.containsMatchIn(key) }
    }.forEach { key ->
        val value = properties.getProperty(key)
        val translatedKey = key.replace(javaVarRegexp, "")

        when{
            value.toBooleanStrictOrNull() != null -> {
                buildConfigFields?.put(
                    translatedKey,
                    BuildConfigField("boolean", value.toBoolean(), null)
                )
            }
            value.toIntOrNull() != null -> {
                buildConfigFields?.put(
                    translatedKey,
                    BuildConfigField("int", value.toInt(), null)
                )
            }
            else -> {
                val sanitizedValue = value.removeSurrounding("\"").addParenthesisIfNeeded()
                buildConfigFields?.put(
                    translatedKey,
                    BuildConfigField("String", sanitizedValue, null)
                )
            }
        }
        manifestPlaceholders.put(translatedKey, value)
    }
}

fun Variant.injectVersionProperties(project: Project) {
    buildConfigFields?.put(
        "VERSION_CODE".replace(javaVarRegexp, ""),
        BuildConfigField("int", project.generateVersionCode().toString(), null)
    )

    val versionNameSuffix = BuildTypes.values().firstOrNull {
        it.name == buildType
    }?.versionNameSuffix

    val version = if (versionNameSuffix != null) {
        Config.versionName + versionNameSuffix
    } else {
        Config.versionName
    }

    buildConfigFields?.put(
        "VERSION_NAME".replace(javaVarRegexp, ""),
        BuildConfigField("String", version.addParenthesisIfNeeded(), null)
    )
}

fun InternalBaseVariant.inject(properties: Properties, ignore: List<String>) {
    val ignoreRegexs = ignore.map { Regex(pattern = it) }
    properties.keys.map { key ->
        key as String
    }.filter { key ->
        key.isNotEmpty() && !ignoreRegexs.any { it.containsMatchIn(key) }
    }.forEach { key ->
        val value = properties.getProperty(key).removeSurrounding("\"")
        val translatedKey = key.replace(javaVarRegexp, "")
        buildConfigField("String", translatedKey, value.addParenthesisIfNeeded())
        mergedFlavor.manifestPlaceholders[translatedKey] = value
    }
}

fun String.addParenthesisIfNeeded(): String {
    if (isEmpty()) {
        return this
    }
    val charArray = this.toCharArray()
    if (length > 1 && charArray[0] == '"' && charArray[charArray.size - 1] == '"') {
        return this
    }
    return "\"$this\""
}

fun Project.generateVersionCode(default: Int = 1) = ByteArrayOutputStream().use { outputStream ->
    exec {
        standardOutput = outputStream
        workingDir = projectDir
        executable = "git"
        args("rev-list", "HEAD", "--count")
    }.let { result ->
        when {
            result.exitValue != 0 -> default
            else -> outputStream.toString().trim().toIntOrNull()?: default
        }
    }
}

fun Project.generateApplicationId(): String {
    val applicationId = "com.dmdiaz.currency"
    project.androidAppComponent()?.onVariants { variant ->
        variant.applicationId.set(applicationId)
    }
    return applicationId
}
