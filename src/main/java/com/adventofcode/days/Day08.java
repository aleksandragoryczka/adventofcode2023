package com.adventofcode.days;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.adventofcode.utils.ReaderUtil;

public class Day08 {
    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        String firstLineInstructions = lines.get(0);

        Map<String, List<String>> nodesMap = readToNodesMap(lines);

        String currentItemKey = "AAA";
        String targetItemKey = "ZZZ";
        int result = getSteps(firstLineInstructions, nodesMap, currentItemKey, targetItemKey);

        System.out.println(result);
    }

    public static void task2(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        String firstLineInstructions = lines.get(0);
        Map<String, List<String>> nodesMap = readToNodesMap(lines);

        List<String> currentItemKeysList = nodesMap.keySet().stream().filter(k -> k.endsWith("A"))
                .collect(Collectors.toList());

        List<Integer> results = currentItemKeysList.stream()
                .map(key -> getSteps(firstLineInstructions, nodesMap, key, "Z")).mapToInt(Integer::intValue).boxed()
                .toList();

        System.out.println(lcm(results));
    }

    private static Map<String, List<String>> readToNodesMap(List<String> lines) {
        Map<String, List<String>> nodesMap = new HashMap<>();

        List<String> linesSubList = lines.subList(2, lines.size());
        for (String line : linesSubList) {
            String[] lineSplitted = line.split(" = ");
            nodesMap.put(lineSplitted[0], Arrays.stream(lineSplitted[1].replaceAll("[()]", "").split(", ")).toList());
        }
        return nodesMap;
    }

    private static long lcm(List<Integer> numbersList) {
        long lcm = numbersList.get(0);
        for (int i = 1; i < numbersList.size(); i++) {
            lcm = lcmAlgorithm(lcm, numbersList.get(i));
        }
        return lcm;
    }

    private static long lcmAlgorithm(long a, long b) {
        return (a * b) / gcdAlgorithm(a, b);
    }

    private static long gcdAlgorithm(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcdAlgorithm(b, a % b);
    }

    private static int getSteps(String firstLineInstructions, Map<String, List<String>> nodesMap, String currentItemKey,
            String targetItemKey) {
        int stepsCounter = 0;
        String[] firstLineInstructionsSplitted = firstLineInstructions.split("");
        for (int i = 0; i < firstLineInstructionsSplitted.length; i++) {
            String c = firstLineInstructionsSplitted[i];
            stepsCounter++;
            if (c.equals("R")) {
                currentItemKey = nodesMap.get(currentItemKey).get(1);
            } else {
                currentItemKey = nodesMap.get(currentItemKey).get(0);
            }
            if (currentItemKey.endsWith(targetItemKey))
                break;
            if (i == firstLineInstructionsSplitted.length - 1) {
                i = -1;
            }
        }
        return stepsCounter;
    }
}
