package com.adventofcode.days;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.adventofcode.utils.ReaderUtil;

public class Day02 {
    public static void task1(String filePath) {
        String[] lines = ReaderUtil.readLineByLineToList(filePath).toArray(new String[0]);
        HashMap<String, Integer> maxCubesNumber = new HashMap<String, Integer>();
        maxCubesNumber.putAll(Map.of("red", 12, "green", 13, "blue", 14));
        int gamesSum = 0;

        outerLoop: for (String line : lines) {
            String[] splittedLine = line.split("[;,]\\s*|(:\\s)");

            for (String cube : Arrays.copyOfRange(splittedLine, 1, splittedLine.length)) {
                String[] splittedCube = cube.split(" ");
                if (Integer.parseInt(splittedCube[0]) > maxCubesNumber.get(splittedCube[1])) {
                    continue outerLoop;
                }
            }
            gamesSum += Integer.parseInt(splittedLine[0].split(" ")[1]);
        }
        System.out.println(gamesSum);
    }

    public static void task2(String filePath) {
        String[] lines = ReaderUtil.readLineByLineToList(filePath).toArray(new String[0]);
        int gamesSum = 0;
        for (String line : lines) {
            String[] splittedLine = line.split("[;,]\\s*|(:\\s)");
            HashMap<String, Integer> maxCubesInGame = new HashMap<String, Integer>();
            for (String cube : Arrays.copyOfRange(splittedLine, 1, splittedLine.length)) {
                String[] splittedCube = cube.split(" ");

                if (maxCubesInGame.containsKey(splittedCube[1])) {
                    if (maxCubesInGame.get(splittedCube[1]) < Integer.parseInt(splittedCube[0])) {
                        maxCubesInGame.put(splittedCube[1], Integer.parseInt(splittedCube[0]));
                    }
                } else {
                    maxCubesInGame.put(splittedCube[1], Integer.parseInt(splittedCube[0]));
                }
            }
            gamesSum += maxCubesInGame.values().stream().reduce(1, (a, b) -> a * b);
        }
        System.out.println(gamesSum);
    }
}
