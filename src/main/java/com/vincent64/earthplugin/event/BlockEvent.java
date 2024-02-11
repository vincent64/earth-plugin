package com.vincent64.earthplugin.event;

import com.vincent64.earthplugin.earth.Region;
import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.file.ShopData;
import com.vincent64.earthplugin.shop.ChestShop;
import com.vincent64.earthplugin.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

import java.util.HashMap;
import java.util.UUID;

public class BlockEvent implements Listener {
    private PlayerData playerData;
    private ShopData shopData;

    public BlockEvent(PlayerData playerData, ShopData shopData) {
        this.playerData = playerData;
        this.shopData = shopData;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        //Get block data
        BlockData blockData = block.getBlockData();
        if(blockData instanceof Directional) {
            //Get sign direction
            Directional directional = (Directional) blockData;
            //Get block behind the sign
            Block blockBehind = block.getRelative(directional.getFacing().getOppositeFace());
            //Check if the block behind is a chest
            if(blockBehind.getState() instanceof Chest) {
                //Get sign's first line
                String firstLine = event.getLine(0);
                //Check if the first line equals shop
                if(firstLine.equals("Shop")) {
                    //Check if there is already a shop there
                    if(!ChestShop.isChestShop(shopData, blockBehind)) {
                        //Create a chest shop
                        ChestShop.createShop(shopData, player, (Chest) blockBehind.getState(), event);
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(Messages.shopAlreadyPresent);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        Location location = block.getLocation();

        switch(block.getType()) {
            case SUGAR_CANE:
                //Check if the sugar cane was placed near the equator
                if(!Region.isNearEquator(location.getBlockZ())) {
                    event.setCancelled(true);
                    player.sendMessage(Messages.regionSugarCane);
                }
                break;
            case SWEET_BERRY_BUSH:
                //Check if the sweet berry bush was placed near the equator
                if(!Region.isInNorthPole(location.getBlockZ()) && !Region.isInSouthPole(location.getBlockZ())) {
                    event.setCancelled(true);
                    player.sendMessage(Messages.regionSweetBerries);
                }
                break;
            case COCOA_BEANS:
                //Check if the cocoa beans were placed near the equator
                if(!Region.isNearEquator(location.getBlockZ())) {
                    event.setCancelled(true);
                    player.sendMessage(Messages.regionCocoaPlant);
                }
                break;
        }

        //Check if the block being placed is a sign
        if(block.getState() instanceof Sign) {
            //Get sign direction
            Directional directional = (Directional) block.getBlockData();
            //Get block behind the sign
            Block blockBehind = block.getRelative(directional.getFacing().getOppositeFace());
            //Check if the block behind is a chest
            if(blockBehind.getState() instanceof Chest) {
                if(!ChestShop.isDoubleChest(blockBehind)) {
                    //Cancel the sign placing event if the chest is a chest shop
                    if(ChestShop.isChestShop(shopData, blockBehind)) {
                        event.setCancelled(true);
                        player.sendMessage(Messages.shopSignNotAllowed);
                    }
                } else {
                    DoubleChest doubleChest = ChestShop.getDoubleChest(blockBehind);
                    Chest leftChest = (Chest) doubleChest.getLeftSide();
                    Chest rightChest = (Chest) doubleChest.getRightSide();

                    //Check if one of the chest is a chest shop
                    if(ChestShop.isChestShop(shopData, leftChest.getBlock()) ||
                            ChestShop.isChestShop(shopData, rightChest.getBlock())) {
                        //Cancel the sign placing event if the chest is a chest shop
                        event.setCancelled(true);
                        player.sendMessage(Messages.shopSignNotAllowed);
                    }
                }
            }
        }

        //Check if the block being placed is a hopper
        if(block.getState() instanceof Hopper) {
            //Get the block above
            Block blockAbove = block.getRelative(BlockFace.UP);
            //Check if the block above is a chest shop
            if(blockAbove.getState() instanceof Chest) {
                if(!ChestShop.isDoubleChest(blockAbove)) {
                    if(ChestShop.isChestShop(shopData, blockAbove)) {
                        event.setCancelled(true);
                        player.sendMessage(Messages.shopHopperNotAllowed);
                    }
                } else {
                    DoubleChest doubleChest = ChestShop.getDoubleChest(blockAbove);
                    Chest leftChest = (Chest) doubleChest.getLeftSide();
                    Chest rightChest = (Chest) doubleChest.getRightSide();

                    //Check if one of the chest is a chest shop
                    if(ChestShop.isChestShop(shopData, leftChest.getBlock()) ||
                            ChestShop.isChestShop(shopData, rightChest.getBlock())) {
                        //Cancel the hopper placing event if the chest is a chest shop
                        event.setCancelled(true);
                        player.sendMessage(Messages.shopHopperNotAllowed);
                    }
                }
            }
        }

        if(!event.isCancelled()) {
            //Increase player's statistics
            playerData.getStatistics().increaseBlocksPlaced(player, 1);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if(block.getState() instanceof Chest) {
            //Check if the chest is a chest shop
            if(!ChestShop.isDoubleChest(block)) {
                if(ChestShop.isChestShop(shopData, block)) {
                    //Check if the player is the shop owner
                    if(ChestShop.isChestShopOwner(shopData, block, player)) {
                        //Remove the shop
                        shopData.removeShop(block.getX(), block.getY(), block.getZ());
                        event.getPlayer().sendMessage(Messages.shopRemoved);
                        //Remove players that are currently buying from the shop (rare)
                        removeTransactors(block);
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(Messages.shopBreakAttempt);
                    }
                }
            } else {
                DoubleChest doubleChest = ChestShop.getDoubleChest(block);
                Chest leftChest = (Chest) doubleChest.getLeftSide();
                Chest rightChest = (Chest) doubleChest.getRightSide();

                //Check if one of the chest is a chest shop
                if(ChestShop.isChestShop(shopData, leftChest.getBlock())) {
                    //Check if the player is the shop owner
                    if(!ChestShop.isChestShopOwner(shopData, leftChest.getBlock(), player)) {
                        event.setCancelled(true);
                        player.sendMessage(Messages.shopBreakAttempt);
                    }
                } else if(ChestShop.isChestShop(shopData, rightChest.getBlock())) {
                    //Check if the player is the shop owner
                    if(!ChestShop.isChestShopOwner(shopData, rightChest.getBlock(), player)) {
                        event.setCancelled(true);
                        player.sendMessage(Messages.shopBreakAttempt);
                    }
                }
            }
        }

        //Check if the block broken is a sign
        if(ChestShop.isSignChestShop(shopData, block)) {
            Block chestBlock = ChestShop.getChestShopFromSign(block);

            //Check if the player is the shop owner
            if(ChestShop.isChestShopOwner(shopData, chestBlock, player)) {
                //Remove the shop
                shopData.removeShop(chestBlock.getX(), chestBlock.getY(), chestBlock.getZ());
                event.getPlayer().sendMessage(Messages.shopRemoved);
                //Remove players that are currently buying from the shop (rare)
                removeTransactors(chestBlock);
            } else {
                event.setCancelled(true);
                player.sendMessage(Messages.shopBreakAttempt);
            }
        }

        //Check if the block is a chair block
        if(block.getBlockData() instanceof Stairs || block.getBlockData() instanceof Slab) {
            if(playerData.isBlockChair(block)) {
                //Remove the arrow and the player
                playerData.removeChairByBlock(block);
            }
        }
    }

    private void removeTransactors(Block block) {
        HashMap<UUID, Block> transactors = playerData.getCurrentTransactor();

        //Check if a player is making a transaction with this block
        for(UUID uuid : transactors.keySet()) {
            Block transactorBlock = transactors.get(uuid);
            if(transactorBlock.getX() == block.getX() &&
                    transactorBlock.getY() == block.getY() &&
                    transactorBlock.getZ() == block.getZ()) {
                //Cancel the transaction
                Player player = Bukkit.getPlayer(uuid);
                //playerData.removeTransactor(player);
                player.sendMessage(Messages.shopCancelled);
            }
        }
    }
}
