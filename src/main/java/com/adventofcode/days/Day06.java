package com.adventofcode.days;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.adventofcode.utils.ReaderUtil;

public class Day06 {

    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<Long>> numbersList = lines.stream()
                .map(line -> Arrays.stream(line.split("\\s+|(?<=Time:)|(?<=Distance:)"))
                        .filter(str -> str.matches("\\d+")).mapToLong(Long::parseLong).boxed().toList())
                .toList();
        countResults(numbersList.get(0), numbersList.get(1));
    }

    public static void task2(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<Long> numbersList = lines.stream()
                .map(line -> Arrays.stream(line.trim().split("\\s+|(?<=Time:)|(?<=Distance:)"))
                        .filter(str -> str.matches("\\d+"))
                        .map(String::valueOf)
                        .collect(Collectors.joining()))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        countResults(Arrays.asList(numbersList.get(0)), Arrays.asList(numbersList.get(1)));
    }

    private static void countResults(List<Long> timesList, List<Long> distancesList) {
        List<Integer> resultsList = solveQuadricEquation(timesList, distancesList);

        int resultMultiplication = resultsList.stream().reduce(1, (a, b) -> a * b);
        System.out.println(resultMultiplication);
    }

    private static List<Integer> solveQuadricEquation(List<Long> timesList, List<Long> distancesList) {
        List<Integer> resultsList = Arrays.asList(new Integer[timesList.size()]);
        Collections.fill(resultsList, 0);
        for (int i = 0; i < timesList.size(); i++) {
            long time = timesList.get(i);
            long distance = distancesList.get(i);
            long f1 = time - 1;
            int x1 = 0;
            long x2 = time;
            double a = f1 / ((1 - x1) * (1 - x2));

            for (int x = 1; x < time; x++) {
                double result = a * (x - x1) * (x - x2);
                if (result > distance)
                    resultsList.set(i, resultsList.get(i) + 1);
            }
        }
        return resultsList;
    }
}
