package com.adventofcode.days;

import java.util.List;

import com.adventofcode.utils.ReaderUtil;

public class Day15 {

    public static void task1(String filePath) {
        String[] lines = ReaderUtil.readLineByLineToList(filePath).get(0).split(",");
        long sum = 0;
        for (int i = 0; i < lines.length; i++) {
            sum += getCodeValue(lines[i]);
        }
        System.out.println(sum);
    }

    public static void task2(String filePath) {
    }

    private static long getCodeValue(String line) {
        long currentValue = 0;
        for (int j = 0; j < line.length(); j++) {
            currentValue += (int) line.charAt(j);
            currentValue *= 17;
            currentValue = currentValue % 256;
        }
        return currentValue;
    }
}
