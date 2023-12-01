package com.adventofcode.day1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import com.adventofcode.utils.ReaderUtil;

public class Day1 {
    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLinyByLineToList(filePath);
        List<String> cleandLines = lines.stream().map(line -> ((String) line).replaceAll("[^0-9]", "")).toList();
        int calibrationValuesSum = cleandLines.stream().map(line -> String.valueOf(line.charAt(0)) + String.valueOf(line.charAt(line.length() - 1))).mapToInt(Integer::parseInt).sum();
        System.out.println(calibrationValuesSum);
    }

    public static void task2(){
        System.out.println("task2");
    }


}
