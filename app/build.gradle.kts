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

plugins {
    alias(libs.plugins.currency.android.application)
    alias(libs.plugins.currency.hilt)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.currency.android.compose)
    alias(libs.plugins.currency.dependecy.graph.generator)
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "com.dmdiaz.currency"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // To publish on the Play store a private signing key is required, but to allow anyone
        // who clones the code to sign and run the release variant, use the debug signing key.
        signingConfig = signingConfigs.named("debug").get()
    }
}

baselineProfile {
    // Automatically merge the generated profile into the app
    automaticGenerationDuringBuild = false
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.ui)

    implementation(projects.features.rates)
    implementation(projects.features.convert)

    implementation(projects.libs.util)
    implementation(projects.libs.designsystem)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    androidTestImplementation(libs.compose.ui.test)
    debugImplementation(libs.compose.ui.test.manifest)
    implementation(libs.compose.material3.windowSizeClass)
    implementation(libs.activity.compose)
    implementation(libs.viewmodel.compose)
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)
    debugImplementation(libs.navigation.testing)
    implementation(libs.tracing.ktx)
    implementation(libs.androidx.profileinstaller)
    baselineProfile(projects.baselineprofile)


    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)


    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.android.compiler)

    //joda
    implementation(libs.joda.money)

    //arrow
    implementation(libs.arrow.core)

    //Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.logging.interceptor)
    testImplementation(libs.mock.server)
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
    ksp (libs.moshi.kotlin.codegen)


    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.mock.server)
    testImplementation(libs.truth)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.roboelectric)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
