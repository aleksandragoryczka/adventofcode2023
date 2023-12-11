package com.adventofcode.days;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.adventofcode.utils.ReaderUtil;

public class Day11 {

    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<Character>> newLines = doubleDotsColumn(doubleDotsRow(lines)).stream()
                .map(line -> line.chars().mapToObj(c -> (char) c).toList())
                .toList();
        List<List<Integer>> startCoordinateList = getStartIndexes(newLines);
        countDistances(startCoordinateList);

    }

    public static void task2(String filePath) {
    }

    private static int countDistances(List<List<Integer>> startCoordinateList) {
        int sum = 0;
        for (int i = 0; i < startCoordinateList.size(); i++) {
            for (int j = i + 1; j < startCoordinateList.size(); j++) {
                sum += Math.abs(startCoordinateList.get(i).get(0) - startCoordinateList.get(j).get(0))
                        + Math.abs(startCoordinateList.get(i).get(1) - startCoordinateList.get(j).get(1));
            }
        }
        System.out.println(sum);
        return sum;
    }

    private static List<List<Integer>> getStartIndexes(List<List<Character>> matrix) {
        List<List<Integer>> startCoordinateList = new ArrayList<List<Integer>>();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j) == '#') {
                    startCoordinateList.add(List.of(i, j));
                }
            }
        }
        return startCoordinateList;
    }

    private static List<String> doubleDotsRow(List<String> lines) {
        List<String> newLines = new ArrayList<String>();
        for (String line : lines) {
            if (line.chars().allMatch(c -> c == '.')) {
                newLines.add(line);
                newLines.add(line);
            } else {
                newLines.add(line);
            }
        }
        return newLines;
    }

    private static List<String> doubleDotsColumn(List<String> lines) {
        List<Integer> columnWithDotsIndex = new ArrayList<Integer>();
        for (AtomicInteger i = new AtomicInteger(0); i.get() < lines.get(0).length(); i.incrementAndGet()) {
            if (lines.stream().allMatch(line -> line.charAt(i.get()) == '.')) {
                columnWithDotsIndex.add(i.get());
            }
        }
        for (int i = columnWithDotsIndex.size() - 1; i >= 0; i--) {
            for (int j = 0; j < lines.size(); j++) {
                StringBuilder rowBuilder = new StringBuilder(lines.get(j));
                rowBuilder.insert(columnWithDotsIndex.get(i), ".");
                lines.set(j, rowBuilder.toString());
            }
        }
        return lines;
    }
}
