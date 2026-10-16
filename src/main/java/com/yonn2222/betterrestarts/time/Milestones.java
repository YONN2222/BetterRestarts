package com.yonn2222.betterrestarts.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record Milestones(Set<Long> chat, Set<Long> titles, long countdownFrom) {

    public record Step(long remaining, boolean chat, boolean title, boolean countdown) {
    }

    public List<Step> crossed(long previous, long current) {
        List<Step> steps = new ArrayList<>();
        for (long second = previous - 1; second >= current; second--) {
            boolean countdown = second >= 1 && second <= countdownFrom;
            boolean chatNow = chat.contains(second);
            boolean titleNow = titles.contains(second);
            if (countdown || chatNow || titleNow) {
                steps.add(new Step(second, chatNow, titleNow, countdown));
            }
        }
        return steps;
    }

    public boolean titleOnSchedule(long remaining) {
        return titles.stream().anyMatch(limit -> remaining <= limit) && remaining > countdownFrom;
    }
}
