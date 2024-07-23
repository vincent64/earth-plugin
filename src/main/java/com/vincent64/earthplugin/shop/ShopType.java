package com.vincent64.earthplugin.shop;

public enum ShopType {
    SELL("sell"),
    BUY("buy");

    private final String name;

    ShopType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ShopType getType(String name) {
        return (name.equals("buy")) ? BUY : SELL;
    }
}
