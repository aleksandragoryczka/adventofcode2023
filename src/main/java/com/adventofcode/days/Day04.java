package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.adventofcode.utils.ReaderUtil;

public class Day04 {
    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);

        List<List<List<String>>> splittedLines = lines.stream().map(line -> Arrays.asList(line.split("\\s*\\|\\s*")))
                .map(lineFragment -> lineFragment.stream().map(fragment -> Arrays.asList(fragment.trim().split("\\s+")))
                        .map(innerList -> {
                            if (innerList.get(0).equals("Card")) {
                                return innerList.subList(2, innerList.size());
                            }
                            return innerList;
                        })
                        .toList())
                .toList();
        List<Integer> points = new ArrayList<Integer>();

        splittedLines.stream().forEach(line -> {
            AtomicInteger linePoints = new AtomicInteger(0);
            line.get(1).stream().forEach(num -> {
                if (line.get(0).contains(num)) {
                    linePoints.incrementAndGet();
                }
            });
            points.add(linePoints.get());
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

    }
}
