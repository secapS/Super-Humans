package xyz.whynospaces.superhumans.api;

import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;

import java.util.HashSet;
import java.util.Set;

public class SuperHuman implements Listener {

    private String name;
    private Set<Ability> abilities;
    private Set<PotionEffect> potionEffects;

    public SuperHuman(String name) {
        this.name = name;
        abilities = new HashSet<>();
    }

    public String getName() {
        return this.name;
    }

    public Set<Ability> getAbilities() {
        return this.abilities;
    }

    public void setAbility(Ability ability) {
        this.abilities.add(ability);
    }

    public Set<PotionEffect> getPotionEffects() {
        return this.potionEffects;
    }

    public void setPotionEffects(Set<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }
}
