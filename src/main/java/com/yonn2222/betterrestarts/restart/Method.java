package com.yonn2222.betterrestarts.restart;

import java.util.Locale;

public enum Method {
    SPIGOT,
    SHUTDOWN,
    PANEL,
    COMMAND;

    public static Method of(String value) {
        try {
            return valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException | NullPointerException e) {
            return SHUTDOWN;
        }
    }
}
