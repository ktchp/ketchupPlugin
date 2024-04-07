package me.ketchup.ketchupplugin.commands;

import me.ketchup.ketchupplugin.ketchupPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public class BodyslamCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String pluginName = ChatColor.translateAlternateColorCodes('&', "&e[&cketchupPlugin&e]&r: ");

        if (!sender.hasPermission("bodyslam.use")) {
            sender.sendMessage(pluginName + ChatColor.RED + "you dont have permission to use this command noob");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(pluginName + ChatColor.RED + "how to use: /bodyslam <player> <heightValue>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            sender.sendMessage(pluginName + ChatColor.RED + args[0] + " is currently not online");
            return true;
        }

        double heightValue;
        try {
            heightValue = Double.parseDouble(args[1]);
            if (heightValue <= 0) {
                sender.sendMessage(pluginName + ChatColor.RED + "height cant be 0 or below");
                return true;
            }

            if (heightValue > 4) {
                sender.sendMessage(pluginName + ChatColor.RED + "height value only goes up to 4 : ( (for now)");
                return true;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(pluginName + ChatColor.RED + "maybe enter something valid next time ?");
            return true;
        }

        Bukkit.getScheduler().runTaskLater(ketchupPlugin.getInstance(), () -> {
            target.sendMessage(pluginName + ChatColor.YELLOW + "you got bodyslammed by " + ChatColor.RED + sender.getName() + "!");
            sender.sendMessage(pluginName + ChatColor.YELLOW + "you bodyslammed " + ChatColor.RED + target.getName() + "!");
            Vector velocity = new Vector(0, heightValue, 0);
            target.setVelocity(velocity);
        }, 1L);

        return true;
    }
}
