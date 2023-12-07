package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.adventofcode.utils.ReaderUtil;

public class Day07 {
    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);

        HashMap<List<Character>, Integer> handsMap = new HashMap<List<Character>, Integer>();
        for (String line : lines) {
            String[] splittedLine = line.split(" ");
            List<Character> key = new ArrayList<Character>();
            for (char c : splittedLine[0].toCharArray()) {
                key.add(c);
            }
            handsMap.put(key, Integer.parseInt(splittedLine[1]));
        }

        List<List<HashMap<List<Character>, Integer>>> orderHandsList = getOrderHandsList(handsMap);
        List<Integer> valuesInOrder = sortAndGetValues(orderHandsList);

        int result = IntStream.range(0, valuesInOrder.size()).map(i -> valuesInOrder.get(i) * (i + 1)).sum();

        System.out.println(result);
    }

    public static void task2(String filePath) {

    }

    private static List<Integer> sortAndGetValues(List<List<HashMap<List<Character>, Integer>>> orderHandsList) {
        for (List<HashMap<List<Character>, Integer>> list : orderHandsList) {
            if (list.size() > 1) {
                Collections.sort(list, (o1, o2) -> {
                    return compareKeys(((HashMap<List<Character>, Integer>) o1).keySet().iterator().next(),
                            ((HashMap<List<Character>, Integer>) o2).keySet().iterator().next());
                });
            }
        }
        List<Integer> valuesInOrder = orderHandsList.stream()
                .flatMap(list -> list.stream().flatMap(map -> map.values().stream())).toList();
        return valuesInOrder;
    }

    private static List<List<HashMap<List<Character>, Integer>>> getOrderHandsList(
            HashMap<List<Character>, Integer> handsMap) {
        List<List<HashMap<List<Character>, Integer>>> orderHandsList = new ArrayList<List<HashMap<List<Character>, Integer>>>();
        for (int i = 0; i < 7; i++) {
            orderHandsList.add(new ArrayList<HashMap<List<Character>, Integer>>());
        }
        for (Map.Entry<List<Character>, Integer> hand : handsMap.entrySet()) {
            List<Character> cards = hand.getKey();
            Map<Object, Long> charOccurences = cards.stream()
                    .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
            if (charOccurences.entrySet().size() == 1) {
                orderHandsList.get(6).add(new HashMap<List<Character>, Integer>(Map.of(cards, hand.getValue())));
            } else if (charOccurences.entrySet().size() == 2) {
                if (charOccurences.values().stream().anyMatch(x -> x == 4)) {
                    orderHandsList.get(5).add(new HashMap<List<Character>, Integer>(Map.of(cards, hand.getValue())));
                }
                if (charOccurences.values().stream().anyMatch(x -> x == 3)) {
                    orderHandsList.get(4).add(new HashMap<List<Character>, Integer>(Map.of(cards, hand.getValue())));
                }
            } else if (charOccurences.entrySet().size() == 3) {
                if (charOccurences.values().stream().anyMatch(x -> x == 3)) {
                    orderHandsList.get(3).add(new HashMap<List<Character>, Integer>(Map.of(cards, hand.getValue())));
                } else {
                    orderHandsList.get(2).add(new HashMap<List<Character>, Integer>(Map.of(cards, hand.getValue())));
                }
            } else if (charOccurences.entrySet().size() == 4) {
                orderHandsList.get(1).add(new HashMap<List<Character>, Integer>(Map.of(cards, hand.getValue())));
            } else {
                orderHandsList.get(0).add(new HashMap<List<Character>, Integer>(Map.of(cards, hand.getValue())));
            }
        }
        return orderHandsList;
    }

    private static int compareKeys(List<Character> key1, List<Character> key2) {
        String keyChars = "AKQJT98765432";

        for (int i = 0; i < 5; i++) {
            char char1 = key1.get(i);
            char char2 = key2.get(i);

            int index1 = keyChars.indexOf(char1);
            int index2 = keyChars.indexOf(char2);

            if (index1 != index2) {
                return Integer.compare(index2, index1);
            }
        }
        return Integer.compare(key1.size(), key2.size());
    }
}
