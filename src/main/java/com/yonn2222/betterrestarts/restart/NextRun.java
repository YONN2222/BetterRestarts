package com.yonn2222.betterrestarts.restart;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

public final class NextRun {
    private NextRun() {
    }

    public static Optional<ZonedDateTime> after(ZonedDateTime now, ZoneId zone, Collection<LocalTime> times, Set<DayOfWeek> days) {
        ZonedDateTime local = now.withZoneSameInstant(zone);
        LocalDate start = local.toLocalDate();
        for (int offset = 0; offset <= 7; offset++) {
            LocalDate date = start.plusDays(offset);
            if (!days.isEmpty() && !days.contains(date.getDayOfWeek())) {
                continue;
            }
            Optional<ZonedDateTime> hit = times.stream()
                    .map(time -> ZonedDateTime.of(date, time, zone))
                    .filter(candidate -> candidate.isAfter(local))
                    .min(Comparator.naturalOrder());
            if (hit.isPresent()) {
                return hit;
            }
        }
        return Optional.empty();
    }
}
