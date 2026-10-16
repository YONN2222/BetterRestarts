package com.yonn2222.betterrestarts.display;

import com.yonn2222.betterrestarts.Messages;
import com.yonn2222.betterrestarts.config.Settings;
import com.yonn2222.betterrestarts.config.TitleTimes;
import com.yonn2222.betterrestarts.platform.Scheduler;
import com.yonn2222.betterrestarts.time.Durations;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.SoundCategory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class Display {
    private final Messages messages;
    private final Settings settings;
    private final Scheduler scheduler;

    public Display(Messages messages, Settings settings, Scheduler scheduler) {
        this.messages = messages;
        this.settings = settings;
        this.scheduler = scheduler;
    }

    private TagResolver[] placeholders(long remaining, String reason) {
        return new TagResolver[]{
                Messages.text("time", Durations.compact(remaining)),
                Messages.text("clock", Durations.clock(remaining)),
                Messages.text("seconds", Long.toString(remaining)),
                Messages.text("reason", reason == null ? "" : reason)
        };
    }

    private static String key(String base, String reason) {
        return reason == null || reason.isBlank() ? base : base + "-reason";
    }

    private static void bar(Player player, String text) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
    }

    private static void title(Player player, String main, String sub, TitleTimes.Times times) {
        player.sendTitle(main, sub, times.fadeIn(), times.stay(), times.fadeOut());
    }

    public void actionBar(Player player, long remaining, String reason) {
        if (!settings.announcements().actionBar()) {
            return;
        }
        bar(player, messages.get(key("actionbar", reason), placeholders(remaining, reason)));
    }

    public void actionBarAll(long remaining, String reason) {
        if (!settings.announcements().actionBar()) {
            return;
        }
        String text = messages.get(key("actionbar", reason), placeholders(remaining, reason));
        for (Player player : Bukkit.getOnlinePlayers()) {
            bar(player, text);
        }
    }

    public void scheduled(CommandSender target, long remaining, String reason) {
        if (!settings.announcements().chat()) {
            return;
        }
        target.sendMessage(messages.get(key("scheduled", reason), placeholders(remaining, reason)));
    }

    public void scheduledAll(long remaining, String reason) {
        if (!settings.announcements().chat()) {
            return;
        }
        broadcast(messages.get(key("scheduled", reason), placeholders(remaining, reason)));
    }

    public void reminder(long remaining, String reason) {
        if (!settings.announcements().chat()) {
            return;
        }
        broadcast(messages.get(key("reminder", reason), placeholders(remaining, reason)));
    }

    public void title(long remaining, String reason) {
        if (!settings.announcements().titles()) {
            return;
        }
        String main = messages.get("title-main", placeholders(remaining, reason));
        String sub = messages.get(key("title-sub", reason), placeholders(remaining, reason));
        for (Player player : Bukkit.getOnlinePlayers()) {
            title(player, main, sub, settings.titleTimes().announce());
        }
        play(settings.sounds().tick(), settings.sounds().pitch());
    }

    public void countdown(long remaining) {
        if (!settings.announcements().titles()) {
            return;
        }
        String main = messages.get("countdown-main", placeholders(remaining, null));
        String sub = messages.get("countdown-sub", placeholders(remaining, null));
        for (Player player : Bukkit.getOnlinePlayers()) {
            title(player, main, sub, settings.titleTimes().countdown());
        }
        play(settings.sounds().tick(), remaining <= 3 ? settings.sounds().urgentPitch() : settings.sounds().pitch());
    }

    public void restarting() {
        String main = messages.get("restarting-main");
        String sub = messages.get("restarting-sub");
        for (Player player : Bukkit.getOnlinePlayers()) {
            title(player, main, sub, settings.titleTimes().announce());
        }
        play(settings.sounds().finish(), settings.sounds().pitch());
    }

    public void cancelled(String by) {
        broadcast(messages.get("cancelled-chat", Messages.text("player", by)));
        String main = messages.get("cancelled-title");
        String sub = messages.get("cancelled-sub");
        for (Player player : Bukkit.getOnlinePlayers()) {
            title(player, main, sub, settings.titleTimes().announce());
            bar(player, "");
        }
        play(settings.sounds().tick(), settings.sounds().pitch() * 0.6f);
    }

    public String kickMessage() {
        return messages.get("kick");
    }

    private static void broadcast(String text) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(text);
        }
        Bukkit.getConsoleSender().sendMessage(text);
    }

    private void play(String sound, float pitch) {
        if (!settings.sounds().enabled()) {
            return;
        }
        float volume = settings.sounds().volume();
        for (Player player : Bukkit.getOnlinePlayers()) {
            scheduler.player(player, () -> player.playSound(player.getLocation(), sound, SoundCategory.MASTER, volume, pitch), 0);
        }
    }
}
