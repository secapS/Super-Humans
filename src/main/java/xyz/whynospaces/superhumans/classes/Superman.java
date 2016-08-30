package xyz.whynospaces.superhumans.classes;

import com.stirante.MoreProjectiles.projectile.ItemProjectile;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.whynospaces.superhumans.SuperHumans;
import xyz.whynospaces.superhumans.api.Ability;
import xyz.whynospaces.superhumans.api.SuperHuman;

/**
 * Created by Owner on 8/29/2016.
 */
public class Superman extends SuperHuman {

    public Superman(String name) {
        super("Superman");

        this.setAbility(new Ability() {

            @Override
            public ItemStack getItemStack() {
                return SuperHumans.INSTANCE.getAPI().getAbility(Superman.this, "laservision");
            }

            @Override
            public String getName() {
                return "laservision";
            }

            @Override
            public void onClick(PlayerInteractEvent event){
                
            }

        });


        this.setPotionEffects(SuperHumans.INSTANCE.getAPI().getPotionEffects(this));
    }
}
