package me.ketchup.ketchupplugin.commands;

import me.ketchup.ketchupplugin.ketchupPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CreeperEncirclementCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String pluginName = ChatColor.translateAlternateColorCodes('&', "&e[&cketchupPlugin&e]&r: ");

        // Check permission for using the command
        if (!sender.hasPermission("creeperencirclement.use")) {
            sender.sendMessage(pluginName + ChatColor.RED + "you dont have permission to use this command noob");
            return true;
        }

        // Check if enough arguments are provided
        if (args.length < 2 || args.length > 3) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', pluginName + "how to use: /creeperencirclement <player> <radius> [charged/true]"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        // Check if the specified player is online
        if (target == null || !target.isOnline()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', pluginName + "&c" + args[0] + "&r is currently offline"));
            return true;
        }

        // Parse the radius
        double radius;
        try {
            radius = Double.parseDouble(args[1]);
            if (radius < 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', pluginName + "&cradius must be a positive number"));
            }

            if (radius > 420) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', pluginName + "&cyou can only enter a number up to 420"));
                return true;
            }
            if (radius == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', pluginName + "&cradius cant be zero blub"));
                return true;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', pluginName + "&center something valid dude"));
            return true;
        }

        // Determine creeper type
        boolean charged = false;
        if (args.length == 3 && args[2].equalsIgnoreCase("charged") || args.length == 3 && args[2].equalsIgnoreCase("true")) {
            charged = true;
        }

        // Get the world where the creepers will spawn
        World world = target.getWorld();

        // Get player's location
        Location playerLocation = target.getLocation();

        // Calculate circumference
        double circumference = 2 * Math.PI * radius;

        // Calculate number of creepers based on circumference
        int numCreepers = (int) (circumference / 2); // 2 blocks apart between creepers

        // Spawn creepers in a circle around the player
        for (int i = 0; i < numCreepers; i++) {
            double angle = 2 * Math.PI * i / numCreepers;
            double x = playerLocation.getX() + radius * Math.cos(angle);
            double z = playerLocation.getZ() + radius * Math.sin(angle);
            Location spawnLocation = new Location(world, x, playerLocation.getY(), z);
            Creeper creeper = (Creeper) world.spawnEntity(spawnLocation, EntityType.CREEPER);
            creeper.setPowered(charged); // Set creeper as charged if specified
        }

        return true;
    }
}