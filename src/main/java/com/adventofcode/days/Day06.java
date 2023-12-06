package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.adventofcode.utils.ReaderUtil;

public class Day06 {

    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<Integer>> numbersList = lines.stream()
                .map(line -> Arrays.stream(line.split("\\s+|(?<=Time:)|(?<=Distance:)"))
                        .filter(str -> str.matches("\\d+")).mapToInt(Integer::parseInt).boxed().toList())
                .toList();
        List<Integer> timesList = numbersList.get(0);
        List<Integer> distancesList = numbersList.get(1);
        
        List<Integer> resultsList = solveQuadricEquation(timesList, distancesList);

        int resultMultiplication = resultsList.stream().reduce(1, (a, b) -> a * b);
        System.out.println(resultMultiplication);
    }

    public static void task2(String filePath) {
    }

    private static List<Integer> solveQuadricEquation(List<Integer> timesList, List<Integer> distancesList) {
        List<Integer> resultsList = Arrays.asList(new Integer[timesList.size()]);
        Collections.fill(resultsList, 0);
        for (int i = 0; i < timesList.size(); i++) {
            int time = timesList.get(i);
            int distance = distancesList.get(i);
            int f1 = time - 1;
            int x1 = 0;
            int x2 = time;
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
