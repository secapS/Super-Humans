package xyz.whynospaces.superhumans;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class SuperHuman {

    private String displayName;
    private List<PotionEffect> potionEffects;
    private List<ItemStack> items;

    public SuperHuman(String displayName, List<PotionEffect> potionEffects, List<ItemStack> items) {
        this.displayName = displayName;
        this.potionEffects = potionEffects;
        this.items = items;
    }

    public SuperHuman() {}

    public List<PotionEffect> getPotionEffects() {
        return this.potionEffects;
    }

    public List<ItemStack> getItems() {
        return this.items;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
