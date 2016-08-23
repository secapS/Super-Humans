package xyz.whynospaces.superhumans;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class SuperHumanFactory {

    public SuperHuman getSuperHumanByName(String name) {
        if(isSuperHuman(name)) {
            String configPath = "superhumans." + name;
            return new SuperHuman(SuperHumans.instance.getConfig().getString(configPath + "display-name"), deserializePotionEffect(name), deserializeItems(name));
        }
        return null;
    }

    public boolean isSuperHuman(String name) {
        System.out.println("Testing: " +  SuperHumans.instance.getConfig().getConfigurationSection("superhumans").getKeys(false).toArray());
        for(String heroes : SuperHumans.instance.getConfig().getConfigurationSection("superhumans").getKeys(false)) {
            return name.equalsIgnoreCase(heroes);
        }
        return false;
    }

    public List<PotionEffect> deserializePotionEffect(String name) {
        List<PotionEffect> deserializedPotionEffects = new ArrayList<PotionEffect>();

        for(String serializedPotion : SuperHumans.instance.getConfig().getStringList("superhumans." + name + "potion-effects")) {
            String potions = serializedPotion.split(":")[0];
            Integer amplifier = Integer.parseInt(serializedPotion.split(":")[1]);
            for(PotionEffectType potionEffectType : PotionEffectType.values()) {
                if(potions.equalsIgnoreCase(potionEffectType.getName())) {
                    deserializedPotionEffects.add(potionEffectType.createEffect(Integer.MAX_VALUE, amplifier));
                }
            }
        }

        return deserializedPotionEffects;
    }

    public List<ItemStack> deserializeItems(String name) {
        List<ItemStack> deserializedItems = new ArrayList<ItemStack>();
        String configPath = "superhumans." + name + "items.";

        for(String items : SuperHumans.instance.getConfig().getConfigurationSection(configPath).getKeys(false)) {
            configPath = configPath + items + ".";
            ItemStack item = new ItemStack(Material.valueOf(SuperHumans.instance.getConfig().getString(configPath + "material")),
                    SuperHumans.instance.getConfig().getInt(configPath + "amount"));
            ItemMeta itemMeta = item.getItemMeta();

            if(SuperHumans.instance.getConfig().getConfigurationSection(configPath + "display-name") != null) {
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', SuperHumans.instance.getConfig().getString(configPath + ".display-name")));
            }

            if(SuperHumans.instance.getConfig().getConfigurationSection(configPath + "leather-armor-color") != null
                    && item.getType() == Material.LEATHER_HELMET
                    && item.getType() == Material.LEATHER_CHESTPLATE
                    && item.getType() == Material.LEATHER_LEGGINGS
                    && item.getType() == Material.LEATHER_BOOTS) {
                String[] rgb_string = SuperHumans.instance.getConfig().getString(configPath + "leather-armor-color").split(":");
                int R = Integer.parseInt(rgb_string[0]);
                int G = Integer.parseInt(rgb_string[1]);
                int B = Integer.parseInt(rgb_string[2]);
                ((LeatherArmorMeta)itemMeta).setColor(Color.fromRGB(R, G, B));
            }

            if(SuperHumans.instance.getConfig().getConfigurationSection(configPath + "shield-meta") != null
                    && item.getType() == Material.SHIELD) {
                BlockStateMeta shieldMeta = (BlockStateMeta)itemMeta;
                Banner banner = (Banner)shieldMeta;
                banner.setBaseColor(DyeColor.valueOf(SuperHumans.instance.getConfig().getString(configPath + "shield-meta.base-color")));

                for(String patterns : SuperHumans.instance.getConfig().getStringList(configPath + "shield-meta.patters")) {
                    String[] patternSerialized = patterns.split(":");
                    banner.addPattern(new Pattern(DyeColor.valueOf(patternSerialized[1]), PatternType.valueOf(patternSerialized[0])));
                }
            }

            item.setItemMeta(itemMeta);
            deserializedItems.add(item);
        }
        return deserializedItems;
    }
}
