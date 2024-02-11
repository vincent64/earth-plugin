package com.vincent64.earthplugin.util;

import com.vincent64.earthplugin.Config;
import org.bukkit.ChatColor;

public class Messages {
    public static String chatMessage =
            ChatColor.DARK_GREEN + "[%s] " + ChatColor.GREEN + "%s" + ChatColor.WHITE + ": %s";
    public static String localChatMessage =
            ChatColor.DARK_BLUE + "[%s] " + ChatColor.BLUE + "%s" + ChatColor.WHITE + ": %s";

    public static String playerJoin =
            ChatColor.DARK_GREEN + "%s" + ChatColor.YELLOW + " joined the server.";
    public static String playerWelcome =
            ChatColor.YELLOW + "Welcome " + ChatColor.DARK_GREEN + "%s" + ChatColor.YELLOW + " on the server!";
    public static String playerLeave =
            ChatColor.DARK_GREEN + "%s" + ChatColor.YELLOW + " left the server.";
    public static String playerDeath =
            ChatColor.RED + "☠ " + ChatColor.DARK_GREEN + "%s" + ChatColor.YELLOW + " %s";
    public static String playerAdvancement =
            ChatColor.DARK_BLUE + "» " + ChatColor.BLUE + "%s has completed the advancement " + ChatColor.GOLD + "%s" + ChatColor.BLUE + ".";
    public static String playerWorldBorder =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You have reached the world border and cannot go further.";
    public static String playerWelcomePrivate =
            ChatColor.GOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            ChatColor.YELLOW + "Welcome on " + ChatColor.GOLD + Config.SERVER_NAME + ChatColor.YELLOW + " !\n" +
            "Make sure to read the " + ChatColor.GREEN + "rules" + ChatColor.YELLOW + " on the Discord server before playing on the server.\n" +
            "If you need help, you can type " + ChatColor.GREEN + "/help" + ChatColor.YELLOW + " or ask on the server! " + ChatColor.GOLD + "Have fun!\n" +
            ChatColor.GOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";

    public static String rankUnlocked =
            ChatColor.DARK_BLUE + "» " + ChatColor.BLUE + "%s has unlocked the " + ChatColor.DARK_GREEN + "%s" + ChatColor.BLUE + " rank title !";
    public static String rankSuccess =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "You have successfully set your rank to %s.";
    public static String rankInvalidCommand =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Please enter a valid rank command.";
    public static String rankNotUnlocked =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You have not yet unlocked this rank.";
    public static String rankInvalidName =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "This rank name does not exist.";

    public static String localInvalidCommand =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Please enter a message.";
    public static String localNobodyNearby =
            ChatColor.GOLD + "» " + ChatColor.YELLOW + "There is nobody near you.";

    public static String moneyView =
            ChatColor.GOLD + "» " + ChatColor.YELLOW + "You have %s%s.";
    public static String moneyDaily =
            ChatColor.GOLD + "» " + ChatColor.YELLOW + "You have received your daily %s%s.";

    public static String transferSuccess =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "You have successfully transferred %s%s to %s !";
    public static String transferSuccessTarget =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "You have received %s%s from %s !";
    public static String transferNotEnoughMoney =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You don't have enough money.";
    public static String transferInvalidCommand =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Please enter a valid player name and amount.";
    public static String transferPlayerInexistant =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "This player does not exist.";
    public static String transferInvalidAmount =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Please enter a valid amount.";

    public static String shopCreated =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "Shop successfully created!";
    public static String shopRemoved =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "Shop removed!";
    public static String shopInvalidType =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Couldn't create a chest shop. Invalid type given. Type must be either \"buy\" or \"sell\".";
    public static String shopInvalidPrice =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Couldn't create a chest shop. Invalid price given. Price must be between 0%s and 9'999'999.99%s.";
    public static String shopInvalidItem =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Couldn't create a chest shop. Invalid item given.";
    public static String shopAccessDenied =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You cannot access a chest shop from another player.";
    public static String shopBreakAttempt =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You cannot break a chest shop from another player.";
    public static String shopAlreadyPresent =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "This chest is already a chest shop.";
    public static String shopBuy =
            ChatColor.GOLD + "» " + ChatColor.YELLOW + "Enter the amount of item you want to buy :\nType \"stop\" to cancel.";
    public static String shopSell =
            ChatColor.GOLD + "» " + ChatColor.YELLOW + "Enter the amount of item you want to sell :\nType \"stop\" to cancel.";
    public static String shopNotEnoughMoney =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You don't have enough money.";
    public static String shopInvalidQuantity =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Please enter a valid quantity between 1 and 256 (included).";
    public static String shopBuySuccess =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "You have bought %sx %s from this shop!";
    public static String shopNotEnoughQuantity =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "This shop does not have enough items.";
    public static String shopSellSuccess =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "You have sold %sx %s to this shop!";
    public static String shopNotEnoughQuantityPlayer =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You do not have enough items.";
    public static String shopNotEnoughRoom =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "This shop does not have enough room for the items. Try lowering the amount you want to sell.";
    public static String shopNotEnoughRoomPlayer =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You don't have enough room in your inventory for the items.";
    public static String shopNotEnoughMoneySeller =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "The seller does not have enough money.";
    public static String shopCancelled =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "Transaction cancelled.";
    public static String shopSignNotAllowed =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You are not allowed to place a sign on a chest shop.";
    public static String shopHopperNotAllowed =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You are not allowed to place a hopper under a chest shop.";

