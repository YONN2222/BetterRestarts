package com.yonn2222.betterrestarts.config;

import com.yonn2222.betterrestarts.time.Durations;
import org.bukkit.configuration.ConfigurationSection;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public record AutoSchedule(boolean enabled, ZoneId zone, List<LocalTime> times, Set<DayOfWeek> days, long announceBefore, String reason) {

    static AutoSchedule read(ConfigurationSection section, Logger log) {
        return new AutoSchedule(
                section.getBoolean("enabled", false),
                Parse.zone(section.getString("timezone", "system"), log),
                Parse.times(section.getStringList("times"), log),
                Parse.days(section.getStringList("days"), log),
                Durations.parse(section.getString("announce-before", "10m")).orElse(600),
                section.getString("reason", ""));
    }
}
