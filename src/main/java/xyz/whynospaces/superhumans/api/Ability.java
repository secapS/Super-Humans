package xyz.whynospaces.superhumans.api;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Ability implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {}

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {}

    public abstract ItemStack getItemStack();

    public abstract String getName();

}