    public static String regionTeleported =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "You have been teleported somewhere in %s!";
    public static String regionDenied =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You can reuse this command again in %s.";
    public static String regionInvalidCommand =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Please enter a valid destination.";
    public static String regionSugarCane =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Sugar cane can only grow near the equator.";
    public static String regionSweetBerries =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Sweet berries can only grow near the poles.";
    public static String regionCocoaPlant =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Cocoa plants can only grow near the equator.";

    public static String crateCreated =
            ChatColor.GOLD + "» " + ChatColor.YELLOW + "A new mystery crate has dropped %s! Find it to get rare and valuable loot! It will disappear in %s.";
    public static String crateFound =
            ChatColor.GOLD + "» " + ChatColor.YELLOW + "The mystery crate was found by %s !";
    public static String crateFirst =
            ChatColor.GOLD + "» " + ChatColor.YELLOW + "You were the first player to find the crate and have received %s.";

    public static String homeTeleported =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "You have been teleported to %s !";
    public static String homeHomeInexistant =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "This home does not exist.";
    public static String homeInvalidCommand =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Please enter a valid home command.";
    public static String homeInvalidName =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Please enter a valid home name.";
    public static String homeCreated =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "Home successfully created!";
    public static String homeDeleted =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "Home deleted!";
    public static String homeNameUsed =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You already have a home with this name.";
    public static String homeLimitReached =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You have reached your home limit and cannot create a new one.";

    public static String townTeleported =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "You have been teleported to %s !";
    public static String townTownInexistant =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "This town does not exist.";
    public static String townInvalidCommand =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Please enter a valid town command.";
    public static String townInvalidName =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "Please enter a valid town name.";
    public static String townCreated =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "Town successfully created!";
    public static String townDeleted =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "Town deleted!";
    public static String townCreatedPublic =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "%s has created a new town called %s !";
    public static String townDeletedPublic =
            ChatColor.DARK_GREEN + "» " + ChatColor.GREEN + "The town %s has been deleted.";
    public static String townDeleteAttempt =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You can delete only your own towns.";
    public static String townDenied =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You can create a new town again in %s.";
    public static String townNameUsed =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "There is already a town with this name.";
    public static String townNotEnoughMoney =
            ChatColor.DARK_RED + "» " + ChatColor.RED + "You don't have enough money to create a town. Money required: %s%s.";

    public static String homeListTop =
            ChatColor.DARK_GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━ " + ChatColor.GREEN + "Home list (%s)" + ChatColor.DARK_GREEN + " ━━━━━━━━━━━━━━━━━━━━━━━━\n";
    public static String homeListBottom =
            ChatColor.GREEN + "\nHomes limit: %s (%s left)\n" +
            ChatColor.DARK_GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
    public static String townListTop =
            ChatColor.DARK_GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━ " + ChatColor.GREEN + "Town list (%s)" + ChatColor.DARK_GREEN + " ━━━━━━━━━━━━━━━━━━━━━━━━\n";
    public static String townListBottom =
            ChatColor.DARK_GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
    public static String rankListTop =
            ChatColor.DARK_GREEN + "━━━━━━━━━━━━━━━━━━━━━━━ " + ChatColor.GREEN + "Your ranks (%s)" + ChatColor.DARK_GREEN + " ━━━━━━━━━━━━━━━━━━━━━━━\n";
    public static String rankListBottom =
            ChatColor.DARK_GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";

    public static String help =
            ChatColor.DARK_GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            ChatColor.GOLD + "Help: Commands list\n" +
            ChatColor.YELLOW + "For more informations about each commands, please refer to\nthe plugin page at: https://github.com/vincent64/earth-plugin.\n" +
            ChatColor.DARK_GREEN + "/money" + ChatColor.GREEN + " - View how much money you have.\n" +
            ChatColor.DARK_GREEN + "/pay [playerName] [amount]" + ChatColor.GREEN + " - Send money to another player.\n" +
            ChatColor.DARK_GREEN + "/region [region]" + ChatColor.GREEN + " - Teleport to a region/continent in the world.\n" +
            ChatColor.DARK_GREEN + "/l [message]" + ChatColor.GREEN + " - Send a message to players nearby.\n" +
            ChatColor.DARK_GREEN + "/home" + ChatColor.GREEN + " - View the list of your homes.\n" +
            ChatColor.DARK_GREEN + "/home [homeName]" + ChatColor.GREEN + " - Teleport to the home.\n" +
            ChatColor.DARK_GREEN + "/home [create/delete] [homeName]" + ChatColor.GREEN + " - Create/delete a home.\n" +
            ChatColor.DARK_GREEN + "/town" + ChatColor.GREEN + " - View the list of every town in the world.\n" +
            ChatColor.DARK_GREEN + "/town [townName]" + ChatColor.GREEN + " - Teleport to the town.\n" +
            ChatColor.DARK_GREEN + "/town [create/delete] [townName]" + ChatColor.GREEN + " - Create/delete a town.\n" +
            ChatColor.DARK_GREEN + "/rank" + ChatColor.GREEN + " - View the list of your ranks.\n" +
            ChatColor.DARK_GREEN + "/rank set [rankId]" + ChatColor.GREEN + " - Set your current rank.\n" +
            ChatColor.DARK_GREEN + "/help" + ChatColor.GREEN + " - Display this message.\n" +
            ChatColor.DARK_GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
}
