package me.ketchup.ketchupplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FreeStaffCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String pluginName = ChatColor.translateAlternateColorCodes('&', "&e[&cMyPlugin&e]&r: ");

        // Check if the sender has permission to use the command
        if (!sender.hasPermission("freestaff.use")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        // If no player is specified, set the sender as operator
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.setOp(true);
                sender.sendMessage(pluginName + ChatColor.YELLOW + "You've been granted operator status.");
            } else {
                sender.sendMessage(pluginName + ChatColor.RED + "You need to specify a player name.");
            }
            return true;
        }

        // If a player is specified, set that player as operator
        String playerName = args[0];
        Player target = Bukkit.getPlayer(playerName);
        if (target != null) {
            target.setOp(true);
            sender.sendMessage(pluginName + "You've granted operator status to " + ChatColor.YELLOW + playerName + ChatColor.RESET + ".");
        } else {
            sender.sendMessage(pluginName + ChatColor.RED + playerName + " is not a valid player name.");
        }
        return true;
    }
}
