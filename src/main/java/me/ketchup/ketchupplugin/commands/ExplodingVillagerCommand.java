package me.ketchup.ketchupplugin.commands;

import me.ketchup.ketchupplugin.ketchupPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.TNTPrimed;

import java.util.ArrayList;
import java.util.List;

public class ExplodingVillagerCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        // Check permission
        if (!player.hasPermission("explodingvillager.use")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        String pluginName = ChatColor.translateAlternateColorCodes('&', "&e[&cketchupPlugin&e]&r: ");

        // Create the exploding villager
        Villager villager = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
        villager.setCustomName(ChatColor.RED + "Exploding Villager");
        villager.setCustomNameVisible(true);
        villager.setProfession(Villager.Profession.NITWIT); // Set villager's profession to Nitwit

        // Set up villager's trades
        List<MerchantRecipe> trades = new ArrayList<>();
        ItemStack tradeItem = new ItemStack(Material.DIAMOND_SWORD);
        MerchantRecipe trade = new MerchantRecipe(tradeItem, 1);
        trade.setExperienceReward(false);
        trade.addIngredient(new ItemStack(Material.EMERALD, 10));
        trades.add(trade);

        villager.setRecipes(trades);

        player.sendMessage(pluginName + ChatColor.GREEN + "Exploding Villager (Nitwit) spawned successfully!");
        return true;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Villager)) return;
        Villager villager = (Villager) event.getRightClicked();
        Player player = event.getPlayer();
        String pluginName = ChatColor.translateAlternateColorCodes('&', "&e[&cketchupPlugin&e]&r: ");
        player.sendMessage(pluginName + ChatColor.YELLOW + "Player right-clicked the villager!");
        if (villager.getCustomName() != null && villager.getCustomName().equals(ChatColor.RED + "Exploding Villager")) {
            player.sendMessage(pluginName + ChatColor.RED + "Right-clicking the villager caused it to explode!");
            // Summon TNT at the villager's location
            TNTPrimed tnt = (TNTPrimed) player.getWorld().spawnEntity(villager.getLocation(), EntityType.PRIMED_TNT);
            tnt.setFuseTicks(0); // Explode immediately
            Bukkit.getScheduler().runTaskLater(ketchupPlugin.getInstance(), () -> {
                // Remove the TNT entity after explosion
                tnt.remove();
            }, 1L);
        } else {
            player.sendMessage(pluginName + ChatColor.YELLOW + "The villager clicked was not an exploding villager!");
        }
    }
}
