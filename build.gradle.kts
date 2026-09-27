plugins {
    `java-gradle-plugin`
    `maven-publish`
    checkstyle
    id("net.nemerosa.versioning") version "4.0.1"
    id("signing")
    id("org.owasp.dependencycheck") version "12.2.2"
    id("org.cyclonedx.bom") version "3.2.4"
}

versioning {
    releaseMode = "snapshot"
    displayMode = "snapshot"
    releaseBuild = false
}

group = "pe.edu.nova.java"
version = findProperty("version") as String

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:4.0.8")

    constraints {
        // spring-boot-buildpack-platform 4.0.8 trae httpclient5 5.5.2. La restricción viaja
        // en la metadata publicada, así que también corrige el classpath de quien aplica
        // el plugin, no solo el de este build.
        implementation("org.apache.httpcomponents.client5:httpclient5:5.6.4") {
            because("CVE-2026-71290 requires httpclient5 5.6.4+")
        }
    }
}

gradlePlugin {
    plugins {
        create("novaSpringBoot") {
            id = "pe.edu.nova.java.spring-boot"
            implementationClass = "pe.edu.nova.java.gradle.NovaSpringBootPlugin"
            displayName = "Nova Spring Boot Plugin"
            description = "Convention plugin que configura proyectos Spring Boot con el meta-framework Nova."
        }
    }
}

checkstyle {
    // Only lint production code. Test suites commonly rely on static-import
    // wildcards (org.junit.jupiter.api.Assertions.*, net.jqwik.api.*), which
    // is an accepted convention that would otherwise trip AvoidStarImport.
    sourceSets = listOf(project.sourceSets.main.get())
}

// Versiones parcheadas de dependencias que el OWASP gate marca con CVSS >= 7. Las cuatro
// llegan por la herramienta checkstyle; son las mismas que usan los starters.
// Verificadas contra la GitHub Advisory Database el 2026-09-27.
configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.apache.httpcomponents" && requested.name.startsWith("httpcore")) {
            useVersion("4.4.16")
            because("CVE-2026-54428, CVE-2026-54399 require httpcore 4.4.16+")
        }
        if (requested.group == "org.apache.httpcomponents.core5" && requested.name.startsWith("httpcore5")) {
            useVersion("5.4.3")
            because("CVE-2026-54399 requires httpcore5 5.4.3+")
        }
        if (requested.group == "commons-beanutils" && requested.name == "commons-beanutils") {
            useVersion("1.11.0")
            because("CVE-2025-48734 requires commons-beanutils 1.11.0+")
        }
        if (requested.group == "org.codehaus.plexus" && requested.name == "plexus-utils") {
            useVersion("3.6.1")
            because("CVE-2025-67030 requires plexus-utils 3.6.1+")
        }
    }
}

dependencyCheck {
    // NVD_API_KEY / NOVA_OWASP_FAIL_ON_CVSS are injected by reusable-owasp-check.yml.
    // Locally (no env vars set) this defaults to "never fail" (11.0, matches plugin default)
    // and an empty NVD key (slower updates, acceptable for local dev).
    failBuildOnCVSS = (System.getenv("NOVA_OWASP_FAIL_ON_CVSS") ?: "11").toFloat()
    nvd.apiKey = System.getenv("NVD_API_KEY") ?: ""
    // reusable-owasp-check.yml restaura un mirror de NVD de menos de 24 horas. Sin estas
    // dos líneas el plugin lo ignora, sincroniza NVD entero y puede quedarse sin memoria.
    autoUpdate = false
    data.directory = System.getenv("NOVA_OWASP_DATA_DIR")
        ?: "${System.getProperty("user.home")}/.dependency-check-data"
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ahincho/nova-java-16-spring-boot-gradle-plugin")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

signing {
    val gpgKeyId: String? = System.getenv("GPG_SIGNING_KEY_ID")
    val gpgKey: String? = System.getenv("GPG_SIGNING_KEY")
    val gpgPassword: String? = System.getenv("GPG_SIGNING_PASSWORD")

    if (gpgKeyId != null && gpgKey != null) {
        useInMemoryPgpKeys(gpgKeyId, gpgKey, gpgPassword ?: "")
        sign(publishing.publications)
    }
}