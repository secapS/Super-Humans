package xyz.whynospaces.superhumans.api;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface Ability {
    void onClick(PlayerInteractEvent event);

    void onDrop(PlayerDropItemEvent event);

    ItemStack getItemStack();

    String getName();
}
