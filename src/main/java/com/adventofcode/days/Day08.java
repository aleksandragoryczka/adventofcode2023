package com.adventofcode.days;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.adventofcode.utils.ReaderUtil;

public class Day08 {
    private static final String Map = null;

    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        String firstLineInstructions = lines.get(0);
        Map<String, List<String>> nodesMap = new HashMap<>();
        String currentItemKey = "AAA";
        String targetItemKey = "ZZZ";
        List<String> linesSubList = lines.subList(2, lines.size());
        for (String line : linesSubList) {
            String[] lineSplitted = line.split(" = ");
            nodesMap.put(lineSplitted[0], Arrays.stream(lineSplitted[1].replaceAll("[()]", "").split(", ")).toList());
        }

        String[] firstLineInstructionsSplitted = firstLineInstructions.split("");
        int stepsCounter = 0;
        for (int i = 0; i < firstLineInstructionsSplitted.length; i++) {
            String c = firstLineInstructionsSplitted[i];
            stepsCounter++;
            if (c.equals("R")) {
                currentItemKey = nodesMap.get(currentItemKey).get(1);
            } else {
                currentItemKey = nodesMap.get(currentItemKey).get(0);
            }
            if (currentItemKey.equals(targetItemKey))
                break;
            if (i == firstLineInstructionsSplitted.length - 1) {
                i = -1;
            }
        }
        System.out.println(stepsCounter);
    }

    public static void task2(String filePath) {

    }
}
