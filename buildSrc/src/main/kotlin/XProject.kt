import org.gradle.api.Project

val Project.libProperties get() = readProperties("${rootProject.rootDir}/lib.properties")