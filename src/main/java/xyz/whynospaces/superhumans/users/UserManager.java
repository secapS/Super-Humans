package xyz.whynospaces.superhumans.users;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import xyz.whynospaces.superhumans.SuperHuman;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private Map<User, SuperHuman> currentHeroes = new HashMap<User, SuperHuman>();

    public void setUserHero(User user, SuperHuman hero) {

        user.setHero(hero);
        this.currentHeroes.put(user, hero);
        Player player = user.getPlayer();
        player.getInventory().clear();

        for(ItemStack itemStack : hero.getItems()) {
            player.getInventory().addItem(itemStack);
        }

        for(PotionEffect potionEffects : hero.getPotionEffects()) {
            player.addPotionEffect(potionEffects);
        }

//        if(users.getHero() != hero) {
//            System.out.println("test8");
//            if(user.getHero() == null) {
//                user.setHero(hero);
//                this.currentHeroes.put(user, hero);
//                Player player = user.getPlayer();
//                player.getInventory().clear();
//                System.out.println("test5");
//                for(ItemStack itemStack : hero.getItems()) {
//                    player.getInventory().addItem(itemStack);
//                    System.out.println("test3");
//                }
//
//                for(PotionEffect potionEffects : hero.getPotionEffects()) {
//                    player.addPotionEffect(potionEffects);
//                }
//            }
//        } else {
//            user.getPlayer().sendMessage(ChatColor.RED + "Sorry! Someone else is currently using that hero!");
//        }

        if(hero == null) {
            this.currentHeroes.remove(user.getPlayer().getName());
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
