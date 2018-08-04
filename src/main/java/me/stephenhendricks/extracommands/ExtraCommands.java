package me.stephenhendricks.extracommands;

import me.stephenhendricks.extracommands.commands.Feed;
import me.stephenhendricks.extracommands.commands.Fly;
import me.stephenhendricks.extracommands.commands.Heal;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExtraCommands extends JavaPlugin {
    private String prefix;

    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.prefix = getConfig().getString("prefix");
        getServer().getPluginCommand("heal").setExecutor(new Heal(this));
        getServer().getPluginCommand("feed").setExecutor(new Feed(this));
        getServer().getPluginCommand("fly").setExecutor(new Fly(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
