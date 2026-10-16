package com.yonn2222.betterrestarts.restart;

public final class Countdown {
    private final long deadlineNanos;
    private final String reason;
    private final boolean automatic;
    private long lastRemaining;

    public Countdown(long seconds, String reason, boolean automatic) {
        this.deadlineNanos = System.nanoTime() + seconds * 1_000_000_000L;
        this.reason = reason;
        this.automatic = automatic;
        this.lastRemaining = seconds;
    }

    public long remaining() {
        long left = deadlineNanos - System.nanoTime();
        if (left <= 0) {
            return 0;
        }
        return (left + 999_999_999L) / 1_000_000_000L;
    }

    public long lastRemaining() {
        return lastRemaining;
    }

    public void remember(long remaining) {
        this.lastRemaining = remaining;
    }

    public String reason() {
        return reason;
    }

    public boolean automatic() {
        return automatic;
    }
}
