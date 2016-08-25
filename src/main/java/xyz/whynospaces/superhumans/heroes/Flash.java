package xyz.whynospaces.superhumans.heroes;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import xyz.whynospaces.superhumans.SuperHuman;
import xyz.whynospaces.superhumans.SuperHumans;
import xyz.whynospaces.superhumans.utils.Cooldowns;

import java.util.*;

public class Flash extends SuperHuman implements Listener {

    public List<String> sprinting = new ArrayList<String>();

    @EventHandler
    public void onSprint(PlayerToggleSprintEvent event) {
        if(event.getPlayer().getInventory().getBoots() != null && event.getPlayer().getInventory().getBoots().equals(this.getSpeedForceBoots()) && !sprinting.contains(event.getPlayer().getName())) {
            sprinting.add(event.getPlayer().getName());
            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 0));
            event.getPlayer().getInventory().addItem(this.getSpeedModifier());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(sprinting.contains(event.getPlayer().getName())) {
                if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.FIREWORK_CHARGE &&
                        event.getPlayer().getInventory().getItemInMainHand().hasItemMeta() &&
                        event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName() &&
                        event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("speed modifier")) {
                    if(event.getPlayer().getInventory().getItemInMainHand().getAmount() <= 8
                            && Cooldowns.tryCooldown(event.getPlayer(), "speedmodifier", 500L)) {
                        int itemAmount = event.getPlayer().getInventory().getItemInMainHand().getAmount();
                        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
                        event.getPlayer().getInventory().getItemInMainHand().setAmount(itemAmount + 1);
                        if(itemAmount == 1) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE,  1));
                        }
                        else if(itemAmount == 2) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 2));
                        }
                        else if(itemAmount == 3) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 4));
                        }
                        else if(itemAmount == 4) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 7));
                        }
                        else if(itemAmount == 5) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 9));
                        }
                        else if(itemAmount == 6) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 49));
                        }
                        else if(itemAmount == 7) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 99));
                        }
                        else if(itemAmount == 8) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 254));
                        }
                        event.getPlayer().updateInventory();
                    }
                }
            }
        }

        if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(sprinting.contains(event.getPlayer().getName())) {
                try {
                    BlockIterator blockIterator = new BlockIterator(event.getPlayer(), 2);
                    Block wall = null;
                    Block phaseTo = null;
                    while(blockIterator.hasNext()) {
                        if(wall == null) {
                            if(!blockIterator.next().isEmpty()) {
                                wall = blockIterator.next();
                            }
                        }

                        phaseTo = blockIterator.next();
                    }
                    if(phaseTo != null) {
                        Location playerLocation = event.getPlayer().getLocation();
                        playerLocation.setX(phaseTo.getX());
                        playerLocation.setY(phaseTo.getY());
                        playerLocation.setZ(phaseTo.getZ());
                        event.getPlayer().teleport(playerLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
                    }
                }catch(NoSuchElementException e) {}
                catch(IllegalStateException e){}
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(event.getItemDrop().getItemStack().getType() == Material.FIREWORK_CHARGE &&
                event.getItemDrop().getItemStack().hasItemMeta() &&
                event.getItemDrop().getItemStack().getItemMeta().hasDisplayName() &&
                event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase("speed modifier")) {

            event.getItemDrop().remove();
            event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
            int itemAmount = event.getPlayer().getInventory().getItemInMainHand().getAmount();

            if(itemAmount == 0) {
                sprinting.remove(event.getPlayer().getName());
                event.getPlayer().setFlying(false);
                event.getPlayer().setAllowFlight(false);
            }
            else if(itemAmount == 1) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 0));
            }
            else if(itemAmount == 2) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 1));
            }
            else if(itemAmount == 3) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 2));
            }
            else if(itemAmount == 4) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 3));
            }
            else if(itemAmount == 5) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 4));
            }
            else if(itemAmount == 6) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 7));
            }
            else if(itemAmount == 7) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 9));
            }
            else if(itemAmount == 8) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 49));
            }
            else if(itemAmount == 9) {
                event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 99));
            }
        }
    }

    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player player = (Player)event.getDamager();
            if(sprinting.contains(player.getName())) {
                if(player.hasPotionEffect(PotionEffectType.SPEED)) {
                    if(player.getPotionEffect(PotionEffectType.SPEED).getAmplifier() >= 9) {
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
        if(event.getTo().getX() != event.getFrom().getX() || event.getTo().getY() != event.getFrom().getY() || event.getTo().getZ() != event.getFrom().getZ()) {
            if(sprinting.contains(event.getPlayer().getName())) {
                Player player = event.getPlayer();
                Location location = event.getPlayer().getLocation();
                Block feet = location.getBlock();
                Block underFeet = feet.getRelative(BlockFace.DOWN);

                if (feet.getType() == Material.STATIONARY_WATER) {
                    location.setY(Math.floor(location.getY() + 1) + .1);
                    player.teleport(location);
                } else if (player.isFlying() && underFeet.getType() == Material.AIR) {
                    location.setY(Math.floor(location.getY() - 1) + .1);
                    player.teleport(location);
                }
                feet = player.getLocation().getBlock();
                underFeet = feet.getRelative(BlockFace.DOWN);
                if (feet.getType() == Material.AIR && underFeet.getType() == Material.STATIONARY_WATER) {
                    if (!player.isFlying()) {
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        player.setFlySpeed(0.1F);
                    }
                } else if (player.isFlying()) {
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    player.setFlySpeed(0.1F);
                }
            }
        }
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
