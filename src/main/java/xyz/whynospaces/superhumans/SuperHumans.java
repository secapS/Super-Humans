package xyz.whynospaces.superhumans;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SuperHumans extends JavaPlugin {

    public static final Logger LOGGER = Logger.getLogger("SuperHumans");
    public static SuperHumans instance;

    @Override
    public void onEnable() {
        instance = this;
        createConfig();
    }

    public void createConfig() {
        if(!this.getDataFolder().exists()) {
            LOGGER.log(Level.CONFIG, "creating plugin folder..");
            this.getDataFolder().mkdirs();
        }

        File configFile = new File(this.getDataFolder(), "config.yml");
        if(!configFile.exists()) {
            LOGGER.log(Level.CONFIG, "creating default config..");
            this.saveDefaultConfig();
        }
    }
}
