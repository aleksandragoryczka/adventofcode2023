package com.adventofcode.days;

import java.util.ArrayList;
import java.util.List;

import com.adventofcode.utils.ReaderUtil;

public class Day14 {

    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<Character>> columns = parseByColumns(lines);

        for (int i = 0; i < columns.size(); i++) {
            for (int j = 0; j < columns.get(i).size(); j++) {
                char c = columns.get(i).get(j);
                if (c == 'O') {
                    int index = getNextIndexForRoundedRock(columns.get(i), j);
                    columns.get(i).set(j, '.');
                    columns.get(i).set(index, 'O');
                }
            }
        }

        System.out.println(countRoundedRocks(columns));
    }

    public static void task2(String filePath) {
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

    private static int getNextIndexForRoundedRock(List<Character> column, int roundedRockIndex) {
        if (roundedRockIndex == 0)
            return 0;
        if (column.get(roundedRockIndex - 1) == '#')
            return roundedRockIndex;
        if (column.get(roundedRockIndex - 1) == '.')
            return getNextIndexForRoundedRock(column, roundedRockIndex - 1);
        if (column.get(roundedRockIndex - 1) == 'O')
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
