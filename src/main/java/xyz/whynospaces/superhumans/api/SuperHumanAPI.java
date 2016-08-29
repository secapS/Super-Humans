package xyz.whynospaces.superhumans.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public interface SuperHumanAPI {

    void registerSuperHuman(SuperHuman superHuman);

    SuperHuman getSuperHuman(String superHuman);

    ItemStack[] getAbilities(SuperHuman superHuman);

    ItemStack getAbility(SuperHuman superHuman, String name);

    boolean setSuperHuman(SuperHuman superHuman, Player player);

    PotionEffect[] getPotionEffects(SuperHuman superHuman);
}
