package xyz.whynospaces.superhumans.heroes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import xyz.whynospaces.superhumans.SuperHuman;
import xyz.whynospaces.superhumans.SuperHumans;

import java.util.ArrayList;
import java.util.List;

public class Flash extends SuperHuman implements Listener {

    public List<String> sprinting = new ArrayList<String>();

    @EventHandler
    public void onSprint(PlayerToggleSprintEvent event) {
        if(event.getPlayer().getInventory().getBoots().equals(this.getSpeedForceBoots()) && !sprinting.contains(event.getPlayer().getName())) {
            sprinting.add(event.getPlayer().getName());
            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 1));
            event.getPlayer().getInventory().addItem(this.getSpeedModifier());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(sprinting.add(event.getPlayer().getName())) {
                if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.FIREWORK_CHARGE &&
                        event.getPlayer().getInventory().getItemInMainHand().hasItemMeta() &&
                        event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName() &&
                        event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("speed modifier")) {
                    if(event.getPlayer().getInventory().getItemInMainHand().getAmount() <= 9) {
                        int itemAmount = event.getPlayer().getInventory().getItemInMainHand().getAmount();
                        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);

                        if(itemAmount <= 3) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, itemAmount ));
                        }
                        else if(itemAmount == 4) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 5));
                        }
                        else if(itemAmount == 5) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 8));
                        }
                        else if(itemAmount == 6) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 10));
                        }
                        else if(itemAmount == 7) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 50));
                        }
                        else if(itemAmount == 8) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 100));
                        }
                        else if(itemAmount == 9) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 255));
                        }
                        event.getPlayer().getInventory().getItemInMainHand().setAmount(itemAmount + 1);
                        event.getPlayer().updateInventory();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.FIREWORK_CHARGE &&
                event.getPlayer().getInventory().getItemInMainHand().hasItemMeta() &&
                event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName() &&
                event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("speed modifier")) {
            event.getItemDrop().remove();
            event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
            int itemAmount = event.getPlayer().getInventory().getItemInMainHand().getAmount();

            if(itemAmount == 1) {
                sprinting.remove(event.getPlayer().getName());
                event.getPlayer().getInventory().setItemInMainHand(null);
            }
            if(itemAmount == 2) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 1));
            }
            else if(itemAmount == 3) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 2));
            }
            else if(itemAmount == 4) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 3));
            }
            else if(itemAmount == 5) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 5));
            }
            else if(itemAmount == 6) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 8));
            }
            else if(itemAmount == 7) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 10));
            }
            else if(itemAmount == 8) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 50));
            }
            else if(itemAmount == 9) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 100));
            }
        }
    }

    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player player = (Player)event.getDamager();
            if(sprinting.contains(player.getName())) {
                if(player.hasPotionEffect(PotionEffectType.SPEED)) {
                    if(player.getPotionEffect(PotionEffectType.SPEED).getAmplifier() >= 10) {
                        event.setDamage(event.getDamage() * player.getPotionEffect(PotionEffectType.SPEED).getAmplifier());
                        Location loc = event.getEntity().getLocation();
                        player.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 1F, false, false);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

    }

    public ItemStack getSpeedForceBoots() {
        return SuperHumans.instance.superHumanFactory.getItemByName("flashbarryallen", "speedforceboots");
    }

    public ItemStack getSpeedModifier() {
        ItemStack item = new ItemStack(Material.FIREWORK_CHARGE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Speed Modifier");
        item.setItemMeta(meta);
        return item;
    }
}
