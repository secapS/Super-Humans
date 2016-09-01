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
import xyz.whynospaces.superhumans.api.SuperHumanTask;
import xyz.whynospaces.superhumans.api.events.PlayerSetSuperHumanEvent;

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
                        Location start = event.getPlayer().getLocation();
                        Vector dir = event.getPlayer().getEyeLocation().getDirection();

                        for(double length = 0; length < 30; length += 0.3) {
                            double x = dir.getX() * length;
                            double y = dir.getY() * length + 1.5;
                            double z = dir.getZ() * length;
                            start.add(x, y, z);
                            start.getWorld().spawnParticle(Particle.REDSTONE, start.getX(), start.getY(), start.getZ(), 0, 10, 255, 0,  0);
                            start.getWorld().getNearbyEntities(start, 0.75, 0.75, 0.75).stream().filter(entity -> entity instanceof LivingEntity).filter(entity -> entity != event.getPlayer()).forEach(entity -> {
                                ((LivingEntity) entity).damage(10);
                                entity.setFireTicks(60);
                            });
                            start.subtract(x, y, z);
                        }
                    }
                }
            }
        });

        this.setPotionEffects(SuperHumans.INSTANCE.getAPI().getPotionEffects(this));
    }

    @EventHandler
    public void onEquip(PlayerSetSuperHumanEvent event) {
        if(event.getSuperHuman().equals(this)) {
            event.getPlayer().setAllowFlight(true);
        }
    }
}
