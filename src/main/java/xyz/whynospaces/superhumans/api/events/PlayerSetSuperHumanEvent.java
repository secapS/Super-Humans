package xyz.whynospaces.superhumans.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.whynospaces.superhumans.api.SuperHuman;

public class PlayerSetSuperHumanEvent extends Event implements Cancellable {

    private static HandlerList handlerList = new HandlerList();

    private boolean cancelled;

    private Player player;
    private SuperHuman superHuman;

    public PlayerSetSuperHumanEvent(Player player, SuperHuman superHuman) {
        this.player = player;
        this.superHuman = superHuman;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
