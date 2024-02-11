package com.vincent64.earthplugin.shop;

import com.vincent64.earthplugin.Config;
import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.file.ShopData;
import com.vincent64.earthplugin.log.Log;
import com.vincent64.earthplugin.util.Messages;
import com.vincent64.earthplugin.util.MoneyUtil;
import com.vincent64.earthplugin.util.NameUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.UUID;
import java.util.regex.Pattern;

public class ChestShop {
    private static final Pattern pricePattern =
            Pattern.compile("\\d{1,7}(\\.\\d{1,2})?");

    public static void createShop(ShopData shopData, Player player, Chest chest, SignChangeEvent event) {
        //Get line content
        String typeLine = event.getLine(1);
        String priceLine = event.getLine(2);
        String itemLine = event.getLine(3);

        //Check if type is either buy or sell
        if(typeLine.equals("buy") || typeLine.equals("sell")) {
            //Check if price is correct
            if(pricePattern.matcher(priceLine).matches()) {
                //Get the item
                Material item = Material.matchMaterial(itemLine);
                //Check if item id is correct
                if(item != null && item.isItem()) {
                    UUID seller = player.getUniqueId();
                    ShopType type = ShopType.getType(typeLine);
                    double price = Double.parseDouble(priceLine);

                    //Create lines to show on the chest shop sign
                    String typeShop = typeLine.equals("buy") ? "Buy" : "Sell";
                    String priceShop = new DecimalFormat("#.##").format(price) + Config.CURRENCY;
                    String itemShop = NameUtil.getUserItemName(item.name());

                    //Change chest shop sign
                    event.setLine(0, ChatColor.BLUE + player.getName());
                    event.setLine(1, ChatColor.GREEN + typeShop);
                    event.setLine(2, ChatColor.GOLD + priceShop);
                    event.setLine(3, ChatColor.GREEN + itemShop);

                    //Create shop object
                    Shop shop = new Shop(seller, type, item, chest.getX(), chest.getY(), chest.getZ(), price);
                    //Add shop to shop list
                    shopData.addShop(shop);
                    player.sendMessage(Messages.shopCreated);
                } else {
                    player.sendMessage(Messages.shopInvalidItem);
                }
            } else {
                player.sendMessage(String.format(Messages.shopInvalidPrice, Config.CURRENCY, Config.CURRENCY));
            }
        } else {
            player.sendMessage(Messages.shopInvalidType);
        }
    }

    public static boolean buyFromShop(ShopData shopData, PlayerData playerData, Block block, Player player, String quantityString) {
        //Check if the quantity is valid
        if(MoneyUtil.isValidQuantityString(quantityString)) {
            int quantity = Integer.parseInt(quantityString);

            //Get chest inventory
            Chest chest = (Chest) block.getState();
            Inventory inventory = chest.getInventory();
            //Get shop
            Shop shop = shopData.getShopAt(block.getX(), block.getY(), block.getZ());

            //Get player inventory
            Inventory playerInventory = player.getInventory();

            //Create item object
            ItemStack itemStack = new ItemStack(shop.getItem(), quantity);

            //Check if the chest contains enough items
            if(inventoryContainsAtLeast(inventory, itemStack, quantity)) {
                //Check if the player has enough in their inventory
                if(hasRoomForItem(playerInventory, itemStack, quantity)) {
                    //Get total price according to quantity
                    double totalPrice = quantity * shop.getPrice();

                    //Check if the player has enough money
                    if (playerData.hasEnoughMoney(player, totalPrice)) {
                        //Pay the shop seller
                        playerData.decreaseMoney(player, totalPrice);
                        Player seller = Bukkit.getPlayer(shop.getSeller());
                        playerData.increaseMoney(seller, totalPrice);

                        //Give the items to the player and remove it from the chest
                        transferItem(inventory, playerInventory, itemStack, quantity);
                        player.sendMessage(String.format(Messages.shopBuySuccess, quantity, NameUtil.getUserItemName(itemStack.getType().name())));
                        Log.println("Player " + player.getName() + " bought " + quantity + "x " + NameUtil.getUserItemName(itemStack.getType().name()) + " from shop: " + shop);
                        return true;
                    } else {
                        player.sendMessage(Messages.shopNotEnoughMoney);
                    }
                } else {
                    player.sendMessage(Messages.shopNotEnoughRoomPlayer);
                }
            } else {
                player.sendMessage(Messages.shopNotEnoughQuantity);
            }
        } else {
            player.sendMessage(Messages.shopInvalidQuantity);
        }
        return false;
    }

