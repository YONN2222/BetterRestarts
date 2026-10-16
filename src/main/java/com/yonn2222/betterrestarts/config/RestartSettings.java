package com.yonn2222.betterrestarts.config;

import com.yonn2222.betterrestarts.restart.Method;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public record RestartSettings(Method method, int delayTicks, boolean kick, String command, PanelSettings panel) {

    static RestartSettings read(ConfigurationSection section) {
        return new RestartSettings(
                Method.of(section.getString("method", "shutdown")),
                Math.max(1, section.getInt("delay-ticks", 40)),
                section.getBoolean("kick-players", true),
                section.getString("command", "stop"),
                PanelSettings.read(panelSection(section)));
    }

    private static ConfigurationSection panelSection(ConfigurationSection parent) {
        ConfigurationSection found = parent.getConfigurationSection("panel");
        return found != null ? found : new YamlConfiguration();
    }
}
