package com.yonn2222.betterrestarts.command;

import com.yonn2222.betterrestarts.BetterRestarts;
import com.yonn2222.betterrestarts.Messages;
import com.yonn2222.betterrestarts.restart.Countdown;
import com.yonn2222.betterrestarts.time.Durations;
import org.bukkit.command.CommandSender;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

final class StatusAction implements Action {
    private static final DateTimeFormatter STAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z");

    private final BetterRestarts plugin;

    StatusAction(BetterRestarts plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "status";
    }

    @Override
    public String permission() {
        return "betterrestarts.status";
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        Messages messages = plugin.messages();
        Optional<Countdown> active = plugin.manager().active();
        if (active.isPresent()) {
            Countdown countdown = active.get();
            String key = countdown.reason().isBlank() ? "status-running" : "status-running-reason";
            sender.sendMessage(messages.get(key,
                    Messages.text("time", Durations.compact(countdown.remaining())),
                    Messages.text("reason", countdown.reason())));
        } else {
            sender.sendMessage(messages.get("status-idle"));
        }
        plugin.manager().nextAutomatic().ifPresent(next -> {
            ZonedDateTime local = next.withZoneSameInstant(plugin.manager().settings().auto().zone());
            sender.sendMessage(messages.get("status-next", Messages.text("when", STAMP.format(local))));
        });
    }
}
