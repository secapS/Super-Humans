package xyz.whynospaces.superhumans;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.whynospaces.superhumans.api.SuperHuman;
import xyz.whynospaces.superhumans.api.SuperHumanAPI;

public class SuperHumanManager implements SuperHumanAPI {

    @Override
    public void registerSuperHuman(SuperHuman superHuman) {

    }

    @Override
    public SuperHuman getSuperHuman(String superHuman) {
        return null;
    }

    @Override
    public ItemStack[] getAbilities(SuperHuman superHuman) {
        return new ItemStack[0];
    }

    @Override
    public ItemStack getAbility(SuperHuman superHuman, String name) {
        return null;
    }

    @Override
    public boolean setSuperHuman(SuperHuman superHuman, Player player) {
        return false;
    }

}
