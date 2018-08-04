package me.stephenhendricks.extracommands.commands;

import me.stephenhendricks.extracommands.ExtraCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal implements CommandExecutor {
    private ExtraCommands plugin;

    public Heal(ExtraCommands plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;

        if ((sender instanceof Player) && !sender.hasPermission("extracommands.heal.others")) {
            sender.sendMessage(ChatColor.RED + "You don't have the extracommands.heal.others permission");
            return false;
        }

        if (args.length > 0) {
            player = this.plugin.getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getPrefix()) + ChatColor.RED + " Player " + args[0] + " not found");
                return false;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getPrefix()) + ChatColor.RED + " You must provide a valid player to heal");
                return false;
            }
        }

        if (player == null) {
            player = (Player) sender;
        }

        player.setHealth(20);

        if (player != sender) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getPrefix()) + " " + args[0] + " has been healed!");
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getPrefix()) + " You have been healed!");

        return true;
    }
}
