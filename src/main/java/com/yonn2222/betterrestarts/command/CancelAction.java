package com.yonn2222.betterrestarts.command;

import com.yonn2222.betterrestarts.BetterRestarts;
import org.bukkit.command.CommandSender;

final class CancelAction implements Action {
    private final BetterRestarts plugin;

    CancelAction(BetterRestarts plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "cancel";
    }

    @Override
    public String permission() {
        return "betterrestarts.cancel";
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        if (!plugin.manager().cancel(sender.getName())) {
            sender.sendMessage(plugin.messages().get("nothing-running"));
        }
    }
}
