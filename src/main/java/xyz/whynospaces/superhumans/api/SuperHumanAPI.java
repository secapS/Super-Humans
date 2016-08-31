package xyz.whynospaces.superhumans.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Set;

public interface SuperHumanAPI {

    void registerSuperHuman(SuperHuman superHuman);

    SuperHuman getSuperHuman(String superHuman);

    Set<ItemStack> getAbilities(SuperHuman superHuman);

    ItemStack getAbility(SuperHuman superHuman, String name);

    boolean setSuperHuman(SuperHuman superHuman, Player player);

    boolean isSuperHuman(SuperHuman superHuman, Player player);

    Set<PotionEffect> getPotionEffects(SuperHuman superHuman);

    Set<SuperHuman> getSuperHumans();

    boolean isAbility(Ability ability, ItemStack itemStack);
}