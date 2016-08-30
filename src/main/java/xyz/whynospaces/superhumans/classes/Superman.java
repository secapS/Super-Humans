package xyz.whynospaces.superhumans.classes;

import com.stirante.MoreProjectiles.projectile.ItemProjectile;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.whynospaces.superhumans.SuperHumans;
import xyz.whynospaces.superhumans.api.Ability;
import xyz.whynospaces.superhumans.api.SuperHuman;

/**
 * Created by Owner on 8/29/2016.
 */
public class Superman extends SuperHuman {

    public Superman() {
        super("superman");

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
                if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if(event.getItem() != null && event.getItem().equals(this.getItemStack())) {
                        Player player = event.getPlayer();
                        Location start = player.getLocation().clone();
                        Vector dir = player.getLocation().getDirection().clone();
                        for(double i = 0; i < 30; i += 0.5) {
                            dir.multiply(i);
                            start.add(dir);
                            start.getWorld().spawnParticle(Particle.REDSTONE, start, 10);
                            start.subtract(dir);
                            dir.normalize();
                        }
                    }
                }
            }
        });


        this.setPotionEffects(SuperHumans.INSTANCE.getAPI().getPotionEffects(this));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        SuperHumans.INSTANCE.getAPI().setSuperHuman(this, event.getPlayer());
    }
}
