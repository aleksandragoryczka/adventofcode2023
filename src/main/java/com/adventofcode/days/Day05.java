package com.adventofcode.days;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.adventofcode.utils.ReaderUtil;

public class Day05 {
    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<Long> firstLineSeeds = Arrays.stream(extractNumbers(lines.get(0))).mapToLong(Long::parseLong).boxed()
                .toList();
        List<List<List<Long>>> convertNumbersRules = parseDataLines(lines.subList(3, lines.size()));

        Queue<HashMap<List<Long>, Long>> sourceDestinationMappingQueue = new LinkedList<HashMap<List<Long>, Long>>();
        for (List<List<Long>> section : convertNumbersRules) {
            HashMap<List<Long>, Long> sourceDestinationMap = new HashMap<>();
            for (List<Long> rule : section) {
                sourceDestinationMap.put(Arrays.asList(rule.get(1), rule.get(1) + rule.get(2) - 1), rule.get(0));
            }
            sourceDestinationMappingQueue.add(sourceDestinationMap);
        }
        while (!sourceDestinationMappingQueue.isEmpty()) {
            HashMap<List<Long>, Long> poppedMapping = sourceDestinationMappingQueue.poll();
            firstLineSeeds = firstLineSeeds.stream().map(num -> getMappingForNumber(num, poppedMapping)).toList();
        }
        System.out.println(firstLineSeeds.stream().min(Long::compareTo).get());
    }

    public static void task2(String filePath) {
    }

    private static Long getMappingForNumber(Long num, HashMap<List<Long>, Long> mapping) {
        Set<List<Long>> mapKeys = mapping.keySet();
        for (List<Long> key : mapKeys) {
            if (num >= key.get(0) && num <= key.get(1)) {
                return mapping.get(key) + num - key.get(0);
            }
        }
        return num;
    }

    private static String[] extractNumbers(String line) {
        return line.replaceAll("[^0-9 ]", "").trim().split("\\s+");
    }

    private static List<List<List<Long>>> parseDataLines(List<String> lines) {
        List<List<List<Long>>> result = new ArrayList<>();
        List<List<Long>> currentSection = new ArrayList<>();
        for (String line : lines) {
            String[] numbers = extractNumbers(line);
            if (numbers.length > 0 && !numbers[0].equals("")) {
                currentSection.add(Arrays.stream(numbers).map(Long::parseLong).toList());
            } else {
                if (currentSection.size() > 0) {
                    result.add(new ArrayList<List<Long>>(currentSection));
                    currentSection.clear();
                }
            }
        }
        result.add(new ArrayList<List<Long>>(currentSection));
        return result;
    }
}
