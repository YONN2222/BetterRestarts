package com.yonn2222.betterrestarts.metrics;

import com.yonn2222.betterrestarts.config.Settings;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Supplier;

public final class Stats {
    private Stats() {
    }

    public static void start(JavaPlugin plugin, Supplier<Settings> settings) {
        int id = pluginId(plugin);
        if (id <= 0) {
            return;
        }
        Metrics metrics = new Metrics(plugin, id);
        metrics.addCustomChart(new SimplePie("language", () -> settings.get().language()));
        metrics.addCustomChart(new SimplePie("restart_method", () -> settings.get().restart().method().name().toLowerCase()));
        metrics.addCustomChart(new SimplePie("automatic_schedule", () -> settings.get().auto().enabled() ? "enabled" : "disabled"));
    }

    private static int pluginId(JavaPlugin plugin) {
        Properties properties = new Properties();
        try (InputStream in = plugin.getResource("metrics.properties")) {
            if (in == null) {
                return 0;
            }
            properties.load(in);
        } catch (IOException e) {
            return 0;
        }
        try {
            return Integer.parseInt(properties.getProperty("bstats-id", "0").trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
