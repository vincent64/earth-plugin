package com.vincent64.earthplugin.earth;

public enum Place {
    GREENLAND("Greenland", "in", 11_904, 1488),
    SVALBARD("Svalbard", "in", 16_896, 1008),
    QUEEN_ELIZABETH_ISLANDS("Queen Elizabeth Islands", "somewhere in", 7296, 1296),
    NORTHERN_LAND_ARCHIPELAGO("Northern Land Archipelago", "near the", 24_216, 1128),
    NEW_ZEMLYA("New Zemlya", "near", 20_208, 1776),
    BAFFIN_ISLAND("Baffin Island", "on", 9096, 2472),
    BERING_STRAIT("Bering Strait", "near the", 672, 3072),
    GREAT_BEAR_LAKE("Great Bear Lake", "somewhere around the", 5064, 3072),
    ICELAND("Iceland", "in", 13_800, 3216),
    LAPLAND("Lapland", "somewhere in", 17_400, 2712),
    KOLA_PENINSULA("Kola Peninsula", "on the", 18_528, 2880),
    PUTORANA_PLATEAU("Putorana Plateau", "in the region of the", 23_688, 2736),
    HUDSON_BAY("Hudson Bay", "along the coast of the", 8088, 3792),
    SCANDINAVIA("Scandinavia", "in", 16_416, 3576),
    FINNISH_LAKELAND("Finnish Lakeland", "somewhere in", 17_760, 3408),
    ALEUTIAN_ISLANDS("Aleutian Islands", "in the", 1584, 4416),
    ALASKA_RANGE("Alaska Range", "somewhere in", 3192, 3528),
    ROCKY_MOUNTAINS("Rocky Mountains", "in the", 4608, 4464),
    SCOTLAND("Scotland", "near", 14_976, 4176),
    JUTLAND_PENINSULA("Jutland Peninsula", "somewhere close to the", 16_128, 4344),
    URAL_MOUNTAINS("Ural Mountains", "along the", 20_304, 3864),
    WEST_SIBERIAN_PLAIN("West Siberian Plain", "in the", 21_576, 4056),
    CENTRAL_SIBERIAN_PLATEAU("Central Siberian Plateau", "somewhere in the", 23_544, 3936),
    EAST_SIBERIAN_PLAIN("East Siberian Plain", "in a random place in the", 25_656, 3360),
    KAMCHATKA_PENINSULA("Kamchatka Peninsula", "on the", 28_968, 4344),
    GREAT_PLAINS("Great Plains", "somewhere in the vast region of the", 6624, 5112),
    LAKE_SUPERIOR("Lake Superior", "somewhere along the", 7872, 5256),
    LAKE_HURON("Lake Huron", "on the coast of", 8328, 5568),
    SAINT_LAURANT_RIVER("Saint-Laurent River", "near", 9504, 5208),
    NEWFOUNDLAND_ISLAND("Newfoundland Island", "on", 10_560, 5184),
    GREAT_BRITAIN_ISLAND("Great Britain Island", "on the", 15_216, 4800),
    EAST_EUROPEAN_PLAIN("East European Plain", "somewhere in the", 18_864, 4272),
    WEST_EUROPEAN_PLAIN("West European Plain", "in an unknown location in the", 17_496, 4944),
    MASSIF_CENTRAL("Massif Central", "in the", 15_524, 5352),
    ALPS("Alps", "in the", 16_200, 5400),
    SEA_OF_AZOV("Sea of Azov", "somewhere along the coastline of the", 18_480, 5472),
    ARAL_SEA("Aral Sea", "somewhere near the", 20_472, 5592),
    LAKE_BALKHASH("Lake Balkhash", "close to the", 21_792, 5400),
    LAKE_BAIKAL("Lake Baikal", "near the", 24_552, 4656),
    ALTAI_MOUNTAINS("Altai Mountains", "in the", 22_824, 5160),
    MANCHURIAN_PLAIN("Manchurian Plain", "in the", 25_632, 5184),
    DEATH_VALLEY("Death Valley", "in the", 5496, 6384),
    APPALACHIAN_MOUNTAINS("Appalachian Mountains", "somewhere in the", 8496, 6288),
    NORTH_ATLANTIC_OCEAN("North Atlantic Ocean", "somewhere in the", 11_928, 6384),
    IBERIAN_PENINSULA("Iberian Peninsula", "on the", 14_976, 6048),
    APENNINE_PENINSULA("Apennine Peninsula", "somewhere on the", 16_464, 5856),
    SARDINIA("Sardinia", "in", 16_104, 6048),
    SICILIA("Sicilia", "in a random location in", 16_536, 6336),
    PELOPONNESE_PENINSULA("Peloponnese Peninsula", "in the region of the", 17_232, 6216),
    BOSPHORUS_STRAIT("Bosphorus Strait", "near the", 17_736, 6048),
    SOUTHERN_ANATOLIA("Southern Anatolia", "in", 18_432, 6360),
    CASPIAN_SEA("Caspian Sea", "somewhere on the shore of the", 19_680, 6048),
    ELBURZ_RANGE("Elburz Range", "somewhere in the", 19_704, 6504),
    PAMIR_MOUNTAINS("Pamir Mountains", "in the", 21_504, 6312),
    TIBETAN_PLATEAU("Tibetan Plateau", "somewhere in the", 23_184, 6696),
    GOBI_DESERT("Gobi Desert", "in the", 23_928, 5760),
    KOREAN_PENINSULA("Korean Peninsula", "on the", 26_160, 6264),
    JAPANESE_ARCHIPELAGO("Japanese Archipelago", "somewhere in the", 27_192, 6456),
    EASTERN_PACIFIC("Eastern Pacific", "somewhere in", 2016, 7968),
    GULF_OF_CALIFORNIA("Gulf of California", "along the", 5712, 7344),
    MEXICAN_PLATEAU("Mexican Plateau", "in the", 6504, 7128),
    FLORIDA_PENINSULA("Florida Peninsula", "in a random location on the", 8328, 7152),
    GULF_OF_MEXICO("Gulf of Mexico", "in a random location along the shore of the", 7680, 7536),
    YUCATAN_PENINSULA("Yucatan Peninsula", "on the", 7728, 8016),
    CARIBBEAN("Caribbean", "somewhere in the", 9048, 8016),
    ATLAS_MOUNTAINS("Atlas Mountains", "in the", 15_480, 6744),
    WESTERN_SAHARA_DESERT("Western Sahara Desert", "in the wild in the", 14_592, 7848),
    WESTERNMOST_POINT_OF_AFRICA("Westernmost Point of Africa", "close to the", 13_896, 8448),
    SOUTHERN_SAHARA_DESERT("Southern Sahara Desert", "in the", 16_560, 8520),
    EASTERN_SAHARA_DESERT("Eastern Sahara Desert", "somewhere in the", 17_544, 7488),
    NILE_DELTA("Nile delta", "in the region of the", 18_000, 6984),
    RED_SEA("Red Sea", "on the coast of the", 18_600, 7896),
    ARABIAN_PENINSULA("Arabian Peninsula", "somewhere on the", 19_368, 7704),
    HIMALAYA("Himalaya", "in the", 22_560, 7152),
    CHINA_PLAIN("China Plain", "in the", 24_816, 6936),
    GULF_OF_PANAMA("Gulf of Panama", "near the", 8592, 8904),
    ARABIAN_SEA("Arabian Sea", "on the coast of the", 20_736, 8448),
    DECCAN_PLATEAU("Deccan Plateau", "in the", 21_960, 8424),
    BAY_OF_BENGAL("Bay of Bengal", "somewhere on the shore of the", 23_040, 8496),
    SOUTH_CHINA_SEA("South China Sea", "in the region of the", 25_152, 5952),
    PHILIPPINE_SEA("Philippine Sea", "somewhere in the", 26_592, 8544),
    WESTERN_PACIFIC("Western Pacific", "in a random place in the", 28_920, 8280),
    NORTHERN_ANDES("Northern Andes", "in the", 8976, 10_824),
    AMAZONIAN_RAINFOREST("Amazonian Rainforest", "somewhere in the", 9864, 10_104),
    AMAZON_DELTA("Amazon Delta", "in the region of the", 11_040, 9672),
    EASTERNMOST_POINT_OF_SOUTH_AMERICA("Easternmost Point of South America", "close to the", 12_336, 10_296),
    GULF_OF_GUINEA("Gulf of Guinea", "along the coast of the", 15_552, 9744),
    SKELETON_COAST("Skeleton Coast", "somewhere along the", 16_392, 11_088),
    LAKE_VICTORIA("Lake Victoria", "in the region of", 18_168, 9792),
    LAKE_TANGANYIKA("Lake Tanganyika", "near the", 17_880, 10_176),
    LAKE_MALAWI("Lake Malawi", "somewhere around", 18_264, 10_704),
    EASTERN_AFRICA("Eastern Africa", "in", 19_200, 9144),
    CELEBES_SEA("Celebes Sea", "on an island in the", 25_752, 9456),
    JAVA_TRENCH("Java Trench", "somewhere along the", 24_288, 10_272),
    BRAZILIAN_HIGHLAND("Brazilian Highland", "somewhere in the", 11_400, 11_520),
    ANDES("Andes", "in the", 9360, 12_528),
    RIO_DE_LA_PLATA_BASIN("Rio De La Plata Basin", "in the region of the", 10_320, 12_696),
    SOUTHERN_ATLANTIC_OCEAN("Southern Atlantic Ocean", "somewhere in the", 14_040, 12_432),
    SOUTHERN_AFRICA("Southern Africa", "in", 17_352, 12_312),
    MADAGASCAR("Madagascar", "on the island of", 19_296, 11_424),
    INDIAN_OCEAN("Indian Ocean", "somewhere in the", 22_128, 11_184),
    WESTERN_AUSTRALIA("Western Australia", "near the tip of", 25_056, 11_712),
    NORTHERN_AUSTRALIA("Northern Australia", "in", 27_480, 10_680),
    GREAT_VICTORIA_DESERT("Great Victoria Desert", "somewhere in the", 26_208, 12_120),
    TASMANIA("Tasmania", "in", 27_864, 13_488),
    EASTERNMOST_POINT_OF_AUSTRALIA("Easternmost Point of Australia", "close to the", 28_416, 12_048),
    PATAGONIA("Patagonia", "somewhere in", 9360, 13_800),
    TIERRA_DEL_FUEGO("Tierra Del Fuego", "in", 9360, 14_784),
    AOTEAROA("Aotearoa", "on the island of", 30_072, 13_536);

    private final String name;
    private final String prefix;
    private final int x, z;

    //IMPORTANT:
    //map_x * 24 = world_x
    //max_z * 24 = world_z

    Place(String name, String prefix, int x, int z) {
        this.name = name;
        this.prefix = prefix;
        this.x = x;
        this.z = z;
    }

    public static Place getNearestPlace(int x, int z) {
        Place nearestPlace = GREENLAND;
        double previousDistance = distance(x, z, GREENLAND);

        //Iterate through every place
        for(Place place : values()) {
            //Calculate distance to this place
            double distance = distance(x, z, place);
            //Check if the distance is lower
            if(distance < previousDistance) {
                //Set place as nearest place
                nearestPlace = place;
                previousDistance = distance;
            }
        }

        return nearestPlace;
    }

    private static double distance(int x, int z, Place place) {
        return distance(x, z, place.getX(), place.getZ());
    }

    private static double distance(int x1, int z1, int x2, int z2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2));
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}
