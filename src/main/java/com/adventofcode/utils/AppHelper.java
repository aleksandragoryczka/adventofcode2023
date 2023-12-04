package com.adventofcode.utils;

public class AppHelper {
    public static String getAbsolutePathToInput(String[] args) {
        return System.getProperty("user.dir") + "\\src\\main\\resources\\day" + args[0] + "\\input" + args[1] + "_"
                + args[2] + ".txt";
    }
}
