package com.vincent64.earthplugin.util;

import java.util.regex.Pattern;

public class NameUtil {
    private static final Pattern namePattern =
            Pattern.compile("[a-zA-z0-9 -.]{1,48}");

    public static boolean isValidName(String name) {
        return namePattern.matcher(name).matches();
    }

    public static String getUserItemName(String itemName) {
        //Split item name at underscore
        String[] itemWords = itemName.split("_");
        String userItemName = "";
        //Loop through each word of the name
        for(String word : itemWords) {
            //Capitalize only the first letter of each word
            userItemName += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase() + " ";
        }
        //Remove last space character
        return userItemName.substring(0, userItemName.length() - 1);
    }
}
