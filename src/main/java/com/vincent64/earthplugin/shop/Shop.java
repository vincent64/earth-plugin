package com.vincent64.earthplugin.shop;

import org.bukkit.Material;

import java.util.UUID;

public class Shop {
    private UUID seller;
    private ShopType type;
    private Material item;
    private int x, y, z;
    private double price;

    public Shop(UUID seller, ShopType type, Material item, int x, int y, int z, double price) {
        this.seller = seller;
        this.type = type;
        this.item = item;
        this.x = x;
        this.y = y;
        this.z = z;
        this.price = price;
    }

    public UUID getSeller() {
        return seller;
    }

    public ShopType getType() {
        return type;
    }

    public Material getItem() {
        return item;
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

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return seller.toString() + "," + type.getName() + "," + item.name() + "," + x + "," + y + "," + z + "," + price;
    }
}
