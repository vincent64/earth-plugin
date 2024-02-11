package com.vincent64.earthplugin.earth;

import com.vincent64.earthplugin.util.RandomUtil;
import org.bukkit.Material;

public enum CrateItemCategory {
    ENCHANTED_BOOK_PROBABILITY(200, 1, Material.ENCHANTED_BOOK),
    ORE_PROBABILITY(200, 4,
            Material.DIAMOND, Material.GOLD_INGOT, Material.EMERALD, Material.IRON_INGOT, Material.REDSTONE, Material.LAPIS_LAZULI,
            Material.COAL_BLOCK, Material.COAL, Material.GOLD_NUGGET, Material.IRON_NUGGET),
    DUNGEON_ITEM_PROBABILITY(500, 8,
            Material.SADDLE, Material.NAME_TAG, Material.AMETHYST_SHARD, Material.BEETROOT_SEEDS, Material.SPECTRAL_ARROW,
            Material.DISC_FRAGMENT_5, Material.MUSIC_DISC_11, Material.MUSIC_DISC_5, Material.MUSIC_DISC_13, Material.MUSIC_DISC_CAT,
            Material.MUSIC_DISC_BLOCKS, Material.MUSIC_DISC_CHIRP, Material.MUSIC_DISC_FAR, Material.MUSIC_DISC_MALL, Material.MUSIC_DISC_MELLOHI,
            Material.MUSIC_DISC_WARD, Material.MUSIC_DISC_WAIT, Material.MUSIC_DISC_STRAD, Material.MUSIC_DISC_STAL, Material.PRISMARINE_CRYSTALS,
            Material.PRISMARINE_SHARD, Material.ARCHER_POTTERY_SHERD, Material.BREWER_POTTERY_SHERD, Material.EXPLORER_POTTERY_SHERD, Material.MINER_POTTERY_SHERD,
            Material.BLADE_POTTERY_SHERD, Material.SLIME_BALL, Material.GUNPOWDER, Material.BONE, Material.APPLE, Material.GOLDEN_APPLE,
            Material.DIAMOND_HORSE_ARMOR, Material.IRON_HORSE_ARMOR, Material.LEATHER_HORSE_ARMOR, Material.GOLDEN_HORSE_ARMOR),
    TOTEM_PROBABILITY(1, 1, Material.TOTEM_OF_UNDYING),
    ENCHANTING_BOTTLE_PROBABILITY(70, 3, Material.EXPERIENCE_BOTTLE),
    ENCHANTED_APPLE_PROBABILITY(29, 1, Material.ENCHANTED_GOLDEN_APPLE);

    private static CrateItemCategory[] weightedCategory;
    private int probability;
    private int maxQuantity;
    private Material[] items;

    CrateItemCategory(int probability, int maxQuantity, Material... items) {
        this.probability = probability;
        this.maxQuantity = maxQuantity;
        this.items = items;
    }

    static {
        //Generate weighted category array
        weightedCategory = new CrateItemCategory[1000];

        //Iterate through each category
        int k = 0;
        for(CrateItemCategory category : values()) {
            //Add the category the amount of time its probability
            for(int i = 0; i < category.getProbability(); i++) {
                weightedCategory[k] = category;
                k++;
            }
        }
    }

    public static CrateItemCategory getRandomCategory() {
        return weightedCategory[RandomUtil.getRandomInt(0, weightedCategory.length - 1)];
    }

    public Material getRandomItem() {
        return items[RandomUtil.getRandomInt(0, items.length - 1)];
    }

    public float getProbability() {
        return probability;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public Material[] getItems() {
        return items;
    }
}
