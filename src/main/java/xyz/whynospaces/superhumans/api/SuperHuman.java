package xyz.whynospaces.superhumans.api;

import org.bukkit.potion.PotionEffect;

import java.util.Set;

public class SuperHuman {

    private String name;
    private Set<Ability> abilities;
    private Set<PotionEffect> potionEffects;

    public SuperHuman(String name, Set<Ability> abilities, Set<PotionEffect> potionEffects) {
        this.abilities = abilities;
        this.potionEffects = potionEffects;
    }

    public String getName() {
        return this.name;
    }

    public Set<Ability> getAbilities() {
        return this.abilities;
    }

    public Set<PotionEffect> getPotionEffects() {
        return this.potionEffects;
    }
}
