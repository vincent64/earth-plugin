package com.vincent64.earthplugin.home;

public class Home {
    private String name;
    private int x, y, z;

    public Home(String name, int x, int y, int z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getName() {
        return name;
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

    @Override
    public String toString() {
        return name + "," + x + "," + y + "," + z;
    }
}
