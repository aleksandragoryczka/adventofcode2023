package com.adventofcode.days;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.adventofcode.utils.ReaderUtil;

public class Day11 {

    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<Character>> charsMatrix = lines.stream().map(line -> line.chars().mapToObj(c -> (char) c).toList())
                .toList();
        List<List<Integer>> startCoordinateList = getStartIndexes(charsMatrix);
        List<Integer> startExpandingRowsNumberList = getExpandedRows(lines);
        List<Integer> startExpandingColumnNumberList = getExpandedColumns(lines);
        countDistancesWithRates(startCoordinateList, startExpandingRowsNumberList, startExpandingColumnNumberList, 1);

    }

    public static void task2(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<Character>> charsMatrix = lines.stream().map(line -> line.chars().mapToObj(c -> (char) c).toList())
                .toList();
        List<List<Integer>> startCoordinateList = getStartIndexes(charsMatrix);
        List<Integer> startExpandingRowsNumberList = getExpandedRows(lines);
        List<Integer> startExpandingColumnNumberList = getExpandedColumns(lines);
        countDistancesWithRates(startCoordinateList, startExpandingRowsNumberList, startExpandingColumnNumberList,
                999999);
    }

    private static void countDistancesWithRates(List<List<Integer>> startCoordinateList,
            List<Integer> startExpandingRowsNumberList, List<Integer> startExpandingColumnNumberList, int rate) {
        long sum = 0;
        for (int i = 0; i < startCoordinateList.size() - 1; i++) {
            for (int j = i + 1; j < startCoordinateList.size(); j++) {

                int x1 = startCoordinateList.get(i).get(0);
                int y1 = startCoordinateList.get(i).get(1);
                int x2 = startCoordinateList.get(j).get(0);
                int y2 = startCoordinateList.get(j).get(1);

                List<Integer> indexesRow = startExpandingRowsNumberList.stream()
                        .filter(val -> (val > x1 && val < x2) || (val < x1 && val > x2))
                        .toList();
                List<Integer> indexesColumn = startExpandingColumnNumberList.stream()
                        .filter(val -> (val < y1 && val > y2) || (val > y1 && val < y2)).toList();
                long milionRateRows = indexesRow.size() > 0 ? indexesRow.size() * rate : 0;
                long milionRateColumns = indexesColumn.size() > 0 ? indexesColumn.size() * rate : 0;

                sum += Math.abs(x1 - x2) + milionRateRows + Math.abs(y1 - y2) + milionRateColumns;
            }
        }
        System.out.println(sum);
    }

    private static List<Integer> getExpandedRows(List<String> lines) {
        List<Integer> startExpandingRowsNumberList = new ArrayList<Integer>();

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).chars().allMatch(c -> c == '.')) {
                startExpandingRowsNumberList.add(i);
            }
        }
        return startExpandingRowsNumberList;
    }

    private static List<Integer> getExpandedColumns(List<String> lines) {
        List<Integer> columnWithDotsIndex = new ArrayList<Integer>();
        for (AtomicInteger i = new AtomicInteger(0); i.get() < lines.get(0).length(); i.incrementAndGet()) {
            if (lines.stream().allMatch(line -> line.charAt(i.get()) == '.')) {
                columnWithDotsIndex.add(i.get());
            }
        }
        return columnWithDotsIndex;
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
}
