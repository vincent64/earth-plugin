package com.vincent64.earthplugin.town;

import java.util.UUID;

public class Town {
    private String name;
    private UUID owner;
    private int x, y, z;

    public Town(String name, UUID owner, int x, int y, int z) {
        this.name = name;
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public UUID getOwner() {
        return owner;
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
        return name + "," + owner + "," + x + "," + y + "," + z;
    }
}
