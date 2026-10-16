package com.yonn2222.betterrestarts.time;

import org.junit.jupiter.api.Test;

import java.util.OptionalLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DurationsTest {

    @Test
    void parsesSingleUnits() {
        assertEquals(OptionalLong.of(30), Durations.parse("30s"));
        assertEquals(OptionalLong.of(120), Durations.parse("2m"));
        assertEquals(OptionalLong.of(300), Durations.parse("5min"));
        assertEquals(OptionalLong.of(3600), Durations.parse("1h"));
        assertEquals(OptionalLong.of(86400), Durations.parse("1d"));
    }

    @Test
    void parsesCompoundValues() {
        assertEquals(OptionalLong.of(5400), Durations.parse("1h30m"));
        assertEquals(OptionalLong.of(3661), Durations.parse("1h1m1s"));
        assertEquals(OptionalLong.of(90), Durations.parse("1m30s"));
    }

    @Test
    void nowIsZeroAndIgnoresCase() {
        assertEquals(OptionalLong.of(0), Durations.parse("now"));
        assertEquals(OptionalLong.of(0), Durations.parse("NOW"));
        assertEquals(OptionalLong.of(600), Durations.parse("10M"));
    }

    @Test
    void bareNumbersMeanMinutes() {
        assertEquals(OptionalLong.of(300), Durations.parse("5"));
    }

    @Test
    void rejectsGarbage() {
        assertTrue(Durations.parse("").isEmpty());
        assertTrue(Durations.parse("soon").isEmpty());
        assertTrue(Durations.parse("5x").isEmpty());
        assertTrue(Durations.parse("-5m").isEmpty());
        assertTrue(Durations.parse("1h ").isPresent());
        assertTrue(Durations.parse(null).isEmpty());
        assertTrue(Durations.parse("99999999999m").isEmpty());
    }

    @Test
    void formatsClock() {
        assertEquals("00:09", Durations.clock(9));
        assertEquals("04:32", Durations.clock(272));
        assertEquals("1:04:32", Durations.clock(3872));
        assertEquals("00:00", Durations.clock(-4));
    }

    @Test
    void formatsCompact() {
        assertEquals("0s", Durations.compact(0));
        assertEquals("45s", Durations.compact(45));
        assertEquals("5m", Durations.compact(300));
        assertEquals("1h 30m", Durations.compact(5400));
        assertEquals("1d 2h 3m 4s", Durations.compact(93784));
    }
}
