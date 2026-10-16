package com.yonn2222.betterrestarts.time;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SuggestionsTest {

    @Test
    void offersPresetsForEmptyInput() {
        List<String> all = Suggestions.forTime("", false);
        assertTrue(all.contains("now"));
        assertTrue(all.contains("5m"));
        assertTrue(all.contains("1h"));
    }

    @Test
    void completesUnitsAfterDigits() {
        List<String> out = Suggestions.forTime("5", false);
        assertTrue(out.containsAll(List.of("5s", "5m", "5h", "5d")));
    }

    @Test
    void filtersPresetsByPrefix() {
        assertEquals(List.of("now"), Suggestions.forTime("no", false));
    }

    @Test
    void doesNotDuplicateEntries() {
        List<String> out = Suggestions.forTime("1", false);
        assertEquals(out.size(), out.stream().distinct().count());
    }

    @Test
    void offersAdjustmentsOnlyWhileCountingDown() {
        assertTrue(Suggestions.forTime("", true).contains("+5m"));
        assertTrue(Suggestions.forTime("+", true).contains("+10m"));
        assertTrue(Suggestions.forTime("-", false).isEmpty());
    }
}
