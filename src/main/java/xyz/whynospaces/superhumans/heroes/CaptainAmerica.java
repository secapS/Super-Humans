package xyz.whynospaces.superhumans.heroes;

import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.whynospaces.superhumans.SuperHuman;
import xyz.whynospaces.superhumans.SuperHumans;

public class CaptainAmerica extends SuperHuman implements Listener {

    @EventHandler
    public void onInteract(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(event.getItemDrop().getItemStack().equals(this.getShield()))
        {
            event.getItemDrop().remove();
            ItemProjectile vibranium = new ItemProjectile("vibraniumshield", player, this.getShield().clone(), 0.6F);
        }
    }

    @EventHandler
    public void onHit(final CustomProjectileHitEvent event)
    {
        if(event.getProjectile().getProjectileName().equalsIgnoreCase("vibraniumshield"))
        {
            final Player player = (Player)event.getProjectile().getShooter();
            if(event.getHitType().equals(CustomProjectileHitEvent.HitType.ENTITY))
            {
                if(event.getProjectile().getShooter() != event.getHitEntity() && event.getHitEntity() != null)
                {
                    Vector to = new Vector(event.getProjectile().getShooter().getLocation().getX(), event.getProjectile().getShooter().getLocation().getY(), event.getProjectile().getShooter().getLocation().getZ());
                    Vector from = new Vector(event.getHitEntity().getLocation().getX(), event.getHitEntity().getLocation().getY(), event.getHitEntity().getLocation().getZ());
                    event.setCancelled(true);
                    event.getProjectile().getEntity().setVelocity(to.subtract(from).multiply(to.distance(from) > 1.0 ? to.distance(from) * 0.02 : 0.02).add(new Vector(0, 0.295, 0)).normalize());
                    event.getHitEntity().getWorld().playSound(event.getHitEntity().getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 0.5F);
                }
                else
                {
                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 0.5F);
                    ItemStack item = this.getShield();
                    if(player.getInventory().firstEmpty() == -1) {
                        player.getWorld().dropItemNaturally(player.getLocation(), item);
                    } else {
                        player.getInventory().addItem(item);
                    }
                }
            }

            if(event.getHitType().equals(CustomProjectileHitEvent.HitType.BLOCK))
            {

                Vector to = new Vector(event.getProjectile().getShooter().getLocation().getX(), event.getProjectile().getShooter().getLocation().getY(), event.getProjectile().getShooter().getLocation().getZ());
                Vector from = new Vector(event.getHitBlock().getLocation().getX(), event.getHitBlock().getLocation().getY(), event.getHitBlock().getLocation().getZ());
                event.setCancelled(true);
                event.getHitBlock().getWorld().playSound(event.getHitBlock().getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 0.5F);
                event.getProjectile().getEntity().setVelocity(to.subtract(from).multiply(to.distance(from) > 1.0 ? to.distance(from) * 0.02 : 0.02).add(new Vector(0, 0.295, 0)).normalize());
            }
        }
    }

    public ItemStack getShield() {
        for(ItemStack items : SuperHumans.instance.superHumanFactory.getSuperHumanByName("captainamerica").getItems()) {
            if(items.hasItemMeta() && items.getType() == Material.SHIELD) {
                return items;
            }
        }
        return null;
    }
}
