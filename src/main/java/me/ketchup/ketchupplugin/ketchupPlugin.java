package me.ketchup.ketchupplugin;

import me.ketchup.ketchupplugin.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class ketchupPlugin extends JavaPlugin {

    static ketchupPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        System.out.println("Starting ketchupPlugin");
        System.out.println("I enjoy ketchup more than the average person.\n" +
                "Made with tomatoes and vinegar, this ubiquitous red sauce is my condiment of choice\n" +
                "for a wide variety of dining situations. Sweet and tangy, it's perfect for eating with french fries\n" +
                "and burgers, of course, but you might not realize how good it is incorporated into other dishes.\n" +
                "Its unique sweet and savory nature makes it a bit of a dynamo that way.\n" +
                "No wonder this addictive table staple of households and restaurants is so beloved.");
        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        //Add the command
        getCommand("freestaff").setExecutor(new FreeStaffCommand());
        getCommand("bodyslam").setExecutor(new BodyslamCommand());
        getCommand("bodyslam").setPermission("bodyslam.use");
        getCommand("spawncreeper").setExecutor(new SpawnCreeperCommand());
        getCommand("spawncreeper").setPermission("spawncreeper.use");
        getCommand("creeperencirclement").setExecutor(new CreeperEncirclementCommand());
        getCommand("creeperencirclement").setPermission("creeperencirclement.use");
        getCommand("garfield").setExecutor(new GarfieldCommand());
        getCommand("garfield").setPermission("garfield.use");
        getCommand("explodingvillager").setExecutor(new ExplodingVillagerCommand());
        getCommand("explodingvillager").setPermission("explodingvillager.use");

    }

    @Override
    public void onDisable() {
        System.out.println("--------------------------------");
        System.out.println("Stopped ketchupPlugin");
        System.out.println("--------------------------------");
    }
    public static ketchupPlugin getInstance() {
        return instance;
    }
}