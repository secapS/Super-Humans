package xyz.whynospaces.superhumans.classes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import xyz.whynospaces.superhumans.SuperHumans;
import xyz.whynospaces.superhumans.api.Ability;
import xyz.whynospaces.superhumans.api.SuperHuman;
import xyz.whynospaces.superhumans.api.SuperHumanTask;
import xyz.whynospaces.superhumans.api.events.PlayerSetSuperHumanEvent;
import xyz.whynospaces.superhumans.utils.Cooldowns;

public class TheFlash extends SuperHuman {

    public TheFlash() {
        super("theflash");

        this.setAbility(new Ability() {

            @Override
            public void onClick(PlayerInteractEvent event) {
                if(SuperHumans.INSTANCE.getAPI().isAbility(this, event.getItem())) {
                    if(event.getPlayer().getInventory().getItemInMainHand().getAmount() <= 8 && Cooldowns.tryCooldown(event.getPlayer(), "speedmodifier", 500L)) {
                        int itemAmount = event.getPlayer().getInventory().getItemInMainHand().getAmount();
                        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
                        event.getPlayer().getInventory().getItemInMainHand().setAmount(itemAmount + 1);

                        if(itemAmount == 1) {
                            event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 1));
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

            @Override
            public void onDrop(PlayerDropItemEvent event) {
                if(event.getItemDrop().getItemStack().equals(this.getItemStack())) {
                    event.getItemDrop().remove();
                    int itemAmount = event.getPlayer().getInventory().getItemInMainHand().getAmount();

                    if(itemAmount == 0) {
                        event.setCancelled(true);
                        event.getPlayer().setFlying(false);
                        event.getPlayer().setAllowFlight(false);
                    }
                    else if(itemAmount == 1) {
                        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
                        event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 0));
                    }
                    else if(itemAmount == 2) {
                        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
                        event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 1));
                    }
                    else if(itemAmount == 3) {
                        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
                        event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 2));
                    }
                    else if(itemAmount == 4) {
                        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
                        event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 3));
                     }
                    else if(itemAmount == 5) {
                        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
                        event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 4));
                    }
                    else if(itemAmount == 6) {
                        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
                        event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 7));
                    }
                    else if(itemAmount == 7) {
                        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
                        event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 9));
                    }
                    else if(itemAmount == 8) {
                        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
                        event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 49));
                    }
                    else if(itemAmount == 9) {
                        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
                        event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 99));
                    }
                }
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

    @EventHandler
    public void onEquip(PlayerSetSuperHumanEvent event) {
        if(event.getSuperHuman().equals(this)) {
            new SuperHumanTask(event.getPlayer(), event.getSuperHuman()) {

                @Override
                public void run() {
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
            }.runTaskTimer(SuperHumans.INSTANCE, 0L, 0L);
        }
    }
}
