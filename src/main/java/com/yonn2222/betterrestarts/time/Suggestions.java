package com.yonn2222.betterrestarts.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class Suggestions {
    private static final List<String> PRESETS = List.of(
            "now", "30s", "1m", "2m", "5m", "10m", "15m", "30m", "1h", "2h", "6h", "12h");
    private static final List<String> ADJUSTMENTS = List.of("+1m", "+5m", "+10m", "-1m", "-5m");
    private static final List<String> UNITS = List.of("s", "m", "h", "d");

    private Suggestions() {
    }

    public static List<String> forTime(String typed, boolean adjustable) {
        String text = typed.toLowerCase(Locale.ROOT);
        List<String> result = new ArrayList<>();
        if (adjustable && (text.isEmpty() || text.startsWith("+") || text.startsWith("-"))) {
            for (String delta : ADJUSTMENTS) {
                if (delta.startsWith(text)) {
                    result.add(delta);
                }
            }
        }
        if (!text.isEmpty() && Character.isDigit(text.charAt(text.length() - 1))) {
            for (String unit : UNITS) {
                result.add(text + unit);
            }
        }
        for (String preset : PRESETS) {
            if (preset.startsWith(text) && !result.contains(preset)) {
                result.add(preset);
            }
        }
        return result;
    }
}
