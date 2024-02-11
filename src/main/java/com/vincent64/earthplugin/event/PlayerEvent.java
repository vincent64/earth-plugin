package com.vincent64.earthplugin.event;

import com.vincent64.earthplugin.Config;
import com.vincent64.earthplugin.earth.MysteryCrate;
import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.file.ShopData;
import com.vincent64.earthplugin.shop.ChestShop;
import com.vincent64.earthplugin.shop.ShopType;
import com.vincent64.earthplugin.util.Messages;
import com.vincent64.earthplugin.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerEvent implements Listener {
    private PlayerData playerData;
    private ShopData shopData;
    private MysteryCrate mysteryCrate;

    public PlayerEvent(PlayerData playerData, ShopData shopData, MysteryCrate mysteryCrate) {
        this.playerData = playerData;
        this.shopData = shopData;
        this.mysteryCrate = mysteryCrate;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        Action action = event.getAction();

        //Check if the action is right-clicking a block
        if(action == Action.RIGHT_CLICK_BLOCK) {
            //Check if the block is a chest
            if(clickedBlock.getState() instanceof Chest) {
                //Check if the chest is a double chest or not
                if(!ChestShop.isDoubleChest(clickedBlock)) {
                    //Check if the chest is a chest shop
                    if (ChestShop.isChestShop(shopData, clickedBlock)) {
                        //Check if the chest shop belongs to the player
                        if (!ChestShop.isChestShopOwner(shopData, clickedBlock, player)) {
                            event.setCancelled(true);
                            player.sendMessage(Messages.shopAccessDenied);
                        }
                    }
                } else {
                    DoubleChest doubleChest = ChestShop.getDoubleChest(clickedBlock);
                    Chest leftChest = (Chest) doubleChest.getLeftSide();
                    Chest rightChest = (Chest) doubleChest.getRightSide();

                    //Check if one of the chest is a chest shop
                    if(ChestShop.isChestShop(shopData, leftChest.getBlock())) {
                        //Check if the chest shop belongs to the player
                        if(!ChestShop.isChestShopOwner(shopData, leftChest.getBlock(), player)) {
                            event.setCancelled(true);
                            player.sendMessage(Messages.shopAccessDenied);
                        }
                    } else if(ChestShop.isChestShop(shopData, rightChest.getBlock())) {
                        //Check if the chest shop belongs to the player
                        if(!ChestShop.isChestShopOwner(shopData, rightChest.getBlock(), player)) {
                            event.setCancelled(true);
                            player.sendMessage(Messages.shopAccessDenied);
                        }
                    }
                }

                //Check if the chest is the crate
                if(clickedBlock.getX() == mysteryCrate.getX() &&
                        clickedBlock.getY() == mysteryCrate.getY() &&
                        clickedBlock.getZ() == mysteryCrate.getZ()) {
                    mysteryCrate.setFound(player);
                }

            }

            //Check if the block is a sign
            if(clickedBlock.getState() instanceof Sign) {
                //Check if the sign belongs to a chest shop
                if(ChestShop.isSignChestShop(shopData, clickedBlock)) {
                    Block chestBlock = ChestShop.getChestShopFromSign(clickedBlock);
                    event.setCancelled(true);

                    //Check if the player clicking it is the owner
                    if(!ChestShop.isChestShopOwner(shopData, chestBlock, player) || true) { //TODO: Remove tru haha
                        //Get shop
                        Shop shop = shopData.getShopAt(chestBlock.getX(), chestBlock.getY(), chestBlock.getZ());
                        //Check if the shop is sell or buy
                        if(shop.getType() == ShopType.BUY) {
                            //Ask player how much they want to buy
                            player.sendMessage(Messages.shopBuy);
                            playerData.addTransactor(player, chestBlock);
                        } else {
                            //Ask player how much they want to sell
                            player.sendMessage(Messages.shopSell);
                            playerData.addTransactor(player, chestBlock);
                        }
                    }
                }
            }

            //Check if the block is a stair or a slab
            if(clickedBlock.getBlockData() instanceof Stairs || clickedBlock.getBlockData() instanceof Slab) {
                //Make sure the player doesn't have any item in hand
                if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                    //Check if the player is not already sited
                    if(!playerData.isPlayerOnChair(player)) {
                        //Get main world
                        World world = Bukkit.getWorld(Config.MAIN_WORLD_NAME);
                        //Get location
                        Location location = clickedBlock.getLocation();
                        //Spawn an arror on the stair/slab
                        Arrow arrow = world.spawn(location.add(0.5d, 0.0d, 0.5d), Arrow.class);
                        arrow.addPassenger(player);
                        playerData.addChair(player, arrow, clickedBlock);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String truncatedDeathMessage = event.getDeathMessage().substring(player.getName().length() + 1);

        //Send message
        event.setDeathMessage(String.format(Messages.playerDeath, player.getName(), truncatedDeathMessage));
    }

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        Advancement advancement = event.getAdvancement();

        if(!Config.DISABLE_ADVANCEMENT_MESSAGE) {
            //Send message
            Bukkit.broadcastMessage(String.format(Messages.playerAdvancement, player.getName(), advancement.getDisplay().getTitle()));
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        if(player.isSneaking() && playerData.isPlayerOnChair(player)) {
            //Remove the arrow and the player
            playerData.removeChairByPlayer(player);
        }
    }
}
