package xyz.whynospaces.superhumans;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.whynospaces.superhumans.api.SuperHumanAPI;

public class SuperHumans extends JavaPlugin {

    private static SuperHumanAPI api;

    @Override
    public void onEnable() {

    }

    public void createConfig() {

    }

    public static SuperHumanAPI getAPI() {
        if(api == null) {
            return new SuperHumanManager();
        }

        return null;
    }

}
