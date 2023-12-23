package com.adventofcode.days;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.adventofcode.utils.ReaderUtil;

public class Day13 {
    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<List<Character>>> patterns = readPatterns(lines);
        long sum = 0;
        for (int i = 0; i < patterns.size(); i++) {
            for (int j = 0; j < patterns.get(i).size() - 1; j++) {
                if (checkIfRowsEqual(patterns.get(i).get(j), patterns.get(i).get(j + 1))) {
                    sum += checkReflectionForNextRows(patterns.get(i), j) * 100;
                }
            }

            for (int k = 0; k < patterns.get(i).get(0).size() - 1; k++) {
                List<Character> column1 = new ArrayList<Character>();
                List<Character> column2 = new ArrayList<Character>();
                for (int j = 0; j < patterns.get(i).size(); j++) {
                    column1.add(patterns.get(i).get(j).get(k));
                    column2.add(patterns.get(i).get(j).get(k + 1));
                }
                if (checkIfRowsEqual(column1, column2)) {
                    sum += checkReflectionForNextColumns(patterns.get(i), k);
                }
            }
        }
        System.out.println(sum);
    }

    public static void task2(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<List<Character>>> rowPatterns = readPatterns(lines);
        List<List<List<Character>>> columnPatterns = readPatternsByColumns(lines);

        long sum = 0;
        for (int i = 0; i < rowPatterns.size(); i++) {
            int foundIndex = checkIfOneSmudge(rowPatterns.get(i));
            if (foundIndex > 0) {
                sum += foundIndex * 100;
            } else {
                foundIndex = checkIfOneSmudge(columnPatterns.get(i));
                sum += foundIndex;
            }
        }
        System.out.println(sum);
    }

    private static int countNotMatchedDigits(List<Character> row1, List<Character> row2) {
        int countNotMatchedDigits = 0;
        for (int i = 0; i < row1.size(); i++) {
            if (!row1.get(i).equals(row2.get(i))) {
                countNotMatchedDigits++;
            }
        }
        return countNotMatchedDigits;
    }

    private static int checkIfOneSmudge(List<List<Character>> pattern) {
        for (AtomicInteger j = new AtomicInteger(0); j.get() < pattern.size() - 1; j
                .getAndIncrement()) {
            List<List<Character>> firstPart = pattern.subList(0, j.get() + 1);

            List<List<Character>> secondPart = pattern.subList(j.get() + 1,
                    j.get() + firstPart.size() + 1 > pattern.size() ? pattern.size() : j.get() + firstPart.size() + 1);
            int differentSignsCount = 0;
            for (int k = 0; k < secondPart.size(); k++) {
                differentSignsCount += countNotMatchedDigits(secondPart.get(k),
                        firstPart.get(firstPart.size() - 1 - k));
            }
            if (differentSignsCount == 1) {
                return (j.get() + 1);
            }
        }
        return 0;
    }

    private static int checkReflectionForNextColumns(List<List<Character>> pattern, int startColumnIndex) {
        int loopCounter = 1;
        while (startColumnIndex - loopCounter >= 0 && startColumnIndex + 1 + loopCounter < pattern.get(0).size()) {
            List<Character> column1 = new ArrayList<Character>();
            List<Character> column2 = new ArrayList<Character>();
            for (int j = 0; j < pattern.size(); j++) {
                column1.add(pattern.get(j).get(startColumnIndex - loopCounter));
                column2.add(pattern.get(j).get(startColumnIndex + 1 + loopCounter));
            }
            if (!checkIfRowsEqual(column1, column2)) {
                return 0;
            }
            loopCounter++;
        }
        return startColumnIndex + 1;
    }

    private static List<List<List<Character>>> readPatternsByColumns(List<String> lines) {
        List<List<List<Character>>> patterns = new ArrayList<List<List<Character>>>();
        List<List<Character>> currentPattern = new ArrayList<List<Character>>();

        for (int i = 0; i < lines.size(); i++) {
            if (!lines.get(i).isEmpty()) {
                for (int j = 0; j < lines.get(i).length(); j++) {

                    if (currentPattern.size() <= j) {
                        List<Character> newColumn = new ArrayList<>();
                        newColumn.add(lines.get(i).charAt(j));
                        currentPattern.add(j, newColumn);
                    } else {
                        List<Character> existingColumn = currentPattern.get(j);
                        existingColumn.add(lines.get(i).charAt(j));
                        currentPattern.set(j, existingColumn);
                    }
                }
            } else {
                patterns.add(new ArrayList<List<Character>>(currentPattern));
                currentPattern = new ArrayList<List<Character>>();
            }
        }
        if (!currentPattern.isEmpty()) {
            patterns.add(new ArrayList<List<Character>>(currentPattern));
        }
        return patterns;
    }

    private static int checkReflectionForNextRows(List<List<Character>> pattern, int startRowIndex) {
        int loopCounter = 1;
        while (startRowIndex - loopCounter >= 0 && startRowIndex + 1 + loopCounter < pattern.size()) {
            if (!checkIfRowsEqual(pattern.get(startRowIndex - loopCounter),
                    pattern.get(startRowIndex + 1 + loopCounter))) {
                return 0;
            }
            loopCounter++;
        }
        return startRowIndex + 1;
    }

    private static boolean checkIfRowsEqual(List<Character> row1, List<Character> row2) {
        for (int i = 0; i < row1.size(); i++) {
            if (!row1.get(i).equals(row2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<List<List<Character>>> readPatterns(List<String> lines) {
        List<List<List<Character>>> patterns = new ArrayList<List<List<Character>>>();
        List<List<Character>> currentPattern = new ArrayList<List<Character>>();

        for (String line : lines) {
            if (line.isEmpty()) {
                patterns.add(new ArrayList<>(currentPattern));
                currentPattern.clear();
            } else {
                List<Character> patternRow = parsePatternRow(line);
                currentPattern.add(patternRow);
            }
        }
        if (!currentPattern.isEmpty()) {
            patterns.add(currentPattern);
        }
        return patterns;
    }

    private static List<Character> parsePatternRow(String line) {
        List<Character> patternRow = new ArrayList<Character>();

        for (char c : line.toCharArray()) {
            patternRow.add(c);
        }
        return patternRow;
    }
}
