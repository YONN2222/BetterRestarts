import io.papermc.hangarpublishplugin.model.Platforms

plugins {
    id("com.modrinth.minotaur") version "2.10.0"
    id("io.papermc.hangar-publish-plugin") version "0.1.4"
}

val pluginName = "BetterRestarts"
val version = providers.gradleProperty("releaseVersion").get()
val jarFile = layout.projectDirectory.file(providers.gradleProperty("jar").get())
val gameVersions = providers.gradleProperty("gameVersions").get().split(",")
val notes = providers.fileContents(layout.projectDirectory.file("../CHANGELOG.md")).asText.map { text ->
    val section = text.split(Regex("(?m)^## ")).firstOrNull { it.startsWith("[$version]") }
    section?.lines()?.drop(1)?.joinToString("\n")?.trim().orEmpty()
}

modrinth {
    token.set(providers.environmentVariable("MODRINTH_TOKEN"))
    projectId.set("betterrestarts")
    versionNumber.set(version)
    versionType.set("release")
    uploadFile.set(jarFile.asFile)
    gameVersions.addAll(this@Build_gradle.gameVersions)
    loaders.addAll("bukkit", "spigot", "paper", "purpur", "folia")
    changelog.set(notes)
}

hangarPublish {
    publications.register("plugin") {
        this.version.set(this@Build_gradle.version)
        id.set(pluginName)
        channel.set("Release")
        changelog.set(notes)
        apiKey.set(providers.environmentVariable("HANGAR_TOKEN"))
        platforms {
            register(Platforms.PAPER) {
                jar.set(jarFile)
                platformVersions.set(listOf("1.21-26.3"))
            }
        }
    }
}
