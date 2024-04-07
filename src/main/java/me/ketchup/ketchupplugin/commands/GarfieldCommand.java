package me.ketchup.ketchupplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class GarfieldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String pluginName = ChatColor.translateAlternateColorCodes('&', "&e[&cketchupPlugin&e]&r: ");

        if (!sender.hasPermission("garfield.use")) {
            sender.sendMessage(pluginName + ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(pluginName + ChatColor.RED + "Usage: /garfield <owner>");
            return true;
        }

        Player owner = null;

        if (args.length == 1) {
            owner = sender.getServer().getPlayer(args[0]);
            if (owner == null || !owner.isOnline()) {
                sender.sendMessage(pluginName + ChatColor.RED + "Player not found or is not online.");
                return true;
            }
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(pluginName + ChatColor.RED + "Usage: /garfield <owner>");
            return true;
        } else {
            owner = (Player) sender;
        }

        if (owner != null) {
            // Spawn a cat named Garfield owned by the specified player
            Cat garfield = (Cat) owner.getWorld().spawnEntity(owner.getLocation().add(1, 0, 0), EntityType.CAT);
            garfield.setOwner(owner); //sets you as the owner of garfield
            garfield.setCustomName(ChatColor.GOLD + "Garfield");
            garfield.setCustomNameVisible(true);
            garfield.setCatType(Cat.Type.RED); // Set cat color to orange

            sender.sendMessage(pluginName + ChatColor.GOLD + "Garfield " + ChatColor.GREEN + "has been summoned and is owned by " + ChatColor.YELLOW + owner.getName());
            return true;
        }

        return false;
    }
}
