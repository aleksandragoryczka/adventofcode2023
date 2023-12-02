package com.adventofcode.days;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adventofcode.utils.ReaderUtil;

public class Day02 {
    public static void task1(String filePath){
        String[] lines = ReaderUtil.readLineByLineToList(filePath).toArray(new String[0]);
        HashMap<String, Integer> maxCubesNumber = new HashMap<String, Integer>();
        maxCubesNumber.putAll(Map.of("red", 12, "green", 13, "blue", 14));
        int gamesSum = 0;
        
        outerLoop:
        for(String line: lines){
            String[] splittedLine = line.split("[;,]\\s*|(:\\s)");

            for(String cube : Arrays.copyOfRange(splittedLine, 1, splittedLine.length)){
                String[] splittedCube = cube.split(" ");
                if(Integer.parseInt(splittedCube[0]) > maxCubesNumber.get(splittedCube[1])){
                    continue outerLoop;
                }
            }
            gamesSum += Integer.parseInt(splittedLine[0].split(" ")[1]);
        }
        System.out.println(gamesSum);
    }

    public static void task2(String filePath){

    }
}
