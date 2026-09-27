package pe.edu.nova.java.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.jvm.toolchain.JavaLanguageVersion;

/**
 * Convention plugin de Gradle para proyectos basados en el meta-framework
 * Nova Platform con Spring Boot.
 * <p>
 * Al aplicar este plugin, el proyecto se configura automáticamente con:
 * Java 25, Spring Boot 4.0.5, repositorios, y el starter del meta-framework
 * como dependencia.
 * </p>
 *
 * @author Nova Platform
 * @version 1.0.0
 */
public class NovaSpringBootPlugin implements Plugin<Project> {

    /** Versión de Java requerida por el framework. */
    private static final int JAVA_VERSION = 25;

    /** Versión de los sub-starters del meta-framework. */
    private static final String STARTER_VERSION = "1.0.0";

    /**
     * Coordenadas del sub-starter mask-utils. Es el núcleo del meta-framework
     * Nova: provee {@code nova.mask.*} (enmascaramiento de campos sensibles en
     * Jackson, logs y respuestas REST) y se autodescubre desde
     * {@code META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports}.
     */
    private static final String MASK_STARTER_DEPENDENCY =
            "pe.edu.nova.java.starters:nova-mask-starter:" + STARTER_VERSION;

    /**
     * Coordenadas del sub-starter api-standard. Complementa el mask starter con
     * {@code nova.api-standard.*} (respuestas API uniformes via interceptor +
     * manejador global de excepciones).
     */
    private static final String API_STANDARD_STARTER_DEPENDENCY =
            "pe.edu.nova.java.starters:nova-api-standard-starter:" + STARTER_VERSION;

    /** Constructor por defecto. */
    public NovaSpringBootPlugin() {}

    @Override
    public void apply(Project project) {
        // 1. Aplicar plugins
        project.getPluginManager().apply("java");
        project.getPluginManager().apply("org.springframework.boot");
        // 2. Configurar Java toolchain
        project.getExtensions().configure(JavaPluginExtension.class, java ->
                java.toolchain(toolchain ->
                        toolchain.getLanguageVersion().set(JavaLanguageVersion.of(JAVA_VERSION))
                )
        );
        // 3. Configurar repositorios
        project.getRepositories().mavenLocal();
        project.getRepositories().mavenCentral();
        // 4. Agregar dependencias a los sub-starters del meta-framework Nova.
        // El starter paraguas de antes ya no se publica: hoy el repositorio
        // nova-java-commons-spring-boot-starter publica los
        // dos sub-starters por separado, así que se agregan ambos para preservar
        // el comportamiento que tenía el plugin antes del rename.
        project.getDependencies().add("implementation", MASK_STARTER_DEPENDENCY);
        project.getDependencies().add("implementation", API_STANDARD_STARTER_DEPENDENCY);
        // 5. Configurar tests con JUnit Platform
        project.getTasks().withType(org.gradle.api.tasks.testing.Test.class, test -> test.useJUnitPlatform());
    }
}
