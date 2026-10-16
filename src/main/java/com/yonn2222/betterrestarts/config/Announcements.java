package com.yonn2222.betterrestarts.config;

import com.yonn2222.betterrestarts.time.Milestones;
import org.bukkit.configuration.ConfigurationSection;

import java.util.logging.Logger;

public record Announcements(boolean actionBar, boolean chat, boolean titles, boolean showOnJoin, Milestones milestones) {

    static Announcements read(ConfigurationSection section, Logger log) {
        Milestones milestones = new Milestones(
                Parse.seconds(section.getStringList("chat-at"), log),
                Parse.seconds(section.getStringList("titles-at"), log),
                Math.max(0, section.getLong("countdown-from", 10)));
        return new Announcements(
                section.getBoolean("action-bar", true),
                section.getBoolean("chat", true),
                section.getBoolean("titles", true),
                section.getBoolean("show-on-join", true),
                milestones);
    }
}
