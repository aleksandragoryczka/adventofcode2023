package com.adventofcode.days;

import java.util.ArrayList;
import java.util.List;

import com.adventofcode.utils.ReaderUtil;

public class Day14 {

    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<Character>> columns = parseByColumns(lines);

        columns = moveNorth(columns);

        System.out.println(countRoundedRocks(columns));
    }

    public static void task2(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<Character>> columns = parseByColumns(lines);
        List<String> cache = new ArrayList<>();
        while (true) {
            columns = doCycle(columns);
            String cachedString = generateColumnsString(columns);
            if (cache.contains(cachedString)) {
                int indexOfCachedString = cache.indexOf(cachedString);
                int currentIndex = cache.size();
                int cycleLength = currentIndex - indexOfCachedString;
                long extraCycles = (1000000000 - 1 - indexOfCachedString) % cycleLength;

                for (int i = 0; i < extraCycles; i++) {
                    columns = doCycle(columns);
                }
                System.out.println(countRoundedRocks(columns));
                return;
            }
            cache.add(cachedString);
        }
    }

    private static String generateColumnsString(List<List<Character>> columns) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            for (int j = 0; j < columns.get(i).size(); j++) {
                sb.append(columns.get(i).get(j));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static List<List<Character>> doCycle(List<List<Character>> columns) {
        columns = moveNorth(columns);
        columns = moveWest(columns);
        columns = moveSouth(columns);
        columns = moveEast(columns);
        return columns;
    }

    private static List<List<Character>> moveEast(List<List<Character>> columns) {
        List<List<Character>> rows = mapFromColumnsToRows(columns);
        for (int i = 0; i < rows.size(); i++) {
            for (int j = rows.get(i).size() - 1; j >= 0; j--) {
                char c = rows.get(i).get(j);
                if (c == 'O') {
                    int index = getNextIndexForRoundedRock(rows.get(i), j, 1, rows.get(i).size() - 1);
                    rows.get(i).set(j, '.');
                    rows.get(i).set(index, 'O');
                }
            }
        }
        rows = mapFromColumnsToRows(rows);
        return rows;
    }

    private static List<List<Character>> moveSouth(List<List<Character>> columns) {
        for (int i = 0; i < columns.size(); i++) {
            for (int j = columns.get(i).size() - 1; j >= 0; j--) {
                char c = columns.get(i).get(j);
                if (c == 'O') {
                    int index = getNextIndexForRoundedRock(columns.get(i), j, 1, columns.get(i).size() - 1);
                    columns.get(i).set(j, '.');
                    columns.get(i).set(index, 'O');
                }
            }
        }
        return columns;
    }

    private static List<List<Character>> moveWest(List<List<Character>> columns) {
        List<List<Character>> rows = mapFromColumnsToRows(columns);
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).size(); j++) {
                char c = rows.get(i).get(j);
                if (c == 'O') {
                    int index = getNextIndexForRoundedRock(rows.get(i), j, -1, 0);
                    rows.get(i).set(j, '.');
                    rows.get(i).set(index, 'O');
                }
            }
        }
        rows = mapFromColumnsToRows(rows);
        return rows;
    }

    private static List<List<Character>> mapFromColumnsToRows(List<List<Character>> columns) {
        List<List<Character>> rows = new ArrayList<List<Character>>();
        for (List<Character> column : columns) {
            for (int i = 0; i < column.size(); i++) {
                char c = column.get(i);
                if (rows.size() <= i) {
                    rows.add(new ArrayList<Character>(List.of(c)));
                } else {
                    List<Character> existingRow = rows.get(i);
                    existingRow.add(c);
                    rows.set(i, existingRow);
                }
            }
        }
        return rows;
    }

    private static List<List<Character>> moveNorth(List<List<Character>> columns) {
        for (int i = 0; i < columns.size(); i++) {
            for (int j = 0; j < columns.get(i).size(); j++) {
                char c = columns.get(i).get(j);
                if (c == 'O') {
                    int index = getNextIndexForRoundedRock(columns.get(i), j, -1, 0);
                    columns.get(i).set(j, '.');
                    columns.get(i).set(index, 'O');
                }
            }
        }
        return columns;
    }

    private static long countRoundedRocks(List<List<Character>> columns) {
        long sum = 0;
        for (int i = 0; i < columns.size(); i++) {
            for (int j = 0; j < columns.get(i).size(); j++) {
                if (columns.get(i).get(j) == 'O') {
                    sum += columns.size() - j;
                }
            }
        }
        return sum;
    }

    private static int getNextIndexForRoundedRock(List<Character> column, int roundedRockIndex, int factor,
            int border) {
        if (roundedRockIndex == border)
            return border;
        if (column.get(roundedRockIndex + factor) == '#')
            return roundedRockIndex;
        if (column.get(roundedRockIndex + factor) == '.')
            return getNextIndexForRoundedRock(column, roundedRockIndex + factor, factor, border);
        if (column.get(roundedRockIndex + factor) == 'O')
            return roundedRockIndex;
        return -1;
    }

    private static List<List<Character>> parseByColumns(List<String> lines) {
        List<List<Character>> columns = new ArrayList<List<Character>>();

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {

                if (columns.size() <= j) {
                    List<Character> newColumn = new ArrayList<>();
                    newColumn.add(lines.get(i).charAt(j));
                    columns.add(j, newColumn);
                } else {
                    List<Character> existingColumn = columns.get(j);
                    existingColumn.add(lines.get(i).charAt(j));
                    columns.set(j, existingColumn);
                }
            }
        }
        return columns;
    }
}
