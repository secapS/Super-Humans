package xyz.whynospaces.superhumans;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.whynospaces.superhumans.api.Ability;
import xyz.whynospaces.superhumans.api.SuperHuman;
import xyz.whynospaces.superhumans.api.SuperHumanAPI;
import xyz.whynospaces.superhumans.utils.ItemBuilder;

import java.util.*;

public class SuperHumanManager implements SuperHumanAPI {

    public Set<SuperHuman> superHumans = new HashSet<>();

    @Override
    public void registerSuperHuman(SuperHuman superHuman) {
        this.superHumans.add(superHuman);
        SuperHumans.INSTANCE.getServer().getPluginManager().registerEvents(superHuman, SuperHumans.INSTANCE);
    }

    @Override
    public SuperHuman getSuperHuman(String superHumanName) {
        for(SuperHuman superHuman : superHumans) {
            if(superHuman.getName().equalsIgnoreCase(superHumanName)) {
                return superHuman;
            }
        }
        return null;
    }

    @Override
    public ItemStack[] getAbilities(SuperHuman superHuman) {
        List<ItemStack> abilities = new ArrayList<>();
        for(String items : SuperHumans.INSTANCE.getConfig().getConfigurationSection("superhumans." + superHuman.getName() + ".items").getKeys(false)) {
            String configPath = "superhumans." + superHuman.getName() + ".items." + items + ".";
            ItemBuilder itemBuilder = new ItemBuilder(Material.getMaterial(SuperHumans.INSTANCE.getConfig().getString(configPath + "material")));
            Material material = Material.getMaterial(SuperHumans.INSTANCE.getConfig().getString(configPath + "material"));
            itemBuilder.amount(SuperHumans.INSTANCE.getConfig().getInt(configPath + "amount"));

            if(SuperHumans.INSTANCE.getConfig().getString(configPath + "display-name") != null) {
                itemBuilder.displayName(ChatColor.translateAlternateColorCodes('&', SuperHumans.INSTANCE.getConfig().getString(configPath + ".display-name")));
            }

            if(SuperHumans.INSTANCE.getConfig().getStringList(configPath + "enchantments") != null) {
                for(String serializedEnchantment : SuperHumans.INSTANCE.getConfig().getStringList(configPath + "enchantments")) {
                    Enchantment enchantment = Enchantment.getByName(serializedEnchantment.split(":")[0]);
                    int level = Integer.parseInt(serializedEnchantment.split(":")[1]);
                    itemBuilder.enchantment(enchantment, level);
                }
            }

            if(SuperHumans.INSTANCE.getConfig().getString(configPath + "leather-armor-color") != null
                    && (material == Material.LEATHER_HELMET
                    || material == Material.LEATHER_CHESTPLATE
                    || material == Material.LEATHER_LEGGINGS
                    || material == Material.LEATHER_BOOTS)) {
                String[] rgb_string = SuperHumans.INSTANCE.getConfig().getString(configPath + "leather-armor-color").split(":");
                int R = Integer.parseInt(rgb_string[0]);
                int G = Integer.parseInt(rgb_string[1]);
                int B = Integer.parseInt(rgb_string[2]);
                itemBuilder.color(Color.fromRGB(R, G, B));
            }

            if(SuperHumans.INSTANCE.getConfig().getConfigurationSection(configPath + "shield-meta") != null
                    && material == Material.SHIELD) {
                itemBuilder.color(DyeColor.valueOf(SuperHumans.INSTANCE.getConfig().getString(configPath + "shield-meta.base-color")));

                if(SuperHumans.INSTANCE.getConfig().getStringList(configPath + "shield-meta.patterns") != null) {
                    Pattern[] patterns = null;
                    int i = 0;
                    for(String pattern : SuperHumans.INSTANCE.getConfig().getStringList(configPath + "shield-meta.patterns")) {
                        String[] patternSerialized = pattern.split(":");
                        patterns[i++] = new Pattern(DyeColor.valueOf(patternSerialized[1]), PatternType.valueOf(patternSerialized[0]));
                    }

                    itemBuilder.patterns(patterns);
                }
            }

            abilities.add(itemBuilder.build());
        }
        return ((ItemStack[])abilities.toArray());
    }

    @Override
    public ItemStack getAbility(SuperHuman superHuman, String name) {
        return null;
    }

    @Override
    public boolean setSuperHuman(SuperHuman superHuman, Player player) {

        if(superHuman != null) {
            player.getInventory().clear();
            player.getActivePotionEffects().clear();

            player.getInventory().addItem(this.getAbilities(superHuman));

        }

        return false;
    }

    @Override
    public PotionEffect[] getPotionEffects(SuperHuman superHuman) {
        List<PotionEffect> potionEffects = new ArrayList<>();
        SuperHumans.INSTANCE.getConfig().getStringList(superHuman.getName()).stream().filter(
                potionEffect ->
                        PotionEffectType.getByName(potionEffect.split(":")[0]) != null)
                .forEach(potionEffect ->
                        potionEffects.add(PotionEffectType.getByName(potionEffect.split(":")[0]).createEffect(Integer.MAX_VALUE, Integer.parseInt(potionEffect.split(":")[1]))));
        return ((PotionEffect[])potionEffects.toArray());
    }

    @Override
    public Set<SuperHuman> getSuperHumans() {
        return this.superHumans;
    }

}