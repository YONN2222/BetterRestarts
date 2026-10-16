plugins {
    `java-library`
    id("com.gradleup.shadow") version "9.4.0"
}

val pluginName: String by project
val website: String by project
val author: String by project
val paperVersion: String by project

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    mavenCentral()
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:$paperVersion")
    implementation("org.bstats:bstats-bukkit:3.2.1")
    implementation("net.kyori:adventure-text-minimessage:5.2.0")
    implementation("net.kyori:adventure-text-serializer-legacy:5.2.0")
    testImplementation("io.papermc.paper:paper-api:$paperVersion")
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    withSourcesJar()
}

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release = 21
        options.compilerArgs.add("-Xlint:all")
    }
    processResources {
        val props = mapOf(
            "name" to pluginName,
            "version" to project.version,
            "description" to project.property("description"),
            "website" to website,
            "author" to author,
            "bstatsId" to project.property("bstatsId")
        )
        inputs.properties(props)
        filesMatching(listOf("plugin.yml", "metrics.properties")) {
            expand(props)
        }
    }
    jar {
        archiveClassifier.set("plain")
    }
    shadowJar {
        archiveBaseName.set(pluginName)
        archiveClassifier.set("")
        relocate("org.bstats", "com.yonn2222.betterrestarts.libs.bstats")
        relocate("net.kyori", "com.yonn2222.betterrestarts.libs.kyori")
        mergeServiceFiles()
        minimize {
            exclude(dependency("net.kyori:.*"))
        }
    }
    build {
        dependsOn(shadowJar)
    }
    test {
        useJUnitPlatform()
        testLogging {
            events("failed")
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
}
