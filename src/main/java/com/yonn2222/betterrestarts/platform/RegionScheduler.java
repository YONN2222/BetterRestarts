package com.yonn2222.betterrestarts.platform;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

final class RegionScheduler implements Scheduler {
    private final JavaPlugin plugin;

    RegionScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Task repeating(Runnable action, long delayTicks, long periodTicks) {
        ScheduledTask task = Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, t -> action.run(), delayTicks, periodTicks);
        return task::cancel;
    }

    @Override
    public void global(Runnable action) {
        Bukkit.getGlobalRegionScheduler().run(plugin, t -> action.run());
    }

    @Override
    public void globalLater(Runnable action, long delayTicks) {
        Bukkit.getGlobalRegionScheduler().runDelayed(plugin, t -> action.run(), Math.max(1, delayTicks));
    }

    @Override
    public void player(Player player, Runnable action, long delayTicks) {
        if (delayTicks <= 0) {
            player.getScheduler().run(plugin, t -> action.run(), null);
        } else {
            player.getScheduler().runDelayed(plugin, t -> action.run(), null, delayTicks);
        }
    }
}
