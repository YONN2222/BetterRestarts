package com.yonn2222.betterrestarts.restart;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NextRunTest {
    private static final ZoneId BERLIN = ZoneId.of("Europe/Berlin");
    private static final List<LocalTime> TIMES = List.of(LocalTime.of(4, 0), LocalTime.of(16, 30));

    private static ZonedDateTime at(int day, int hour, int minute) {
        return ZonedDateTime.of(2026, 10, day, hour, minute, 0, 0, BERLIN);
    }

    @Test
    void picksTheNextTimeToday() {
        assertEquals(at(16, 16, 30), NextRun.after(at(16, 10, 0), BERLIN, TIMES, Set.of()).orElseThrow());
    }

    @Test
    void rollsOverToTomorrow() {
        assertEquals(at(17, 4, 0), NextRun.after(at(16, 20, 0), BERLIN, TIMES, Set.of()).orElseThrow());
    }

    @Test
    void ignoresTheCurrentSecondItself() {
        assertEquals(at(16, 16, 30), NextRun.after(at(16, 4, 0), BERLIN, TIMES, Set.of()).orElseThrow());
    }

    @Test
    void respectsWeekdays() {
        ZonedDateTime next = NextRun.after(at(16, 10, 0), BERLIN, TIMES, Set.of(DayOfWeek.MONDAY)).orElseThrow();
        assertEquals(DayOfWeek.MONDAY, next.getDayOfWeek());
        assertEquals(at(19, 4, 0), next);
    }

    @Test
    void convertsIntoTheConfiguredZone() {
        ZonedDateTime utcNow = at(16, 10, 0).withZoneSameInstant(ZoneId.of("UTC"));
        assertEquals(at(16, 16, 30), NextRun.after(utcNow, BERLIN, TIMES, Set.of()).orElseThrow());
    }

    @Test
    void emptyWithoutTimes() {
        assertTrue(NextRun.after(at(16, 10, 0), BERLIN, List.of(), Set.of()).isEmpty());
    }
}
