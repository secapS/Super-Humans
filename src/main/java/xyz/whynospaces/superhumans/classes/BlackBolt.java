package xyz.whynospaces.superhumans.classes;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.whynospaces.superhumans.SuperHumans;
import xyz.whynospaces.superhumans.api.Ability;
import xyz.whynospaces.superhumans.api.SuperHuman;
import xyz.whynospaces.superhumans.api.SuperHumanTask;

public class BlackBolt extends SuperHuman {

    public BlackBolt() {
        super("blackbolt");

        this.setAbility(new Ability() {

            @Override
            public void onClick(PlayerInteractEvent event) {
                if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if(SuperHumans.INSTANCE.getAPI().isAbility(this, event.getItem())) {
                        new SuperHumanTask(event.getPlayer(), BlackBolt.this) {
                            Location start = event.getPlayer().getLocation();
                            Vector dir = event.getPlayer().getEyeLocation().getDirection();
                            double length = 0;
                            @Override
                            public void run() {
                                length++;
                                double x = dir.getX() * length;
                                double y = dir.getY() * length + 1.5;
                                double z = dir.getZ() * length;
                                start.add(x, y, z);
                                this.getPlayer().getWorld().createExplosion(start, (float)length/5);
                                start.subtract(x, y, z);

                                if(length > 30) {
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(SuperHumans.INSTANCE, 0, 1);
                    }
                }
            }

            @Override
            public ItemStack getItemStack() {
                return SuperHumans.INSTANCE.getAPI().getAbility(BlackBolt.this, "sonicscream");
            }

            @Override
            public String getName() {
                return "sonicscream";
            }
        });

        this.setPotionEffects(SuperHumans.INSTANCE.getAPI().getPotionEffects(this));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        SuperHumans.INSTANCE.getAPI().setSuperHuman(this, event.getPlayer());
    }
}
