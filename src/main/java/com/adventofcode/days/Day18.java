package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adventofcode.utils.ReaderUtil;

public class Day18 {
    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        ;
        List<List<Long>> coordinates = new ArrayList<List<Long>>(Arrays.asList(Arrays.asList(0L, 0L)));

        Pattern pattern = Pattern.compile("([RLDU])\\s(\\d+)");

        AtomicInteger i = new AtomicInteger(0);
        lines.stream().map(pattern::matcher).filter(Matcher::find).forEach(matcher -> {
            switch (matcher.group(1)) {
                case "R":
                    coordinates.add(Arrays.asList(coordinates.get(i.get()).get(0) + Integer.parseInt(matcher.group(2)),
                            coordinates.get(i.get()).get(1)));
                    i.getAndIncrement();
                    break;
                case "L":
                    coordinates.add(Arrays.asList(coordinates.get(i.get()).get(0) - Integer.parseInt(matcher.group(2)),
                            coordinates.get(i.get()).get(1)));
                    i.getAndIncrement();
                    break;
                case "U":
                    coordinates.add(Arrays.asList(coordinates.get(i.get()).get(0),
                            coordinates.get(i.get()).get(1) + Integer.parseInt(matcher.group(2))));
                    i.getAndIncrement();
                    break;
                case "D":
                    coordinates.add(Arrays.asList(coordinates.get(i.get()).get(0),
                            coordinates.get(i.get()).get(1) - Integer.parseInt(matcher.group(2))));
                    i.getAndIncrement();
                    break;
            }
        });
        coordinates.remove(0);
        System.out.println(shoelaceFormulaWithPerimeter(coordinates));

    }

    public static void task2(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<Long>> coordinates = new ArrayList<List<Long>>(Arrays.asList(Arrays.asList(0L, 0L)));

        Pattern pattern = Pattern.compile("#(\\w+)");

        AtomicInteger i = new AtomicInteger(0);
        lines.stream().map(pattern::matcher).filter(Matcher::find).forEach(matcher -> {
            char direction = matcher.group(1).charAt(5);
            long distance = Long.parseLong(matcher.group(1).substring(0, 5), 16);
            switch(direction) {
                case '0':
                    coordinates.add(Arrays.asList(coordinates.get(i.get()).get(0) + distance, coordinates.get(i.get()).get(1)));
                    i.getAndIncrement();
                    break;
                case '2':
                    coordinates.add(Arrays.asList(coordinates.get(i.get()).get(0) - distance, coordinates.get(i.get()).get(1)));
                    i.getAndIncrement();
                    break;
                case '3':
                    coordinates.add(Arrays.asList(coordinates.get(i.get()).get(0), coordinates.get(i.get()).get(1) + distance));
                    i.getAndIncrement();
                    break;
                case '1':
                    coordinates.add(Arrays.asList(coordinates.get(i.get()).get(0), coordinates.get(i.get()).get(1) - distance));
                    i.getAndIncrement();
                    break;
            }
        });
        coordinates.remove(0);
        System.out.println(shoelaceFormulaWithPerimeter(coordinates));
    }

    private static long shoelaceFormulaWithPerimeter(List<List<Long>> cornersCoordinates) {
        long sum = 0;
        long perimeter = 0;
        for (int i = 0; i < cornersCoordinates.size() - 1; i++) {
            sum += cornersCoordinates.get(i).get(0) * cornersCoordinates.get(i + 1).get(1)
                    - cornersCoordinates.get(i + 1).get(0) * cornersCoordinates.get(i).get(1);
            perimeter += Math.abs(cornersCoordinates.get(i).get(0) - cornersCoordinates.get(i + 1).get(0)
                    + cornersCoordinates.get(i).get(1) - cornersCoordinates.get(i + 1).get(1));
        }
        sum += cornersCoordinates.get(cornersCoordinates.size() - 1).get(0) * cornersCoordinates.get(0).get(1)
                - cornersCoordinates.get(0).get(0) * cornersCoordinates.get(cornersCoordinates.size() - 1).get(1);
        perimeter += Math.abs(cornersCoordinates.get(cornersCoordinates.size() - 1).get(0)
                - cornersCoordinates.get(0).get(0) + cornersCoordinates.get(cornersCoordinates.size() - 1).get(1)
                - cornersCoordinates.get(0).get(1));
        return Math.abs(sum / 2) + perimeter / 2 + 1;
    }
}
