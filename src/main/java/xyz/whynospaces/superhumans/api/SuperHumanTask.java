package xyz.whynospaces.superhumans.api;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class SuperHumanTask extends BukkitRunnable {

    private Player player;
    private SuperHuman superHuman;

    public SuperHumanTask(Player player, SuperHuman superHuman) {
        this.player = player;
        this.superHuman = superHuman;
    }

    public Player getPlayer() {
        return this.player;
    }

    public SuperHuman getSuperHuman() {
        return this.superHuman;
    }

}
