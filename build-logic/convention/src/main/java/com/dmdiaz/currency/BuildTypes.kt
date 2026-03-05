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


package com.dmdiaz.currency

import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.dmdiaz.currency.BuildTypes.debug
import com.dmdiaz.currency.BuildTypes.release

/**
 * This is shared between :app module to provide configurations type safety.
 */
@Suppress("EnumEntryName")
enum class BuildTypes(
    val versionNameSuffix: String? = null,
    val minifyAndShrinkResources: Boolean = true,
) {
    debug(".debug", minifyAndShrinkResources = false),
    stage(".stage",  minifyAndShrinkResources = true),
    release( minifyAndShrinkResources = true),
}

fun CommonExtension<*, *, *, *, *, *>.configureBuildTypes(
    buildTypeConfigurationBlock: BuildType.(buildType: BuildTypes) -> Unit = {}
) {
    buildTypes {
        val block: BuildType.(buildType: BuildTypes) -> Unit = {
            if (this@configureBuildTypes is ApplicationExtension && this is ApplicationBuildType) {
                if (it.minifyAndShrinkResources) {
                    isShrinkResources = true
                    isMinifyEnabled = true
                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                }
                if (it.versionNameSuffix != null) {
                    versionNameSuffix = it.versionNameSuffix
                }
            }
            buildTypeConfigurationBlock(this, it)
        }

        BuildTypes.values().forEach {
            if (it == debug || it == release){
                getByName(it.name){ block(this, it) }
            } else {
                create(it.name){ block(this, it) }
            }
        }
    }
}
