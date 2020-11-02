import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.library")
    id("kotlin-android")
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
}

val libProperties = Properties().apply { load(FileInputStream("lib.properties")) }

android {
    commonConfig(libProperties)

    kotlinOptions.jvmTarget = "1.8"

    //buildFeatures.viewBinding = true
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
    implementation("androidx.fragment:fragment-ktx:1.2.5")

    testImplementation("junit:junit:4.13.1")
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

publishing {
    publications {
        create<MavenPublication>(libProperties.getProperty("lib.name")) {
            groupId = libProperties.getProperty("lib.group")
            artifactId = libProperties.getProperty("lib.name")
            version = libProperties.getProperty("version.name")

            artifact("$buildDir/outputs/aar/lib-release.aar")

            //from(components["java"])

            pom.withXml {
                asNode().apply {
                    appendNode("description", libProperties.getProperty("lib.desc"))
                    appendNode("name", rootProject.name)
                    appendNode("url", "https://github.com/${libProperties.getProperty("lib.group")}/${libProperties.getProperty("lib.name")}")
                    appendNode("licenses").appendNode("license").apply {
                        appendNode("name", "MIT")
                        appendNode("url", "https://opensource.org/licenses/mit-license.php")
                        appendNode("distribution", "repo")
                    }
                    appendNode("developers").appendNode("developer").apply {
                        appendNode("id", libProperties.getProperty("lib.group"))
                        appendNode("name", libProperties.getProperty("lib.group"))
                    }
                    appendNode("scm").apply {
                        appendNode("url", "https://github.com/${libProperties.getProperty("lib.group")}/${libProperties.getProperty("lib.name")}")
                    }
                    appendNode("dependencies").apply {
                        configurations.getByName("implementation") {
                            dependencies.forEach {
                                appendNode("dependency").apply {
                                    appendNode("groupId", it.group)
                                    appendNode("artifactId", it.name)
                                    appendNode("version", it.version)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

bintray {
    user = project.findProperty("bintrayUser").toString()
    key = project.findProperty("bintrayKey").toString()
    publish = true

    setPublications(libProperties.getProperty("lib.name"))

    pkg.apply {
        repo = libProperties.getProperty("lib.name")
        name = libProperties.getProperty("lib.name")
        userOrg = libProperties.getProperty("lib.group")
        githubRepo = "maven"
        vcsUrl = "https://github.com/${libProperties.getProperty("lib.group")}/${libProperties.getProperty("lib.name")}"
        description = libProperties.getProperty("lib.desc")
        setLabels("kotlin", libProperties.getProperty("lib.name"))
        setLicenses("MIT")
        desc = description
        websiteUrl = "https://github.com/${libProperties.getProperty("lib.group")}/${libProperties.getProperty("lib.name")}"
        issueTrackerUrl = "https://github.com/${libProperties.getProperty("lib.group")}/${libProperties.getProperty("lib.name")}/issues"
        githubReleaseNotesFile = "README.md"

        version.apply {
            name = libProperties.getProperty("version.name")
            desc = libProperties.getProperty("lib.desc")
            released = Date().toString()
            vcsTag = libProperties.getProperty("version.name")
        }
    }
}