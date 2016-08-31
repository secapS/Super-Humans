package xyz.whynospaces.superhumans.classes;

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
                    event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
                    int itemAmount = event.getPlayer().getInventory().getItemInMainHand().getAmount();

                    if(itemAmount == 0) {
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
    public void onSprint(PlayerToggleSprintEvent event) {
        if(event.getPlayer().getInventory().getBoots() != null && event.getPlayer().getInventory().getBoots().equals(SuperHumans.INSTANCE.getAPI().getAbility(TheFlash.this, "speedforceboots"))
                && !event.getPlayer().getInventory().contains(SuperHumans.INSTANCE.getAPI().getAbility(this, "speedmodifier"))) {
            if(event.getPlayer().getInventory().getItem()) {
                event.getPlayer().getInventory().addItem(SuperHumans.INSTANCE.getAPI().getAbility(this, "speedmodifier"));
                event.getPlayer().updateInventory();
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        SuperHumans.INSTANCE.getAPI().setSuperHuman(this, event.getPlayer());
    }
}
