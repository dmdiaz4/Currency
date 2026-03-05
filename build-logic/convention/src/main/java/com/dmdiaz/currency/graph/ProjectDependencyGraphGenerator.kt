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

import guru.nidi.graphviz.attribute.Font
import guru.nidi.graphviz.attribute.GraphAttr
import guru.nidi.graphviz.attribute.Label
import guru.nidi.graphviz.attribute.Rank
import guru.nidi.graphviz.attribute.Rank.RankType
import guru.nidi.graphviz.attribute.Shape
import guru.nidi.graphviz.attribute.Style
import guru.nidi.graphviz.model.Factory.graph
import guru.nidi.graphviz.model.Factory.mutGraph
import guru.nidi.graphviz.model.Factory.mutNode
import guru.nidi.graphviz.model.Link
import guru.nidi.graphviz.model.MutableGraph
import guru.nidi.graphviz.model.MutableNode
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ProjectDependency

// Based on https://github.com/JakeWharton/SdkSearch/blob/766d612ed52cdf3af9cd0728b6afd87006746ae5/gradle/projectDependencyGraph.gradle
internal class ProjectDependencyGraphGenerator(
    private val project: Project,
    private val projectGenerator: DependencyGraphGeneratorExtension.ProjectGenerator,
) {
  fun generateGraph(): MutableGraph {
    val projects = mutableSetOf<Project>()
    val dependencies = mutableListOf<ProjectDependencyContainer>()
    fun addProject(project: Project) {
      if (projectGenerator.includeProject(project) && projects.add(project)) {
        project.configurations
          .filter { projectGenerator.includeConfiguration.invoke(it) }
          .flatMap { configuration ->
            configuration.dependencies
              .withType(ProjectDependency::class.java)
              .map { ProjectDependencyContainer(project, it.dependencyProject, configuration) }
          }
          .forEach {
            dependencies.add(it)
            addProject(it.to)
          }
      }
    }
    project.allprojects.filter { it.isDependingOnOtherProject() }.forEach { addProject(it) }

    val graph = mutGraph().setDirected(true).graphAttrs().add(GraphAttr.dpi(100))
    graph.graphAttrs().add(Label.of(project.name).locate(Label.Location.TOP), Font.size(DEFAULT_FONT_SIZE))
    graph.nodeAttrs().add(Style.FILLED)
    projects.forEach { addNode(it, dependencies, graph) }
    rankRootProjects(graph, projects, dependencies)
    addDependencies(dependencies, graph)

    return projectGenerator.graph(graph)
  }

  private fun addNode(project: Project, dependencies: List<ProjectDependencyContainer>, graph: MutableGraph) {
    val node = mutNode(project.path)

    if (dependencies.none { it.to == project }) {
      node.add(Shape.RECTANGLE)
    }

    node.add(project.target().color)
    graph.add(projectGenerator.projectNode(node, project))
  }

  @Suppress("Detekt.SpreadOperator") private fun rankRootProjects(graph: MutableGraph, projects: MutableSet<Project>, dependencies: List<ProjectDependencyContainer>) {
    graph.add(
      graph()
        .graphAttr()
        .with(Rank.inSubgraph(RankType.SAME))
        .with(*projects.filter { project -> dependencies.none { it.to == project } }.map { mutNode(it.path) }.toTypedArray()),
    )
  }

  private fun addDependencies(dependencies: MutableList<ProjectDependencyContainer>, graph: MutableGraph) {
    val rootNodes: List<MutableNode> = graph.rootNodes().filterNotNull().filter { it.links().isEmpty() }
    dependencies
      .filterNot { (from, to, _) -> from == to }
      .distinctBy { it.from.path to it.to.path }
      .forEach { (from, to, configuration) ->
        val fromNode = rootNodes.single { it.name().toString() == from.path }
        val toNode = rootNodes.singleOrNull { it.name().toString() == to.path } ?: return@forEach
        val link = projectGenerator.link(Link.to(toNode), from, to, configuration)
        graph.add(fromNode.addLink(link))
      }
  }

  internal data class ProjectDependencyContainer(
    val from: Project,
    val to: Project,
    val configuration: Configuration,
  )

  internal companion object {
    const val DEFAULT_FONT_SIZE = 35
  }
}
