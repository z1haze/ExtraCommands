package me.stephenhendricks.extracommands.commands;

import me.stephenhendricks.extracommands.ExtraCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {
    private ExtraCommands plugin;

    public Fly(ExtraCommands plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;

        if (sender instanceof Player) {
            player = (Player) sender;
        }

        switch (args.length) {
            case 0:
                // only the fly command with no args
                if (player != null) {
                    // sender is the player
                    this.toggleFlight(player);
                    break;
                } else {
                    // sender is the console
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getPrefix()) + ChatColor.RED + " You must provide a valid player to toggle fly");
                    return false;
                }
            case 1:
                // fly command with 1 arg
                if (player != null) {
                    // sender is the player
                    if (!this.checkFirstArg(args[0], player)) {
                        if (!sender.hasPermission("extracommands.fly.others")) {
                            // player doesn't have permission to give other players flight
                            sender.sendMessage(ChatColor.RED + "You don't have the extracommands.fly.others permission");
                            return false;
                        }
                    }
                    break;
                } else {
                    // sender is the console
                    player = this.plugin.getServer().getPlayer(args[0]);
                    if (player != null) {
                        // toggle flight of the player
                        this.toggleFlight(player);
                        break;
                    } else {
                        // command arg didn't match a player
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getPrefix()) + ChatColor.RED + " Player " + args[0] + " not found");
                        return false;
                    }
                }
            default:
                // more than 1 arg send with the command
                if (player != null && !player.hasPermission("extracommands.fly.others")) {
                    // sender is the player and doesn't have permission to give other players flight
                    sender.sendMessage(ChatColor.RED + "You don't have the extracommands.fly.others permission");
                    return false;
                }
                player = this.plugin.getServer().getPlayer(args[0]);
                if (player != null) {
                    // toggle flight of the player
                    this.checkFirstArg(args[1], player);
                } else {
                    // command arg didn't match a player
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getPrefix()) + ChatColor.RED + " Player " + args[0] + " not found");
                    return false;
                }
        }

        // If the command sender doesnt match the player who's flight was toggled, send them a message confirming their action was complete
        if (player != sender) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getPrefix()) + (player.getAllowFlight() ? "" : ChatColor.RED) + " Flying " + (player.getAllowFlight() ? "enabled" : "disabled") + " for " + args[0] + "!");
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getPrefix()) + (player.getAllowFlight() ? "" : ChatColor.RED) + " Flying " + (player.getAllowFlight() ? "enabled" : "disabled") + "!");
        return true;
    }

    private boolean checkFirstArg(String arg, Player player) {
        switch (arg.toLowerCase()) {
            case "on":
                player.setAllowFlight(true);
                player.setFlying(true);
                return true;
            case "off":
                player.setFlying(false);
                player.setAllowFlight(false);
                return true;
            default:
                return false;
        }
    }

    private void toggleFlight(Player player) {
        if (player.getAllowFlight()) {
            player.setFlying(false);
            player.setAllowFlight(false);
        } else {
            player.setAllowFlight(true);
            player.setFlying(true);
        }
    }
}
