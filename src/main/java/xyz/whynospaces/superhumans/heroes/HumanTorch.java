package xyz.whynospaces.superhumans.heroes;

import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.projectile.BlockProjectile;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.whynospaces.superhumans.SuperHuman;
import xyz.whynospaces.superhumans.SuperHumans;

public class HumanTorch extends SuperHuman implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getInventory().getItemInMainHand().equals(this.getFireball())) {
                event.setCancelled(true);
                final BlockProjectile projectile = new BlockProjectile("fireball", event.getPlayer(), 51, 0, 1.6F);
                projectile.getEntity().getWorld().playSound(projectile.getEntity().getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1.0F, -3.0F);
                projectile.addRunnable(() -> projectile.getEntity().getWorld().spawnParticle(Particle.FLAME, projectile.getEntity().getLocation().clone(), 1, 0, 0, 0, 0));
            }

            if(event.getPlayer().getInventory().getItemInMainHand().equals(this.getSuperNova())) {
                event.setCancelled(true);
                event.getPlayer().spawnParticle(Particle.FLAME, event.getPlayer().getLocation(), 50);
                event.getPlayer().getWorld().playSound( event.getPlayer().getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1.0F, -3.0F);
                event.getPlayer().getNearbyEntities(3.0, 3.0, 3.0).stream().filter(entity -> (entity instanceof LivingEntity)).forEach(entity -> entity.setFireTicks(20 * 60));
            }
        }
    }

    @EventHandler
    public void onHit(CustomProjectileHitEvent event)
    {
        Player player = (Player) event.getProjectile().getShooter();
        if(event.getProjectile().getProjectileName().equalsIgnoreCase("fireball"))
        {
            if(event.getHitType().equals(CustomProjectileHitEvent.HitType.ENTITY))
            {
                event.getHitEntity().setFireTicks(20 * 60);
            }
        }
    }

    public ItemStack getFireball() {
        return SuperHumans.instance.superHumanFactory.getItemByName("humantorch", "fireball");
    }

    public ItemStack getSuperNova() {
        return SuperHumans.instance.superHumanFactory.getItemByName("humantorch", "supernova");
    }
}
