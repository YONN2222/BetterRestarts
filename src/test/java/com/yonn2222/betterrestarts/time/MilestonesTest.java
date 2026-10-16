package com.yonn2222.betterrestarts.time;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MilestonesTest {
    private final Milestones milestones = new Milestones(Set.of(600L, 60L), Set.of(600L, 300L, 60L), 10);

    @Test
    void firesChatAndTitleOnTheExactSecond() {
        List<Milestones.Step> steps = milestones.crossed(601, 600);
        assertEquals(1, steps.size());
        assertTrue(steps.getFirst().chat());
        assertTrue(steps.getFirst().title());
        assertFalse(steps.getFirst().countdown());
    }

    @Test
    void titleOnlyWhereChatIsNotListed() {
        Milestones.Step step = milestones.crossed(301, 300).getFirst();
        assertFalse(step.chat());
        assertTrue(step.title());
    }

    @Test
    void countdownRunsEverySecondFromTen() {
        List<Milestones.Step> steps = milestones.crossed(11, 0);
        assertEquals(10, steps.size());
        assertEquals(10, steps.getFirst().remaining());
        assertEquals(1, steps.getLast().remaining());
        assertTrue(steps.stream().allMatch(Milestones.Step::countdown));
    }

    @Test
    void catchesUpAfterALagSpike() {
        List<Milestones.Step> steps = milestones.crossed(65, 58);
        assertEquals(List.of(60L), steps.stream().map(Milestones.Step::remaining).toList());
    }

    @Test
    void nothingBetweenMilestones() {
        assertTrue(milestones.crossed(500, 499).isEmpty());
    }

    @Test
    void titleOnScheduleOnlyInsideTheLargestWindow() {
        assertFalse(milestones.titleOnSchedule(3600));
        assertTrue(milestones.titleOnSchedule(600));
        assertTrue(milestones.titleOnSchedule(120));
        assertFalse(milestones.titleOnSchedule(8));
    }
}
