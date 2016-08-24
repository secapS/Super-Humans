package xyz.whynospaces.superhumans.users;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import xyz.whynospaces.superhumans.SuperHuman;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private Map<User, SuperHuman> currentHeroes = new HashMap<User, SuperHuman>();

    public void setUserHero(User user, SuperHuman hero) {
        if(hero != null) {
            user.setHero(hero);
            this.currentHeroes.put(user, hero);
            Player player = user.getPlayer();
            player.getInventory().clear();
            player.getActivePotionEffects().clear();

            for(ItemStack itemStack : hero.getItems()) {
                player.getInventory().addItem(itemStack);
            }

            for(PotionEffect potionEffects : hero.getPotionEffects()) {
                player.addPotionEffect(potionEffects);
            }
        } else {
            this.currentHeroes.remove(user.getPlayer().getName());
            user.getPlayer().getInventory().clear();
            user.getPlayer().getActivePotionEffects().clear();
        }
    }

    public User getUserByPlayer(Player player) {
        return getUserByPlayer(player.getName());
    }

    public User getUserByPlayer(String player) {
        for(User user : currentHeroes.keySet()) {
            if(user.getPlayer().getName().equalsIgnoreCase(player)) {
                return user;
            }
        }
        return null;
    }

    public Map<User, SuperHuman> getCurrentHeroes() {
        return this.currentHeroes;
    }
}
