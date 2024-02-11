package com.vincent64.earthplugin.util;

public class TimeUtil {
    public static String secondsToHMS(long totalSeconds) {
        //Calculate the hours, minutes and seconds from total seconds
        int hours = (int) (totalSeconds / 3600);
        int minutes = (int) ((totalSeconds % 3600) / 60);
        int seconds = (int) (totalSeconds % 60);

        //Return string according to the scale of time
        if(hours == 0) {
            if(minutes == 0) {
                return seconds + " seconds";
            } else {
                return minutes + " minutes and " + seconds + " seconds";
            }
        } else {
            return hours + " hours, " + minutes + " minutes and " + seconds + " seconds";
        }
    }
}
