package com.yonn2222.betterrestarts.command;

import com.yonn2222.betterrestarts.BetterRestarts;
import org.bukkit.command.CommandSender;

final class ReloadAction implements Action {
    private final BetterRestarts plugin;

    ReloadAction(BetterRestarts plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "reload";
    }

    @Override
    public String permission() {
        return "betterrestarts.reload";
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        plugin.reloadAll();
        sender.sendMessage(plugin.messages().get("reloaded"));
    }
}
