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


import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.dmdiaz.currency.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    implementation(libs.graphviz.java)
    implementation(libs.truth)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {

        // Register plugins for each module type
        register("androidApplication") {
            id = "currency.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "currency.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidTest") {
            id = "currency.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("androidBaselineProfile") {
            id = "currency.android.baselineprofile"
            implementationClass = "AndroidBaselineProfileConventionPlugin"
        }
        register("jvmLibrary") {
            id = "currency.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        // Register plugins for feature modules(library module with extra functionality)
        register("androidFeature") {
            id = "currency.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("dependencyGraphGenerator") {
            id = "currency.dependency.graph.generator"
            implementationClass = "DependencyGraphGeneratorPlugin"
        }

        register("properties") {
            id = "currency.properties"
            implementationClass = "PropertiesPlugin"
        }

        // Register plugins for modules to apply functionality
        register("androidCompose") {
            id = "currency.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("hilt") {
            id = "currency.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("androidRoom") {
            id = "currency.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidFlavors") {
            id = "currency.android.flavors"
            implementationClass = "AndroidFlavorsConventionPlugin"
        }
        register("androidBuildTypes") {
            id = "currency.android.build.types"
            implementationClass = "AndroidBuildTypesConventionPlugin"
        }
        register("androidLint") {
            id = "currency.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }
    }
}
