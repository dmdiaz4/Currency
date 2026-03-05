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

import com.android.build.api.variant.Variant
import com.dmdiaz.currency.properties.PropertiesPluginExtension
import com.dmdiaz.currency.properties.androidAppComponent
import com.dmdiaz.currency.properties.androidLibraryComponent
import com.dmdiaz.currency.properties.inject
import com.dmdiaz.currency.properties.injectVersionProperties
import com.dmdiaz.currency.properties.loadDefaultPropertiesFile
import com.dmdiaz.currency.properties.loadPropertiesFile
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.FileNotFoundException
import java.util.Properties

/**
 * Plugin that reads properties from a properties file and injects manifest build and BuildConfig
 * variables into an Android project. Since property keys are turned into Java variables,
 * invalid variable characters from the property key are removed.
 *
 * e.g.
 * A key defined as "sdk.dir" in the properties file will be converted to "sdkdir".
 */
class PropertiesPlugin : Plugin<Project> {

    private val extensionName = "propertiesConfig"

    override fun apply(project: Project) {
        val extension = project.extensions.create(
            extensionName,
            PropertiesPluginExtension::class.java
        )

        val supportedComponents =
            listOf(project.androidAppComponent(), project.androidLibraryComponent())
        supportedComponents.forEach { component ->
            component?.onVariants { variant ->
                val properties= project.loadDefaultPropertiesFile(extension.propertiesFileName)
                generateConfigKey(project, extension, properties, variant)
            }
        }
    }

    private fun generateConfigKey(
        project: Project,
        extension: PropertiesPluginExtension,
        properties: Properties?,
        variant: Variant
    ) {

        if (extension.addVersionProperties){
            variant.injectVersionProperties(project)
        }

        properties?.let {
            variant.inject(properties, extension.ignoreList)
        }

        // Inject build-type specific properties
        val buildTypeFileName = "${variant.buildType}.properties"
        val buildTypeProperties = try {
            project.loadPropertiesFile(buildTypeFileName)
        } catch (e: FileNotFoundException) {
            null
        }
        buildTypeProperties?.let {
            variant.inject(it, extension.ignoreList)
        }

        // Inject flavor-specific properties
        val flavorFileName = "${variant.flavorName}.properties"
        val flavorProperties = try {
            project.loadPropertiesFile(flavorFileName)
        } catch (e: FileNotFoundException) {
            null
        }
        flavorProperties?.let {
            variant.inject(it, extension.ignoreList)
        }
    }
}