package xyz.whynospaces.superhumans;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.whynospaces.superhumans.api.SuperHumanAPI;
import xyz.whynospaces.superhumans.classes.CaptainAmerica;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SuperHumans extends JavaPlugin {

    private SuperHumanAPI api;
    public static SuperHumans INSTANCE;

    public Logger LOGGER = this.getLogger();

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.createConfig();
        this.getAPI().registerSuperHuman(new CaptainAmerica());
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

    public SuperHumanAPI getAPI() {
        if(api == null) return new SuperHumanManager();
        return null;
    }

}
