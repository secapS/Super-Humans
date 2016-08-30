package xyz.whynospaces.superhumans.classes;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.whynospaces.superhumans.SuperHumans;
import xyz.whynospaces.superhumans.api.Ability;
import xyz.whynospaces.superhumans.api.SuperHuman;

public class TheFlash extends SuperHuman {

    public TheFlash() {
        super("theflash");

        this.setAbility(new Ability() {

            @Override
            public void onClick(PlayerInteractEvent event) {

            }

            @Override
            public ItemStack getItemStack() {
                return SuperHumans.INSTANCE.getAPI().getAbility(TheFlash.this, "speedmodifier");
            }

            @Override
            public String getName() {
                return "speedmodifier";
            }
        });

        this.setPotionEffects(SuperHumans.INSTANCE.getAPI().getPotionEffects(this));
    }
}
