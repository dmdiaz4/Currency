# Convention Plugins

The `build-logic` folder defines project-specific convention plugins, used to keep a single
source of truth for common module configurations.

This approach is heavily based on
[https://developer.squareup.com/blog/herding-elephants/](https://developer.squareup.com/blog/herding-elephants/)
and
[https://github.com/jjohannes/idiomatic-gradle](https://github.com/jjohannes/idiomatic-gradle).

By setting up convention plugins in `build-logic`, we can avoid duplicated build script setup,
messy `subproject` configurations, without the pitfalls of the `buildSrc` directory.

`build-logic` is an included build, as configured in the root
[`settings.gradle.kts`](../settings.gradle.kts).

Inside `build-logic` is a `convention` module, which defines a set of plugins that all normal
modules can use to configure themselves.

`build-logic` also includes a set of `Kotlin` files used to share logic between plugins themselves,
which is most useful for configuring Android components (libraries vs applications) with shared
code.

These plugins are *additive* and *composable*, and try to only accomplish a single responsibility.
Modules can then pick and choose the configurations they need.
If there is one-off logic for a module without shared code, it's preferable to define that directly
in the module's `build.gradle`, as opposed to creating a convention plugin with module-specific
setup.

Current list of plugins:

- [`currency.android.application`](convention/src/main/java/AndroidApplicationConventionPlugin.kt),
  [`currency.android.library`](convention/src/main/java/AndroidLibraryConventionPlugin.kt),
  [`currency.jvm.library`](convention/src/main/java/AndroidTestConventionPlugin.kt),
  [`currency.android.test`](convention/src/main/java/AndroidTestConventionPlugin.kt),
  [`currency.android.feature`](convention/src/main/java/AndroidFeatureConventionPlugin.kt): Configures common Android and Kotlin options for these types of modules.
- [`currency.android.compose`](convention/src/main/java/AndroidComposeConventionPlugin.kt): Configures Jetpack Compose options
- [`currency.android.flavors`](convention/src/main/java/AndroidBuildTypesConventionPlugin.kt): Configures Android module's flavors
- [`currency.android.build.types`](convention/src/main/java/AndroidFlavorsConventionPlugin.kt): Configures Android module's build types
- [`currency.android.lint`](convention/src/main/java/AndroidLintConventionPlugin.kt): Configures both ktLint and android's lint options (Already included in [`currency.android.application`](convention/src/main/java/AndroidApplicationConventionPlugin.kt) and [`prepass.android.library`](convention/src/main/java/AndroidLibraryConventionPlugin.kt )
- [`currency.android.room`](convention/src/main/java/AndroidRoomConventionPlugin.kt): Configures Room configuration and options
- [`currency.hilt`](convention/src/main/java/HiltConventionPlugin.kt): Configures modules Hilt configuration and options
- [`currency.dependency.graph.generator`](convention/src/main/java/DependencyGraphGeneratorPlugin.kt): Generates gradle tasks that produce dependency graphs
- [`currency.secrets`](convention/src/main/java/SecretsPlugin.kt): Add properties to build config and res

