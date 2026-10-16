package com.yonn2222.betterrestarts.platform;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface Scheduler {
    Task repeating(Runnable action, long delayTicks, long periodTicks);

    void global(Runnable action);

    void globalLater(Runnable action, long delayTicks);

    void player(Player player, Runnable action, long delayTicks);

    static Scheduler create(JavaPlugin plugin) {
        try {
            Class.forName("io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler");
            return new RegionScheduler(plugin);
        } catch (ClassNotFoundException missing) {
            return new ClassicScheduler(plugin);
        }
    }
}
