package com.vincent64.earthplugin.earth;

import com.vincent64.earthplugin.Config;
import com.vincent64.earthplugin.EarthPlugin;
import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.log.Log;
import com.vincent64.earthplugin.util.Messages;
import com.vincent64.earthplugin.util.RandomUtil;
import com.vincent64.earthplugin.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.List;

public class MysteryCrate {
    private PlayerData playerData;
    private List<ItemStack> items;
    private int x = 0;
    private int y = Integer.MAX_VALUE;
    private int z = 0;
    private boolean isFound;

    public MysteryCrate(PlayerData playerData) {
        this.playerData = playerData;
    }

    public static void create(MysteryCrate mysteryCrate) {
        Log.println("MysteryCrate: Generating crate...");
        //Get the main world
        World world = Bukkit.getWorld(Config.MAIN_WORLD_NAME);
        //Get random coordinates for the crate
        int x = getRandomX();
        int z = getRandomZ();
        int y = world.getHighestBlockYAt(x, z) + 1;

        //Check if the coordinates are good
        boolean isGoodCoordinates = false;
        while(!isGoodCoordinates) {
            //Get the block at these coordinates
            Material blockBelow = world.getBlockAt(x, y - 1, z).getType();

            //Check if is no water below
            if(blockBelow != Material.WATER) {
                isGoodCoordinates = true;
            } else {
                //Regenerate coordinates if they are not good
                x = getRandomX();
                z = getRandomZ();
                y = world.getHighestBlockYAt(x, z) + 1;
            }
        }

        //Generate random inventory
        List<ItemStack> items = generateItems();

        //Set block as a chest
        Block block = world.getBlockAt(x, y, z);
        block.setType(Material.CHEST);
        //Get chest inventory
        Chest chest = (Chest) block.getState();
        Inventory inventory = chest.getInventory();

        //Iterate through each item in the list
        int i = 0;
        for(ItemStack item : items) {
            inventory.setItem(i * 2, item);
            i++;
        }

        //Send message to all players
        Place nearestPlace = Place.getNearestPlace(x, z);
        String timeLeft = TimeUtil.secondsToHMS((long) (Config.MYSTERY_CRATE_INTERVAL_TICKS / 20d));
        Bukkit.broadcastMessage(String.format(Messages.crateCreated,
                nearestPlace.getPrefix() + " " + nearestPlace.getName(), timeLeft));
        Log.println("MysteryCrate: Crate generate at " + x + ", " + y + ", " + z);

        //Create mystery crate
        mysteryCrate.setItems(items);
        mysteryCrate.setLocation(x, y, z);
        mysteryCrate.setFound(false);
    }

    private static int getRandomX() {
        return RandomUtil.getRandomInt(Config.MAIN_WORLD_MIN_X, Config.MAIN_WORLD_MAX_X);
    }

    private static int getRandomZ() {
        return RandomUtil.getRandomInt(Config.MAIN_WORLD_MIN_Z, Config.MAIN_WORLD_MAX_X);
    }

    private static List<ItemStack> generateItems() {
        //Create item list
        List<ItemStack> items = new ArrayList<>();
        //Get random amount of item
        int amountOfItem = RandomUtil.getRandomInt(3, 14);

        //Iterate through each item
        for(int i = 0; i < amountOfItem; i++) {
            //Get random item category
            CrateItemCategory category = CrateItemCategory.getRandomCategory();

            if(category == CrateItemCategory.ENCHANTED_BOOK_PROBABILITY) {
                //Get random enchanted book
                ItemStack item = getRandomEnchantedBook();
                //Add the item to the list
                items.add(item);
            } else {
                //Get random item from this category
                Material randomItem = category.getRandomItem();
                //Get random quantity
                int randomQuantity = RandomUtil.getRandomInt(1, Math.min(randomItem.getMaxStackSize(), category.getMaxQuantity()));

                //Create item
                ItemStack item = new ItemStack(randomItem, randomQuantity);
                //Add the item to the list
                items.add(item);
            }
        }

        return items;
    }

    private static ItemStack getRandomEnchantedBook() {
        //Get random enchantment
        int randomIndex = RandomUtil.getRandomInt(0, Registry.ENCHANTMENT.stream().toArray().length - 1);
        Enchantment randomEnchantment = Enchantment.PROTECTION_ENVIRONMENTAL;
        int currentIndex = 0;
        for(Enchantment enchantment : Registry.ENCHANTMENT) {
            if(currentIndex == randomIndex) {
                randomEnchantment = enchantment;
                break;
            } else {
                currentIndex++;
            }
        }

        //Get the enchantment min and max level
        int minLevel = randomEnchantment.getStartLevel();
        int maxLevel = randomEnchantment.getMaxLevel();
        //Get random level between these
        int randomLevel = RandomUtil.getRandomInt(minLevel, maxLevel);

        //Create book
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemStack.getItemMeta();
        meta.addStoredEnchant(randomEnchantment, randomLevel, false);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public void setFound(Player player) {
        //Check if the crate was found
        if(!isFound) {
            //Set crate as found and send public message of the finder
            Bukkit.broadcastMessage(String.format(Messages.crateFound, player.getName()));
            isFound = true;

            //Give money prize to the player who found it first
            playerData.increaseMoney(player, Config.MYSTERY_CRATE_PRIZE_MONEY);
            player.sendMessage(String.format(Messages.crateFirst, Config.MYSTERY_CRATE_PRIZE_MONEY + Config.CURRENCY));
        }
    }

    public void remove() {
        //Get main world
        World world = Bukkit.getWorld(Config.MAIN_WORLD_NAME);
        //Remove the block
        world.getBlockAt(x, y, z).setType(Material.AIR);

        //Set default crate coordinates
        setItems(null);
        setLocation(0, Integer.MAX_VALUE, 0);
        setFound(false);
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public void setLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setFound(boolean found) {
        isFound = found;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
