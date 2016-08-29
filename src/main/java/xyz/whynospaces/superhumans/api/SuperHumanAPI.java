package xyz.whynospaces.superhumans.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface SuperHumanAPI {

    void registerSuperHuman(SuperHuman superHuman);

    SuperHuman getSuperHuman(String superHuman);

    ItemStack[] getAbilities(SuperHuman superHuman);

    ItemStack getAbility(SuperHuman superHuman, String name);

    boolean setSuperHuman(SuperHuman superHuman, Player player);

}
