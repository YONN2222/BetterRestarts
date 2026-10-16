package com.yonn2222.betterrestarts.config;

import org.bukkit.configuration.ConfigurationSection;

public record SoundSettings(boolean enabled, String tick, String finish, float volume, float pitch, float urgentPitch) {

    static SoundSettings read(ConfigurationSection section) {
        return new SoundSettings(
                section.getBoolean("enabled", true),
                section.getString("tick", "block.note_block.pling"),
                section.getString("final", "entity.ender_dragon.growl"),
                (float) section.getDouble("volume", 1.0),
                (float) section.getDouble("pitch", 1.0),
                (float) section.getDouble("urgent-pitch", 1.6));
    }
}
