package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.adventofcode.utils.ReaderUtil;

public class Day04 {
    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);

        List<List<List<String>>> splittedLines = getSplittedLines(lines);
        List<Integer> points = new ArrayList<Integer>();

        splittedLines.stream().forEach(line -> {
            points.add(getPointsForLine(line));
        });

        AtomicInteger pointsSum = new AtomicInteger(0);
        points.forEach(point -> {
            if (point > 1) {
                pointsSum.addAndGet((int) Math.pow(2, point - 1));
            } else {
                pointsSum.addAndGet(point);
            }
        });
        System.out.println(pointsSum.get());
    }

    public static void task2(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);

        List<List<List<String>>> splittedLines = getSplittedLines(lines);
        List<Integer> copiesNumbersList = new ArrayList<Integer>(Collections.nCopies(splittedLines.size(), 1));
        for (int i = 0; i < splittedLines.size(); i++) {
            List<List<String>> line = splittedLines.get(i);
            int point = getPointsForLine(line);
            copiesNumbersList = updateCopiesNumbersList(point, i, copiesNumbersList, copiesNumbersList.get(i));
        }
        System.out.println(copiesNumbersList.stream().mapToInt(Integer::intValue).sum());
    }

    private static List<List<List<String>>> getSplittedLines(List<String> lines) {
        return lines.stream().map(line -> Arrays.asList(line.split("\\s*\\|\\s*")))
                .map(lineFragment -> lineFragment.stream().map(fragment -> Arrays.asList(fragment.trim().split("\\s+")))
                        .map(innerList -> {
                            if (innerList.get(0).equals("Card")) {
                                return innerList.subList(2, innerList.size());
                            }
                            return innerList;
                        })
                        .toList())
                .toList();
    }

    private static List<Integer> updateCopiesNumbersList(int point, int currentLineNumber,
            List<Integer> copiesNumbersList, int cardCopiesNumber) {
        for (int i = 0; i < point; i++) {
            int index = currentLineNumber + i + 1;
            copiesNumbersList.set(index, copiesNumbersList.get(index) + cardCopiesNumber);
        }
        return copiesNumbersList;
    }

    private static int getPointsForLine(List<List<String>> line) {
        AtomicInteger linePoints = new AtomicInteger(0);
        line.get(1).stream().forEach(num -> {
            if (line.get(0).contains(num)) {
                linePoints.incrementAndGet();
            }
        });
        return linePoints.get();
    }
}
