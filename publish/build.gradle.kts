import io.papermc.hangarpublishplugin.model.Platforms

plugins {
    base
    id("com.modrinth.minotaur") version "2.10.0"
    id("io.papermc.hangar-publish-plugin") version "0.1.4"
}

val pluginName = "BetterRestarts"
val releaseVersion = providers.gradleProperty("releaseVersion").get()
val versionParts = releaseVersion.split(Regex("[.-]"))
val major = versionParts[0].toInt()
val minor = versionParts[1].toInt()
val stage = when {
    major > 0 -> "release"
    minor > 0 -> "beta"
    else -> "alpha"
}
val jarFile = layout.projectDirectory.file(providers.gradleProperty("jar").get())
val mcVersions = providers.gradleProperty("gameVersions").get().split(",")
val notesFile = providers.gradleProperty("notesFile").orNull
val notes = if (notesFile != null) {
    providers.fileContents(layout.projectDirectory.file(notesFile)).asText.map { it.trim() }
} else {
    providers.fileContents(layout.projectDirectory.file("../CHANGELOG.md")).asText.map { text ->
        val section = text.split(Regex("(?m)^## ")).firstOrNull { it.startsWith("[$releaseVersion]") }
        section?.lines()?.drop(1)?.joinToString("\n")?.trim().orEmpty()
    }
}

modrinth {
    token.set(providers.environmentVariable("MODRINTH_TOKEN"))
    projectId.set("better-restarts")
    versionNumber.set(releaseVersion)
    versionType.set(stage)
    uploadFile.set(jarFile.asFile)
    gameVersions.addAll(mcVersions)
    loaders.addAll("bukkit", "spigot", "paper", "purpur", "folia")
    changelog.set(notes)
}

hangarPublish {
    publications.register("plugin") {
        version.set(releaseVersion)
        id.set(pluginName)
        channel.set(stage.replaceFirstChar { it.uppercase() })
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
