package xyz.whynospaces.superhumans.users;

import org.bukkit.entity.Player;
import xyz.whynospaces.superhumans.SuperHuman;

public class User {

    private Player player;
    private SuperHuman hero;

    public User(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public SuperHuman getHero() {
        return this.hero;
    }

    public void setHero(SuperHuman hero) {
        this.hero = hero;
    }
}
