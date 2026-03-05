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

import com.dmdiaz.currency.graph.DependencyGraphGeneratorExtension
import com.dmdiaz.currency.graph.DependencyGraphGeneratorExtension.Generator
import com.dmdiaz.currency.graph.DependencyGraphGeneratorExtension.ProjectGenerator
import com.dmdiaz.currency.graph.DependencyGraphGeneratorTask
import com.dmdiaz.currency.graph.ProjectDependencyGraphGeneratorTask
import com.dmdiaz.currency.graph.nonEmptyPrepend
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

@Suppress("ObjectLiteralToLambda")
open class DependencyGraphGeneratorPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create(
            "dependencyGraphGenerator",
            DependencyGraphGeneratorExtension::class.java,
            project
        )

        val dependencyGraphGeneratorAction = object : Action<Generator> {
            override fun execute(generator: Generator) {
                val taskAction = object : Action<DependencyGraphGeneratorTask> {
                    override fun execute(task: DependencyGraphGeneratorTask) {
                        task.generator = generator
                        task.group = "reporting"
                        task.description =
                            "Generates a dependency graph${generator.name.nonEmptyPrepend(" for ")}"
                        val buildDir = project.layout.buildDirectory.get().asFile
                        val defaultOutputDirectory = File(buildDir, "reports/dependency-graph/")
                        task.outputDirectory =
                            if (generator.outputDirectoryPath.isNotEmpty()){
                                File(generator.outputDirectoryPath)
                            } else {
                                defaultOutputDirectory
                            }
                    }
                }
                project.tasks.register(
                    generator.gradleTaskName,
                    DependencyGraphGeneratorTask::class.java,
                    taskAction
                )
            }
        }
        extension.generators.all(dependencyGraphGeneratorAction)

        val projectDependencyGraphGeneratorAction = object : Action<ProjectGenerator> {
            override fun execute(projectGenerator: ProjectGenerator) {
                val taskAction = object : Action<ProjectDependencyGraphGeneratorTask> {
                    override fun execute(task: ProjectDependencyGraphGeneratorTask) {
                        task.projectGenerator = projectGenerator
                        task.group = "reporting"
                        task.description =
                            "Generates a project dependency graph${projectGenerator.name.nonEmptyPrepend(" for ")}"
                        val buildDir = project.layout.buildDirectory.get().asFile
                        val defaultOutputDirectory = File(buildDir, "reports/project-dependency-graph/")
                        task.outputDirectory =
                            if (projectGenerator.outputDirectoryPath.isNotEmpty()){
                                File(projectGenerator.outputDirectoryPath)
                            } else {
                                defaultOutputDirectory
                            }
                    }
                }
                project.tasks.register(
                    projectGenerator.gradleTaskName,
                    ProjectDependencyGraphGeneratorTask::class.java,
                    taskAction
                )
            }
        }

        extension.projectGenerators.all(projectDependencyGraphGeneratorAction)
    }
}
