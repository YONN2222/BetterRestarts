package com.yonn2222.betterrestarts.command;

import org.bukkit.command.CommandSender;

interface Action {

    String name();

    String permission();

    void run(CommandSender sender, String label, String[] args);
}
