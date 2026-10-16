package com.yonn2222.betterrestarts.command;

import com.yonn2222.betterrestarts.BetterRestarts;
import com.yonn2222.betterrestarts.time.Suggestions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class RestartCommand extends Command implements PluginIdentifiableCommand {
    private final BetterRestarts plugin;
    private final Action schedule;
    private final Map<String, Action> named;

    public RestartCommand(BetterRestarts plugin, String name, List<String> aliases) {
        super(name, "Schedule or cancel a server restart", "/" + name + " <time|cancel|status|reload> [reason]", aliases);
        this.plugin = plugin;
        this.schedule = new ScheduleAction(plugin);
        List<Action> actions = List.of(new CancelAction(plugin), new StatusAction(plugin), new ReloadAction(plugin));
        this.named = Map.of(
                "cancel", actions.get(0),
                "status", actions.get(1),
                "reload", actions.get(2));
        setPermission("betterrestarts.restart;betterrestarts.cancel;betterrestarts.status;betterrestarts.reload");
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(plugin.messages().get("usage", com.yonn2222.betterrestarts.Messages.text("label", label)));
            return true;
        }
        Action action = named.getOrDefault(args[0].toLowerCase(Locale.ROOT), schedule);
        if (!sender.hasPermission(action.permission())) {
            sender.sendMessage(plugin.messages().get("no-permission"));
            return true;
        }
        action.run(sender, label, args);
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if (args.length != 1) {
            return List.of();
        }
        String typed = args[0].toLowerCase(Locale.ROOT);
        List<String> out = new ArrayList<>();
        if (sender.hasPermission(schedule.permission())) {
            boolean running = plugin.manager().active().isPresent();
            out.addAll(Suggestions.forTime(typed, running));
        }
        named.forEach((name, action) -> {
            if (name.startsWith(typed) && sender.hasPermission(action.permission())) {
                out.add(name);
            }
        });
        return out;
    }
}
