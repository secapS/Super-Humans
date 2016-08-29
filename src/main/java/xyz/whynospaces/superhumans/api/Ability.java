package xyz.whynospaces.superhumans.api;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Ability {

    public void onClick(PlayerInteractEvent event) {}

    public void onDrop(PlayerDropItemEvent event) {}

    public abstract ItemStack getItemStack();

    public abstract String getName();

}