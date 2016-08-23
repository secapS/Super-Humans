package xyz.whynospaces.superhumans.users;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.whynospaces.superhumans.SuperHuman;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private Map<String, String> currentHeroes;

    public UserManager() {
        this.currentHeroes = new HashMap<String, String>();
    }

    public void setUserHero(User user, SuperHuman hero) {
        if(!currentHeroes.values().contains(hero.getSimpleName()) && user.getHero() != hero) {
            this.currentHeroes.put(hero.getSimpleName(), user.getPlayer().getName());

            Player player = user.getPlayer();

            player.getInventory().clear();
            player.getInventory().addItem(((ItemStack[])hero.getItems().toArray()));
        } else {
            user.getPlayer().sendMessage(ChatColor.RED + "Sorry! Someone else is currently using that hero!");
        }

        if(hero == null) {
            this.currentHeroes.remove(user.getPlayer().getName());
        }
    }

    public Map<String, String> getCurrentHeroes() {
        return this.currentHeroes;
    }
}
