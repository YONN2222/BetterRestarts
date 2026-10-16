package com.yonn2222.betterrestarts.config;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Locale;

public record CommandSettings(String name, List<String> aliases, boolean replaceBuiltIn, long maxSeconds) {

    static CommandSettings read(ConfigurationSection section, String maxTime) {
        return new CommandSettings(
                section.getString("name", "restart").toLowerCase(Locale.ROOT),
                section.getStringList("aliases"),
                section.getBoolean("replace-built-in", true),
                com.yonn2222.betterrestarts.time.Durations.parse(maxTime).orElse(86400));
    }
}
