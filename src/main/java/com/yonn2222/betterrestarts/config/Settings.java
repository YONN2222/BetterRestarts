package com.yonn2222.betterrestarts.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.logging.Logger;

public record Settings(
        String language,
        CommandSettings command,
        Announcements announcements,
        TitleTimes titleTimes,
        SoundSettings sounds,
        RestartSettings restart,
        AutoSchedule auto
) {

    public static Settings read(FileConfiguration config, Logger log) {
        return new Settings(
                config.getString("language", "en"),
                CommandSettings.read(section(config, "command"), config.getString("max-time", "24h")),
                Announcements.read(section(config, "announcements"), log),
                TitleTimes.read(section(config, "title-times")),
                SoundSettings.read(section(config, "sounds")),
                RestartSettings.read(section(config, "restart")),
                AutoSchedule.read(section(config, "schedule"), log));
    }

    private static ConfigurationSection section(ConfigurationSection parent, String name) {
        ConfigurationSection found = parent.getConfigurationSection(name);
        return found != null ? found : new YamlConfiguration();
    }
}
