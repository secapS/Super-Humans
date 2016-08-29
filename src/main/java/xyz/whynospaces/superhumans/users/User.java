package xyz.whynospaces.superhumans.users;

import org.bukkit.entity.Player;

public class User {

    private Player player;

    public User(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
