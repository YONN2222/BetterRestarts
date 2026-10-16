package com.yonn2222.betterrestarts.config;

import com.yonn2222.betterrestarts.time.Durations;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

final class Parse {
    private Parse() {
    }

    static Set<Long> seconds(List<String> raw, Logger log) {
        Set<Long> out = new HashSet<>();
        for (String entry : raw) {
            Durations.parse(entry).ifPresentOrElse(out::add, () -> log.warning("Ignoring invalid time '" + entry + "' in config.yml"));
        }
        return out;
    }

    static ZoneId zone(String name, Logger log) {
        if (name == null || name.equalsIgnoreCase("system")) {
            return ZoneId.systemDefault();
        }
        try {
            return ZoneId.of(name);
        } catch (DateTimeException e) {
            log.warning("Unknown timezone '" + name + "', using the system timezone");
            return ZoneId.systemDefault();
        }
    }

    static List<LocalTime> times(List<String> raw, Logger log) {
        List<LocalTime> out = new ArrayList<>();
        for (String entry : raw) {
            try {
                out.add(LocalTime.parse(entry.trim()));
            } catch (DateTimeParseException e) {
                log.warning("Ignoring invalid schedule time '" + entry + "', expected HH:mm");
            }
        }
        return out;
    }

    static Set<DayOfWeek> days(List<String> raw, Logger log) {
        Set<DayOfWeek> out = new HashSet<>();
        for (String entry : raw) {
            try {
                out.add(DayOfWeek.valueOf(entry.trim().toUpperCase(Locale.ROOT)));
            } catch (IllegalArgumentException e) {
                log.warning("Ignoring invalid weekday '" + entry + "'");
            }
        }
        return out;
    }
}
