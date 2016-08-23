package xyz.whynospaces.superhumans;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.whynospaces.superhumans.users.User;
import xyz.whynospaces.superhumans.users.UserManager;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SuperHumans extends JavaPlugin {

    public static final Logger LOGGER = Logger.getLogger("SuperHumans");
    public static SuperHumans instance;

    public SuperHumanFactory superHumanFactory;
    public UserManager userManager;

    @Override
    public void onEnable() {
        instance = this;
        createConfig();
        superHumanFactory = new SuperHumanFactory();
        userManager = new UserManager();

        //TESTING STUFF ITEM DESERIALIZER
        this.getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                userManager.setUserHero(new User(event.getPlayer()), superHumanFactory.getSuperHumanByName("captainamerica"));
            }

        }, this);
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