    public static boolean sellToShop(ShopData shopData, PlayerData playerData, Block block, Player player, String quantityString) {
        //Check if the quantity is valid
        if(MoneyUtil.isValidQuantityString(quantityString)) {
            int quantity = Integer.parseInt(quantityString);

            //Get chest inventory
            Chest chest = (Chest) block.getState();
            Inventory inventory = chest.getInventory();
            //Get shop
            Shop shop = shopData.getShopAt(block.getX(), block.getY(), block.getZ());

            //Get player inventory
            Inventory playerInventory = player.getInventory();

            //Create item object
            ItemStack itemStack = new ItemStack(shop.getItem(), quantity);

            //Check if the player has enough items
            if(inventoryContainsAtLeast(playerInventory, itemStack, quantity)) {
                //Check if the chest has enough room for the items
                if(hasRoomForItem(inventory, itemStack, quantity)) {
                    //Get total price according to quantity
                    double totalPrice = quantity * shop.getPrice();

                    //Check if the seller has enough money
                    Player seller = Bukkit.getPlayer(shop.getSeller());
                    if (playerData.hasEnoughMoney(seller, totalPrice)) {
                        //Pay the player
                        playerData.decreaseMoney(seller, totalPrice);
                        playerData.increaseMoney(player, totalPrice);

                        //Give the items to the player and remove it from the chest
                        transferItem(playerInventory, inventory, itemStack, quantity);
                        player.sendMessage(String.format(Messages.shopSellSuccess, quantity, NameUtil.getUserItemName(itemStack.getType().name())));
                        Log.println("Player " + player.getName() + " sold " + quantity + "x " + NameUtil.getUserItemName(itemStack.getType().name()) + " to shop: " + shop);
                        return true;
                    } else {
                        player.sendMessage(Messages.shopNotEnoughMoneySeller);
                    }
                } else {
                    player.sendMessage(Messages.shopNotEnoughRoom);
                }
            } else {
                player.sendMessage(Messages.shopNotEnoughQuantityPlayer);
            }
        } else {
            player.sendMessage(Messages.shopInvalidQuantity);
        }
        return false;
    }

    public static boolean isChestShop(ShopData shopData, Block block) {
        //Check if the block is a chest
        if(block.getState() instanceof Chest) {
            Chest chest = (Chest) block.getState();
            //Check if the chest is a chest shop
            return shopData.getShopAt(chest.getX(), chest.getY(), chest.getZ()) != null;
        }

        return false;
    }

    public static boolean isSignChestShop(ShopData shopData, Block block) {
        //Check if the block is a sign
        if(block.getState() instanceof Sign) {
            //Get block data
            BlockData blockData = block.getBlockData();
            if(blockData instanceof Directional) {
                //Get sign direction
                Directional directional = (Directional) blockData;
                //Get block behind the sign
                Block blockBehind = block.getRelative(directional.getFacing().getOppositeFace());
                //Check if the block behind is a chest
                if(blockBehind.getState() instanceof Chest) {
                    Chest chest = (Chest) blockBehind.getState();
                    //Check if the chest is a chest shop
                    return shopData.getShopAt(chest.getX(), chest.getY(), chest.getZ()) != null;
                }
            }
        }

        return false;
    }

    public static Block getChestShopFromSign(Block block) {
        return block.getRelative(((Directional) block.getBlockData()).getFacing().getOppositeFace());
    }

    public static boolean isChestShopOwner(ShopData shopData, Block block, Player player) {
        Chest chest = (Chest) block.getState();

        //Get shop from coordinates
        Shop shop = shopData.getShopAt(chest.getX(), chest.getY(), chest.getZ());
        //Check if the shop belongs to the player
        return shop.getSeller().equals(player.getUniqueId());
    }

    public static boolean isDoubleChest(Block block) {
        return ((Chest) block.getState()).getInventory() instanceof DoubleChestInventory;
    }

    public static DoubleChest getDoubleChest(Block block) {
        return (DoubleChest) ((Chest) block.getState()).getInventory().getHolder();
    }

    private static boolean inventoryContainsAtLeast(Inventory inventory, ItemStack itemStack, int quantity) {
        int totalQuantity = 0;

        //Loop through each item in the inventory
        for(ItemStack item : inventory.getContents()) {
            if(item != null && item.getType() == itemStack.getType()) {
                totalQuantity += item.getAmount();
            }
        }

        return totalQuantity >= quantity;
    }

    private static void transferItem(Inventory inventoryFrom, Inventory inventoryTo, ItemStack itemStack, int quantity) {
        //Loop through each item in the starting inventory
        for(int i = 0; i < inventoryFrom.getSize(); i++) {
            //Iterate as long as quantity isn't equal to zero
            if(quantity > 0) {
                ItemStack item = inventoryFrom.getItem(i);
                if(item != null && item.getType() == itemStack.getType()) {
                    if(item.getAmount() <= quantity) {
                        int itemQuantity = item.getAmount();
                        //Transfer item to the other inventory and decrease quantity
                        inventoryTo.addItem(item.clone());
                        inventoryFrom.setItem(i, null);
                        quantity -= itemQuantity;
                    } else {
                        //Transfer only a fraction of the item stack
                        ItemStack clonedItemTo = item.clone();
                        ItemStack clonedItemFrom = item.clone();
                        clonedItemTo.setAmount(quantity);
                        clonedItemFrom.setAmount(item.getAmount() - quantity);
                        inventoryTo.addItem(clonedItemTo);
                        inventoryFrom.setItem(i, clonedItemFrom);
                        quantity = 0;
                    }
                }
            }
        }
    }

    private static boolean hasRoomForItem(Inventory inventory, ItemStack itemStack, int quantity) {
        for(int i = 0; i < inventory.getStorageContents().length; i++) {
            ItemStack item = inventory.getItem(i);
            if(item != null) {
                if(item.getType() == itemStack.getType()) {
                    quantity -= item.getMaxStackSize() - item.getAmount();
                }
            } else {
                quantity -= itemStack.getMaxStackSize();
            }
        }

        return quantity <= 0;
    }
}
