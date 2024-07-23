package com.vincent64.earthplugin.earth;

import org.bukkit.Location;

public enum Region {
    EUROPE("Europe", "europe", 16_608, 5112),
    NORTH_AMERICA("North America", "north-america", 6912, 5352),
    SOUTH_AMERICA("South America", "south-america", 10_632, 10_848),
    ASIA("Asia", "asia", 23_760, 6744),
    AFRICA("Africa", "africa", 17_352, 9120);

    public static final int EQUATOR = 9696;
    public static final int EQUATOR_WIDTH = 2048;
    public static final int POLES_START = 4500;
    private final String name;
    private final String id;
    private final int x, z;

    Region(String name, String id, int x, int z) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.z = z;
    }

    public static Region getRegion(String id) {
        for(Region region : values()) {
            if(region.id.equals(id)) {
                return region;
            }
        }
        return null;
    }

    public static boolean isInNorthPole(int z) {
        return z < EQUATOR - POLES_START;
    }

    public static boolean isInSouthPole(int z) {
        return z > EQUATOR + POLES_START;
    }

    public static boolean isNearEquator(int z) {
        return z > EQUATOR - EQUATOR_WIDTH && z < EQUATOR + EQUATOR_WIDTH;
    }

    public static boolean hasCrossedEquator(Location start, Location end) {
        return (start.getZ() >= EQUATOR && end.getZ() < EQUATOR)
                || (start.getZ() <= EQUATOR && end.getZ() > EQUATOR);
    }

    public static boolean hasCrossedArcticCircle(Location start, Location end) {
        return (start.getZ() >= EQUATOR - POLES_START && end.getZ() < EQUATOR - POLES_START)
                || (start.getZ() <= EQUATOR - POLES_START && end.getZ() > EQUATOR - POLES_START);
    }

    public static boolean hasCrossedAntarcticCircle(Location start, Location end) {
        return (start.getZ() >= EQUATOR + POLES_START && end.getZ() < EQUATOR + POLES_START)
                || (start.getZ() <= EQUATOR + POLES_START && end.getZ() > EQUATOR + POLES_START);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}
