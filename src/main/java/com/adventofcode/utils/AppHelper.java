package com.adventofcode.utils;

public class AppHelper {
    public static String getAbsolutePathToInput(String[] args) {
        return System.getProperty("user.dir") + "\\src\\main\\java\\com\\adventofcode\\day" + args[0] + "\\input" + args[2] + ".txt";
    }
}
