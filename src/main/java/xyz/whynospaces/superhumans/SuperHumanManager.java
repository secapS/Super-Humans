package xyz.whynospaces.superhumans;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.whynospaces.superhumans.api.Ability;
import xyz.whynospaces.superhumans.api.SuperHuman;
import xyz.whynospaces.superhumans.api.SuperHumanAPI;
import xyz.whynospaces.superhumans.api.events.PlayerSetSuperHumanEvent;
import xyz.whynospaces.superhumans.utils.ItemBuilder;

import java.util.*;

public class SuperHumanManager implements SuperHumanAPI {

    public Set<SuperHuman> superHumans = new HashSet<>();
    public Map<String, SuperHuman> users = new HashMap<>();

    @Override
    public void registerSuperHuman(SuperHuman superHuman) {
        this.superHumans.add(superHuman);
        SuperHumans.INSTANCE.getServer().getPluginManager().registerEvents(superHuman, SuperHumans.INSTANCE);

        for(Ability ability : superHuman.getAbilities()) {
            SuperHumans.INSTANCE.getServer().getPluginManager().registerEvents(new Listener() {
                @EventHandler
                public void onDrop(PlayerDropItemEvent event) {
                    ability.onDrop(event);
                }

                @EventHandler
                public void onClick(PlayerInteractEvent event) {
                    ability.onClick(event);
                }
            }, SuperHumans.INSTANCE);
        }
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
    public Set<ItemStack> getAbilities(SuperHuman superHuman) {
        Set<ItemStack> abilities = new HashSet<>();
        for(String items : SuperHumans.INSTANCE.getConfig().getConfigurationSection("superhumans." + superHuman.getName() + ".items").getKeys(false)) {
            String configPath = "superhumans." + superHuman.getName() + ".items." + items + ".";
            abilities.add(getAbility(superHuman, items));
        }
        return abilities;
    }

    @Override
    public ItemStack getAbility(SuperHuman superHuman, String name) {
        return buildItem(superHuman, name);
    }

    public ItemStack buildItem(SuperHuman superHuman, String itemName) {
        if(SuperHumans.INSTANCE.getConfig().getConfigurationSection("superhumans." + superHuman.getName() + ".items." + itemName) != null) {
            String configPath = "superhumans." + superHuman.getName() + ".items." + itemName + ".";
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
                    List<Pattern> patterns = new ArrayList<>();
                    for(String pattern : SuperHumans.INSTANCE.getConfig().getStringList(configPath + "shield-meta.patterns")) {
                        String[] patternSerialized = pattern.split(":");
                        patterns.add(new Pattern(DyeColor.valueOf(patternSerialized[1]), PatternType.valueOf(patternSerialized[0])));
                    }

                    itemBuilder.patterns(patterns.toArray(new Pattern[patterns.size()]));
                }
            }
            return itemBuilder.build();
        }
        return null;
    }


    @Override
    public boolean setSuperHuman(SuperHuman superHuman, Player player) {

        if(superHuman != null) {
            this.users.put(player.getName(), superHuman);
            player.getInventory().clear();
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

            this.getAbilities(superHuman).forEach(itemStack -> player.getInventory().addItem(itemStack));
            superHuman.getPotionEffects().forEach(potionEffect -> player.addPotionEffect(potionEffect));

            PlayerSetSuperHumanEvent playerSetSuperHumanEvent = new PlayerSetSuperHumanEvent(player, superHuman);

            if(!playerSetSuperHumanEvent.isCancelled()) {
                SuperHumans.INSTANCE.getServer().getPluginManager().callEvent(playerSetSuperHumanEvent);
            }
            return true;
        }
        else if(this.users.remove(player.getName(), superHuman)){
            player.getInventory().clear();
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

            return true;
        }

        return false;
    }

    @Override
    public Set<PotionEffect> getPotionEffects(SuperHuman superHuman) {
        Set<PotionEffect> potionEffects = new HashSet<>();
        SuperHumans.INSTANCE.getConfig().getStringList(superHuman.getName()).stream().filter(
                potionEffect ->
                        PotionEffectType.getByName(potionEffect.split(":")[0]) != null)
                .forEach(potionEffect ->
                        potionEffects.add(PotionEffectType.getByName(potionEffect.split(":")[0]).createEffect(Integer.MAX_VALUE, Integer.parseInt(potionEffect.split(":")[1]))));
        return potionEffects;
    }

    @Override
    public Set<SuperHuman> getSuperHumans() {
        return this.superHumans;
    }

    public Map<String, SuperHuman> getUsers() {
        return this.users;
    }

}
