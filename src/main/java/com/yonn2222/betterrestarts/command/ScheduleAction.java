package com.yonn2222.betterrestarts.command;

import com.yonn2222.betterrestarts.BetterRestarts;
import com.yonn2222.betterrestarts.Messages;
import com.yonn2222.betterrestarts.restart.Countdown;
import com.yonn2222.betterrestarts.time.Durations;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalLong;

final class ScheduleAction implements Action {
    private final BetterRestarts plugin;

    ScheduleAction(BetterRestarts plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "restart";
    }

    @Override
    public String permission() {
        return "betterrestarts.restart";
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        Messages messages = plugin.messages();
        String input = args[0];
        boolean relative = input.startsWith("+") || input.startsWith("-");
        OptionalLong parsed = Durations.parse(relative ? input.substring(1) : input);
        if (parsed.isEmpty()) {
            sender.sendMessage(messages.get("invalid-time", Messages.text("input", input), Messages.text("label", label)));
            return;
        }
        Optional<Countdown> running = plugin.manager().active();
        long seconds = parsed.getAsLong();
        if (relative) {
            if (running.isEmpty()) {
                sender.sendMessage(messages.get("nothing-running"));
                return;
            }
            long base = running.get().remaining();
            seconds = input.startsWith("+") ? base + seconds : base - seconds;
            if (seconds <= 0) {
                sender.sendMessage(messages.get("too-short"));
                return;
            }
        }
        long limit = plugin.manager().settings().command().maxSeconds();
        if (seconds > limit) {
            sender.sendMessage(messages.get("too-long", Messages.text("max", Durations.compact(limit))));
            return;
        }
        String reason = args.length > 1 ? String.join(" ", Arrays.copyOfRange(args, 1, args.length)) : "";
        long target = seconds;
        running.ifPresent(old -> sender.sendMessage(messages.get("rescheduled",
                Messages.text("old", Durations.compact(old.remaining())),
                Messages.text("new", Durations.compact(target)))));
        plugin.manager().schedule(seconds, reason);
    }
}
