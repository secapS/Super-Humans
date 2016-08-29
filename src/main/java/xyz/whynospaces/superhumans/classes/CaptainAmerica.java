package xyz.whynospaces.superhumans.classes;

import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import xyz.whynospaces.superhumans.SuperHumans;
import xyz.whynospaces.superhumans.api.Ability;
import xyz.whynospaces.superhumans.api.SuperHuman;

public class CaptainAmerica extends SuperHuman {

    Ability[] abilities = new Ability[] {
            new Ability() {

                @Override
                @EventHandler
                public void onDrop(PlayerDropItemEvent event) {
                    Player player = event.getPlayer();
                    if(event.getItemDrop().getItemStack().equals(this.getItemStack()))
                    {
                        event.getItemDrop().remove();
                        ItemProjectile vibranium = new ItemProjectile("vibraniumshield", player, this.getItemStack().clone(), 0.6F);
                    }
                }

                public ItemStack getItemStack() {
                    return SuperHumans.getAPI().getAbility(CaptainAmerica.this, "shield");
                }

                public String getName() {
                    return "shield";
                }
            }
    };

    public CaptainAmerica() {
        super("captainamerica");
        this.setAbilities(abilities);
        this.setPotionEffects(SuperHumans.getAPI().getPotionEffects(this));
    }

    @EventHandler
    public void onProjectileHitEvent(CustomProjectileHitEvent event) {

    }
}
