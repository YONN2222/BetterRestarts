package com.yonn2222.betterrestarts.config;

import org.bukkit.configuration.ConfigurationSection;

public record PanelSettings(String url, String serverId, String apiKey) {

    static PanelSettings read(ConfigurationSection section) {
        String key = section.getString("api-key", "").trim();
        if (key.isBlank()) {
            key = System.getenv().getOrDefault("BETTERRESTARTS_PANEL_KEY", "").trim();
        }
        String url = section.getString("url", "").trim();
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        return new PanelSettings(url, section.getString("server-id", "").trim(), key);
    }

    public boolean complete() {
        return !url.isBlank() && !serverId.isBlank() && !apiKey.isBlank();
    }
}
