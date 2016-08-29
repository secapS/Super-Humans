package xyz.whynospaces.superhumans.api;

import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;

public class SuperHuman implements Listener {

    private String name;
    private Ability[] abilities;
    private PotionEffect[] potionEffects;

    public SuperHuman(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Ability[] getAbilities() {
        return this.abilities;
    }

    public void setAbilities(Ability[] abilities) {
        this.abilities = abilities;
    }

    public PotionEffect[] getPotionEffects() {
        return this.potionEffects;
    }

    public void setPotionEffects(PotionEffect[] potionEffects) {
        this.potionEffects = potionEffects;
    }
}
