# Nova Spring Boot Gradle Plugin

A convention plugin. Applying it configures the Java toolchain, applies
the Spring Boot Gradle plugin and sets the build conventions every Nova
service shares — so build files stop being copies of each other.

## What it applies

- The Java toolchain, pinned to the platform's target version
- `org.springframework.boot` 4.0.5 and the dependency-management plugin
- Nova's compiler, test and packaging conventions

Everything it does lives in `NovaSpringBootPlugin`.

## Install

Published to GitHub Packages. Declare the plugin repository in
`settings.gradle.kts`:

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            url = uri("https://maven.pkg.github.com/ahincho/nova-java-spring-boot-gradle-plugin")
            credentials {
                username = providers.gradleProperty("gpr.user").orNull ?: System.getenv("GITHUB_ACTOR")
                password = providers.gradleProperty("gpr.key").orNull ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
```

## Use

```kotlin
plugins {
    id("pe.edu.nova.java.spring-boot") version "0.1.0-SNAPSHOT"
}
```

There is no configuration block. The point of a convention plugin is that
there is nothing to decide — if a service needs to differ, it overrides
the specific task in its own build file.

## The Maven equivalent

[nova-java-spring-boot-parent](https://github.com/ahincho/nova-java-spring-boot-parent)
carries the same conventions as a parent POM.

## Requirements

Java 25, Gradle 8+.

## License

Eclipse Public License 2.0 — see [LICENSE](LICENSE).

Copyright © 2026 Angel Hincho.
