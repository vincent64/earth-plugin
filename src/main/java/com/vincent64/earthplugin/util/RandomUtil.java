package com.vincent64.earthplugin.util;

import java.util.Random;

public class RandomUtil {
    public static int getRandomInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static float getRandomFloat() {
        return new Random().nextFloat();
    }
}
