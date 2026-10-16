package com.yonn2222.betterrestarts.restart;

import com.yonn2222.betterrestarts.config.PanelSettings;
import com.yonn2222.betterrestarts.config.Settings;
import org.bukkit.Bukkit;
import com.yonn2222.betterrestarts.BetterRestarts;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public final class Restarter {
    private final BetterRestarts plugin;
    private final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();

    public Restarter(BetterRestarts plugin) {
        this.plugin = plugin;
    }

    public void run(Settings settings) {
        switch (settings.restart().method()) {
            case SPIGOT -> Bukkit.spigot().restart();
            case SHUTDOWN -> Bukkit.shutdown();
            case COMMAND -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), settings.restart().command());
            case PANEL -> callPanel(settings.restart().panel());
        }
    }

    private void callPanel(PanelSettings panel) {
        if (!panel.complete()) {
            plugin.getLogger().warning("Panel restart is not configured, shutting down instead");
            Bukkit.shutdown();
            return;
        }
        HttpRequest request = HttpRequest.newBuilder(URI.create(panel.url() + "/api/client/servers/" + panel.serverId() + "/power"))
                .timeout(Duration.ofSeconds(15))
                .header("Authorization", "Bearer " + panel.apiKey())
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"signal\":\"restart\"}"))
                .build();
        http.sendAsync(request, HttpResponse.BodyHandlers.discarding()).whenComplete((response, error) -> {
            if (error != null || response.statusCode() >= 300) {
                String reason = error != null ? error.getMessage() : "HTTP " + response.statusCode();
                plugin.getLogger().warning("Panel restart failed (" + reason + "), shutting down instead");
                plugin.scheduler().global(Bukkit::shutdown);
            }
        });
    }
}
