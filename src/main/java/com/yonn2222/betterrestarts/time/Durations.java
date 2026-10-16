package com.yonn2222.betterrestarts.time;

import java.util.Locale;
import java.util.OptionalLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Durations {
    private static final Pattern WHOLE = Pattern.compile("(?:\\d{1,9}[a-z]*)+");
    private static final Pattern PART = Pattern.compile("(\\d{1,9})([a-z]*)");

    private Durations() {
    }

    public static OptionalLong parse(String input) {
        if (input == null) {
            return OptionalLong.empty();
        }
        String text = input.toLowerCase(Locale.ROOT).trim();
        if (text.equals("now")) {
            return OptionalLong.of(0);
        }
        if (!WHOLE.matcher(text).matches()) {
            return OptionalLong.empty();
        }
        Matcher matcher = PART.matcher(text);
        boolean single = text.chars().filter(Character::isLetter).count() == 0;
        long total = 0;
        while (matcher.find()) {
            long amount = Long.parseLong(matcher.group(1));
            String unit = matcher.group(2);
            long factor = single ? 60 : factorOf(unit);
            if (factor == 0) {
                return OptionalLong.empty();
            }
            total += amount * factor;
        }
        return OptionalLong.of(total);
    }

    private static long factorOf(String unit) {
        return switch (unit) {
            case "s", "sec", "secs", "second", "seconds" -> 1;
            case "m", "min", "mins", "minute", "minutes" -> 60;
            case "h", "hr", "hrs", "hour", "hours" -> 3600;
            case "d", "day", "days" -> 86400;
            default -> 0;
        };
    }

    public static String clock(long seconds) {
        long safe = Math.max(0, seconds);
        long hours = safe / 3600;
        long minutes = safe % 3600 / 60;
        long secs = safe % 60;
        if (hours > 0) {
            return String.format(Locale.ROOT, "%d:%02d:%02d", hours, minutes, secs);
        }
        return String.format(Locale.ROOT, "%02d:%02d", minutes, secs);
    }

    public static String compact(long seconds) {
        long safe = Math.max(0, seconds);
        if (safe == 0) {
            return "0s";
        }
        StringBuilder out = new StringBuilder();
        append(out, safe / 86400, "d");
        append(out, safe % 86400 / 3600, "h");
        append(out, safe % 3600 / 60, "m");
        append(out, safe % 60, "s");
        return out.toString();
    }

    private static void append(StringBuilder out, long value, String unit) {
        if (value == 0) {
            return;
        }
        if (!out.isEmpty()) {
            out.append(' ');
        }
        out.append(value).append(unit);
    }
}
