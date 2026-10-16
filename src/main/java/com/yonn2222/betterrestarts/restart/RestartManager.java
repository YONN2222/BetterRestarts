package com.yonn2222.betterrestarts.restart;

import com.yonn2222.betterrestarts.Messages;
import com.yonn2222.betterrestarts.config.AutoSchedule;
import com.yonn2222.betterrestarts.config.Settings;
import com.yonn2222.betterrestarts.display.Display;
import com.yonn2222.betterrestarts.time.Milestones;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.yonn2222.betterrestarts.BetterRestarts;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;

public final class RestartManager {
    private final BetterRestarts plugin;
    private final Restarter restarter;
    private final ZonedDateTime startedAt = ZonedDateTime.now();

    private Settings settings;
    private Display display;
    private Countdown active;
    private ZonedDateTime autoOccurrence;
    private ZonedDateTime skipped;

    public RestartManager(BetterRestarts plugin, Settings settings, Messages messages) {
        this.plugin = plugin;
        this.restarter = new Restarter(plugin);
        apply(settings, messages);
    }

    public synchronized void apply(Settings settings, Messages messages) {
        this.settings = settings;
        this.display = new Display(messages, settings, plugin.scheduler());
    }

    public synchronized Settings settings() {
        return settings;
    }

    public synchronized Display display() {
        return display;
    }

    public synchronized Optional<Countdown> active() {
        return Optional.ofNullable(active);
    }

    public synchronized void schedule(long seconds, String reason) {
        String text = reason;
        if ((text == null || text.isBlank()) && active != null) {
            text = active.reason();
        }
        begin(seconds, text, false, null);
    }

    private void begin(long seconds, String reason, boolean automatic, ZonedDateTime occurrence) {
        if (seconds <= 0) {
            active = null;
            execute();
            return;
        }
        active = new Countdown(seconds, reason, automatic);
        autoOccurrence = occurrence;
        display.scheduledAll(seconds, reason);
        Milestones milestones = settings.announcements().milestones();
        if (milestones.titleOnSchedule(seconds)) {
            display.title(seconds, reason);
        }
        display.actionBarAll(seconds, reason);
    }

    public synchronized boolean cancel(String by) {
        if (active == null) {
            return false;
        }
        if (active.automatic() && autoOccurrence != null) {
            skipped = autoOccurrence;
        }
        active = null;
        autoOccurrence = null;
        display.cancelled(by);
        return true;
    }

    public synchronized void tick() {
        if (active == null) {
            checkAutomatic();
            return;
        }
        long remaining = active.remaining();
        for (Milestones.Step step : settings.announcements().milestones().crossed(active.lastRemaining(), remaining)) {
            if (step.countdown()) {
                display.countdown(step.remaining());
            } else if (step.title()) {
                display.title(step.remaining(), active.reason());
            }
            if (step.chat()) {
                display.reminder(step.remaining(), active.reason());
            }
        }
        active.remember(remaining);
        if (remaining <= 0) {
            active = null;
            autoOccurrence = null;
            execute();
            return;
        }
        display.actionBarAll(remaining, active.reason());
    }

    public synchronized Optional<ZonedDateTime> nextAutomatic() {
        AutoSchedule auto = settings.auto();
        if (!auto.enabled()) {
            return Optional.empty();
        }
        return NextRun.after(ZonedDateTime.now(), auto.zone(), auto.times(), auto.days());
    }

    private void checkAutomatic() {
        AutoSchedule auto = settings.auto();
        Optional<ZonedDateTime> next = nextAutomatic();
        if (next.isEmpty()) {
            return;
        }
        ZonedDateTime occurrence = next.get();
        long until = Duration.between(ZonedDateTime.now(), occurrence).getSeconds();
        if (until > auto.announceBefore() || occurrence.equals(skipped)) {
            return;
        }
        if (!startedAt.isBefore(occurrence.minusSeconds(auto.announceBefore()))) {
            return;
        }
        begin(until, auto.reason(), true, occurrence);
    }

    private void execute() {
        display.restarting();
        Settings snapshot = settings;
        plugin.scheduler().globalLater(() -> {
            if (snapshot.restart().kick()) {
                String kick = display.kickMessage();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    plugin.scheduler().player(player, () -> player.kickPlayer(kick), 0);
                }
            }
            plugin.scheduler().globalLater(() -> restarter.run(snapshot), 20L);
        }, snapshot.restart().delayTicks());
    }
}
