package com.yonn2222.betterrestarts;

import com.yonn2222.betterrestarts.command.RestartCommand;
import com.yonn2222.betterrestarts.config.CommandSettings;
import com.yonn2222.betterrestarts.config.Settings;
import com.yonn2222.betterrestarts.metrics.Stats;
import com.yonn2222.betterrestarts.platform.Scheduler;
import com.yonn2222.betterrestarts.platform.Task;
import com.yonn2222.betterrestarts.restart.RestartManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.Set;

public final class BetterRestarts extends JavaPlugin implements Listener {
    private static final Set<String> REPLACEABLE = Set.of("org.spigotmc", "org.bukkit.command.defaults", "io.papermc.paper.command");

    private Messages messages;
    private RestartManager manager;
    private RestartCommand command;
    private Scheduler scheduler;
    private Task ticker;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        scheduler = Scheduler.create(this);
        Settings settings = Settings.read(getConfig(), getLogger());
        messages = new Messages(this, settings.language());
        manager = new RestartManager(this, settings, messages);
        registerCommand(settings.command());
        getServer().getPluginManager().registerEvents(this, this);
        Stats.start(this, () -> manager.settings());
        ticker = scheduler.repeating(manager::tick, 20L, 20L);
    }

    @Override
    public void onDisable() {
        if (ticker != null) {
            ticker.cancel();
        }
        if (command != null) {
            command.unregister(commandMap());
        }
    }

    private void registerCommand(CommandSettings settings) {
        CommandMap map = commandMap();
        Map<String, Command> known = map.getKnownCommands();
        Command existing = known.get(settings.name());
        if (existing != null && settings.replaceBuiltIn() && isReplaceable(existing)) {
            known.remove(settings.name());
            getLogger().info("Replaced the built-in /" + settings.name() + " command");
        }
        command = new RestartCommand(this, settings.name(), settings.aliases());
        map.register(getName().toLowerCase(), command);
    }

    private static CommandMap commandMap() {
        try {
            return (CommandMap) Bukkit.getServer().getClass().getMethod("getCommandMap").invoke(Bukkit.getServer());
        } catch (ReflectiveOperationException failure) {
            throw new IllegalStateException("The command map is not available on this server", failure);
        }
    }

    private static boolean isReplaceable(Command existing) {
        if (existing instanceof PluginIdentifiableCommand) {
            return false;
        }
        String name = existing.getClass().getName();
        return REPLACEABLE.stream().anyMatch(name::startsWith);
    }

    public void reloadAll() {
        reloadConfig();
        Settings settings = Settings.read(getConfig(), getLogger());
        messages = new Messages(this, settings.language());
        manager.apply(settings, messages);
    }

    public Scheduler scheduler() {
        return scheduler;
    }

    public Messages messages() {
        return messages;
    }

    public RestartManager manager() {
        return manager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!manager.settings().announcements().showOnJoin()) {
            return;
        }
        scheduler.player(player, () -> manager.active().ifPresent(countdown -> {
            manager.display().scheduled(player, countdown.remaining(), countdown.reason());
            manager.display().actionBar(player, countdown.remaining(), countdown.reason());
        }), 20L);
    }
}
