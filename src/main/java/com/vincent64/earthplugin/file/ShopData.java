package com.vincent64.earthplugin.file;

import com.vincent64.earthplugin.log.Log;
import com.vincent64.earthplugin.shop.Shop;
import com.vincent64.earthplugin.shop.ShopType;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopData {
    private FileConfiguration configuration;
    private List<Shop> shopData;

    public ShopData(FileConfiguration configuration) {
        this.configuration = configuration;

        //Get shops data
        shopData = getShops();
    }

    public void save() {
        //Initialize string list of shops
        List<String> shopString = new ArrayList<>();

        //Loop through each shop
        for(Shop shop : shopData) {
            shopString.add(shop.toString());
        }

        //Save back into file
        configuration.set("shops", shopString);
        YamlLoader.saveYaml(configuration, "shops");
    }

    public List<Shop> getShops() {
        //Get shops as a list of string
        List<String> shopList = configuration.getStringList("shops");
        //Create list of shops
        List<Shop> shops = new ArrayList<>();

        //Loop through each element of the list
        for(String shopString : shopList) {
            //Get the components of the element
            String[] components = shopString.split(",");
            UUID seller = UUID.fromString(components[0]);
            ShopType type = ShopType.getType(components[1]);
            Material item = Material.matchMaterial(components[2]);
            int x = Integer.parseInt(components[3]);
            int y = Integer.parseInt(components[4]);
            int z = Integer.parseInt(components[5]);
            double price = Double.parseDouble(components[6]);

            //Create shop object
            Shop shop = new Shop(seller, type, item, x, y, z, price);
            //Add the shop to the list
            shops.add(shop);
        }

        return shops;
    }

    public Shop getShopAt(int x, int y, int z) {
        //Check through the list of shops
        for(Shop shop : shopData) {
            if(shop.getX() == x && shop.getY() == y && shop.getZ() == z) {
                return shop;
            }
        }

        return null;
    }

    public void addShop(Shop shop) {
        //Add shop to the list
        shopData.add(shop);
        Log.println("Shop added: " + shop.toString());
    }

    public void removeShop(int x, int y, int z) {
        //Get shop at coordinates
        Shop shop = getShopAt(x, y, z);
        //Remove it from the list
        shopData.remove(shop);
        Log.println("Shop removed: " + shop.toString());
    }
}
