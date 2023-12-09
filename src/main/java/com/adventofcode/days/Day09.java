package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.adventofcode.utils.ReaderUtil;

public class Day09 {
    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<Integer>> report = lines.stream()
                .map(line -> Arrays.stream(line.split(" ")).map(Integer::parseInt).toList()).toList();
        List<Integer> extrapolatedValuesList = new ArrayList<Integer>();
        for (List<Integer> reportLine : report) {
            List<List<Integer>> extrapolationHistoryList = new ArrayList<List<Integer>>();
            extrapolationHistoryList.add(reportLine);
            List<Integer> newLine = reportLine;
            while (!newLine.stream().allMatch(value -> value == 0)) {
                newLine = addSequence(newLine);
                extrapolationHistoryList.add(newLine);
            }
            extrapolatedValuesList.add(extrapolate(extrapolationHistoryList));
        }
        System.out.println(extrapolatedValuesList.stream().mapToInt(Integer::intValue).sum());
    }

    private static List<Integer> addSequence(List<Integer> currentLine) {
        List<Integer> newLine = new ArrayList<Integer>();
        for (int i = 1; i < currentLine.size(); i++) {
            newLine.add(currentLine.get(i) - currentLine.get(i - 1));
        }
        return newLine;
    }

    private static int extrapolate(List<List<Integer>> extrapolationHistoryList) {
        extrapolationHistoryList.get(extrapolationHistoryList.size() - 1).add(0);
        for (int i = extrapolationHistoryList.size() - 1; i > 1; i--) {
            int lowerValue = extrapolationHistoryList.get(i).get(extrapolationHistoryList.get(i).size() - 1);
            int upperValue = extrapolationHistoryList.get(i - 1).get(extrapolationHistoryList.get(i - 1).size() - 1);
            extrapolationHistoryList.get(i - 1).add(upperValue + lowerValue);
        }
        return extrapolationHistoryList.get(0).get(extrapolationHistoryList.get(0).size() - 1) + extrapolationHistoryList.get(1).get(extrapolationHistoryList.get(1).size() - 1);

    }

    public static void task2(String filePath) {
        
    }
}
