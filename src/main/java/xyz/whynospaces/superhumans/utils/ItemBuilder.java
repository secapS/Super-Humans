package xyz.whynospaces.superhumans.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder
{
    protected Material material;
    protected int amount = 1;
    protected short durability = 0;

    protected String displayName;
    protected List<String> lore;
    protected ItemFlag[] flags;
    protected Map<Enchantment, Integer> enchantments = new HashMap<>();
    protected String owner;
    protected Pattern[] patterns;
    protected DyeColor baseColor;
    protected Color color;

    public ItemBuilder(Material material)
    {
        this.material = material;
    }

    public ItemBuilder(ItemStack itemStack)
    {
        this.material = itemStack.getType();
        this.amount = itemStack.getAmount();
        this.durability = itemStack.getDurability();

        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta.hasDisplayName())
            this.displayName = itemMeta.getDisplayName();
        if(itemMeta.hasLore())
            this.lore = new ArrayList<>(itemMeta.getLore());

        Set<ItemFlag> itemFlags = itemMeta.getItemFlags();
        if(!itemFlags.isEmpty()) this.flags = itemFlags.toArray(new ItemFlag[itemFlags.size()]);

        this.enchantments = itemMeta.getEnchants();

        if(this.enchantments.size() == 0) this.enchantments = null;
    }

    public ItemBuilder material(Material material)
    {
        this.material = material;

        return this;
    }

    public ItemBuilder amount(int amount)
    {
        this.amount = amount;

        return this;
    }

    public ItemBuilder durability(int durability)
    {
        this.durability = (short) durability;

        return this;
    }


    public ItemBuilder displayName(String displayName)
    {
        this.displayName = ChatColor.WHITE + displayName;

        return this;
    }

    public ItemBuilder lore(String... loreArray)
    {
        this.lore = processLore(new ArrayList<>(Arrays.asList(loreArray)));

        return this;
    }

    public ItemBuilder addLore(List<String> additionalLore)
    {
        additionalLore = processLore(additionalLore);

        if(this.lore == null)
            this.lore = new ArrayList<>();

        this.lore.addAll(additionalLore);

        return this;
    }

    public ItemBuilder flags(ItemFlag... flags)
    {
        this.flags = flags;

        return this;
    }


    public ItemBuilder enchantment(Enchantment enchantment, int level)
    {
        this.enchantments.put(enchantment, level);

        return this;
    }


    protected List<String> processLore(List<String> oldLore)
    {
        List<String> lore = new ArrayList<String>();

        for(String l : oldLore)
        {
            String[] splitLine = l.split("\\n");
            for(String s : splitLine)
                lore.add(ChatColor.WHITE + s);
        }

        return lore;
    }

    public ItemBuilder owner(String name) {
        if(this.material == Material.SKULL) {
            this.owner = name;
            return this;
        } else {
            throw new IllegalArgumentException("The item must be a skull in order to have an owner.");
        }
    }

    public ItemBuilder color(DyeColor baseColor) {
        if(this.material == Material.SHIELD) {
            this.baseColor = baseColor;
            return this;
        } else {
            throw new IllegalArgumentException("The item must be a shield in order to have a base color.");
        }
    }

    public ItemBuilder color(Color color) {
        if(this.material == Material.LEATHER_HELMET || this.material == Material.LEATHER_CHESTPLATE ||
                this.material == Material.LEATHER_LEGGINGS ||
                this.material == Material.LEATHER_BOOTS) {
            this.color = color;
            return this;
        } else {
            throw new IllegalArgumentException("The item must be a leather armor piece in order to have patterns.");
        }
    }

    public ItemBuilder patterns(Pattern[] patterns) {
        if(this.material == Material.SHIELD) {
            this.patterns = patterns;
            return this;
        } else {
            throw new IllegalArgumentException("The item must be a shield in order to have patterns.");
        }
    }

    public ItemStack build()
    {
        if(this.material == null) throw new IllegalArgumentException("No material was specified for the itemstack");

        ItemStack itemStack = new ItemStack(this.material, this.amount, this.durability);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if(this.displayName != null) itemMeta.setDisplayName(this.displayName);
        if(this.lore != null) itemMeta.setLore(this.lore);
        if(this.flags != null) itemMeta.addItemFlags(this.flags);
        if(this.owner != null) ((SkullMeta)itemMeta).setOwner(this.owner);
        if(this.baseColor != null) {
            BlockStateMeta shieldMeta = (BlockStateMeta)itemMeta;
            Banner banner = (Banner)shieldMeta.getBlockState();
            banner.setBaseColor(this.baseColor);

            if(patterns != null) {
                for(Pattern pattern : patterns) {
                    banner.addPattern(pattern);
                }
            }
            banner.update();
            shieldMeta.setBlockState(banner);
        }

        if(this.color != null) {
            ((LeatherArmorMeta)itemMeta).setColor(color);
        }

        itemStack.setItemMeta(itemMeta);

        if(this.enchantments != null) itemStack.addUnsafeEnchantments(this.enchantments);

        return itemStack;
    }
}
