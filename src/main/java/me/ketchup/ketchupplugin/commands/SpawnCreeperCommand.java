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

public class SpawnCreeperCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String pluginName = ChatColor.translateAlternateColorCodes('&', "&e[&cketchupPlugin&e]&r: ");

        // Check permission for using the command
        if (!sender.hasPermission("spawncreeper.use")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', pluginName + "&4You do not have permission to use this command."));
            return true;
        }

        // Check if a player is specified as the target
        if (args.length < 1 || args.length > 2) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', pluginName + "Usage: /spawncreeper <player> [charged]"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        // Check if the specified player is online
        if (target == null || !target.isOnline()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', pluginName + "&c" + args[0] + "&r is currently not online."));
            return true;
        }

        // Get the world where the creeper will spawn
        World world = target.getWorld();

        // Get player's location and direction
        Location playerLocation = target.getLocation();
        float playerYaw = playerLocation.getYaw();

        // Calculate spawn location behind the player
        double spawnX = playerLocation.getX() + Math.sin(Math.toRadians(playerYaw)) * 3; // Spawn 3 blocks behind the player
        double spawnZ = playerLocation.getZ() - Math.cos(Math.toRadians(playerYaw)) * 3; // Spawn 3 blocks behind the player
        double spawnY = playerLocation.getY(); // Keep the same height

        Location creeperSpawnLocation = new Location(world, spawnX, spawnY, spawnZ);

        // Determine creeper type
        final boolean charged;
        if (args.length == 2 && args[1].equalsIgnoreCase("charged") || args.length == 2 && args[1].equalsIgnoreCase("true")) {
            charged = true;
        } else {
            charged = false;
        }

        // Spawn creeper behind the player
        Bukkit.getScheduler().runTaskLater(ketchupPlugin.getInstance(), () -> {
            Creeper creeper = (Creeper) world.spawnEntity(creeperSpawnLocation, EntityType.CREEPER);
            creeper.setPowered(charged); // Set creeper as charged if specified
        }, 1L);

        return true;
    }
}
