package com.adventofcode.days;

import java.util.ArrayList;
import java.util.List;

import com.adventofcode.utils.ReaderUtil;

public class Day13 {
    private static class UpdatedField {
        int finaldIndex;
        List<List<Character>> pattern;

        public UpdatedField(int finaldIndex, List<List<Character>> pattern) {
            this.finaldIndex = finaldIndex;
            this.pattern = pattern;
        }
    }

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
        List<List<List<Character>>> patterns = readPatterns(lines);
        long sum = 0;
        for (int i = 0; i < patterns.size(); i++) {
            for (int j = 0; j < patterns.get(i).size() - 1; j++) {
                if (checkIfRowsEqual(patterns.get(i).get(j), patterns.get(i).get(j + 1))) {
                    UpdatedField updatedField = updatePatternCheckReflectionForNextRows(patterns.get(i), j);
                    sum += updatedField.finaldIndex * 100;
                    patterns.set(i, updatedField.pattern);
                }
            }
            System.out.println(patterns.get(i));
            System.out.println(sum);

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

    private static List<Character> tryToUpdatePatternByRows(List<Character> row1, List<Character> row2) {
        int erorrCounter = 0;
        for (int i = 0; i < row1.size(); i++) {
            if (!row1.get(i).equals(row2.get(i))) {
                erorrCounter++;
            }
        }
        if (erorrCounter == 1) {
            return row1;
            // System.out.println(erorrCounter);
            // System.out.println(row1);
        }
        return null;
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

    private static UpdatedField updatePatternCheckReflectionForNextRows(List<List<Character>> pattern,
            int startRowIndex) {
        int loopCounter = 1;
        int errorCounter = 0;
        // int updatedIndex = -1;
        List<List<Character>> initialPattern = new ArrayList<>(pattern);
        while (startRowIndex - loopCounter >= 0 && startRowIndex + 1 + loopCounter < pattern.size()) {
            if (!checkIfRowsEqual(pattern.get(startRowIndex - loopCounter),
                    pattern.get(startRowIndex + 1 + loopCounter)) && errorCounter == 0) {
                List<Character> newRow = tryToUpdatePatternByRows(pattern.get(startRowIndex - loopCounter),
                        pattern.get(startRowIndex + 1 + loopCounter));
                if (newRow != null) {
                    pattern.set(startRowIndex + 1 + loopCounter, newRow);
                    // updatedIndex
                    errorCounter++;
                } else {
                    return new UpdatedField(0, initialPattern);
                }
            }
            loopCounter++;
        }
        return new UpdatedField(startRowIndex + 1, pattern);
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
