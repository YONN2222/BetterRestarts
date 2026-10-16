package com.yonn2222.betterrestarts.config;

import org.bukkit.configuration.ConfigurationSection;

public record TitleTimes(Times announce, Times countdown) {

    public record Times(int fadeIn, int stay, int fadeOut) {
    }

    static TitleTimes read(ConfigurationSection section) {
        return new TitleTimes(
                times(section, "announce", 0.25, 3.0, 0.5),
                times(section, "countdown", 0.0, 1.1, 0.1));
    }

    private static Times times(ConfigurationSection section, String name, double in, double stay, double out) {
        ConfigurationSection part = section.getConfigurationSection(name);
        if (part == null) {
            return new Times(ticks(in), ticks(stay), ticks(out));
        }
        return new Times(
                ticks(part.getDouble("fade-in", in)),
                ticks(part.getDouble("stay", stay)),
                ticks(part.getDouble("fade-out", out)));
    }

    private static int ticks(double seconds) {
        return (int) Math.max(0, Math.round(seconds * 20));
    }
}
