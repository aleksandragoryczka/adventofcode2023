package com.adventofcode.days;

import java.util.ArrayList;
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

        HashMap<List<Character>, Integer> handsMap = getHandsMapFromLines(lines);

        List<List<HashMap<List<Character>, Integer>>> orderHandsList = getOrderHandsList(handsMap);
        List<Integer> valuesInOrder = sortAndGetValues(orderHandsList, "AKQJT98765432");

        int result = IntStream.range(0, valuesInOrder.size()).map(i -> valuesInOrder.get(i) * (i + 1)).sum();

        System.out.println(result);
    }

    public static void task2(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);

        HashMap<List<Character>, Integer> handsMap = getHandsMapFromLines(lines);

        List<List<HashMap<List<Character>, Integer>>> orderHandsList = getOrderHandsListWithJokers(handsMap);
        List<Integer> valuesInOrder = sortAndGetValues(orderHandsList, "AKQT98765432J");

        int result = IntStream.range(0, valuesInOrder.size()).map(i -> valuesInOrder.get(i) * (i + 1)).sum();

        System.out.println(result);
    }

    private static HashMap<List<Character>, Integer> getHandsMapFromLines(List<String> lines) {
        HashMap<List<Character>, Integer> handsMap = new HashMap<List<Character>, Integer>();
        for (String line : lines) {
            String[] splittedLine = line.split(" ");
            List<Character> key = new ArrayList<Character>();
            for (char c : splittedLine[0].toCharArray()) {
                key.add(c);
            }
            handsMap.put(key, Integer.parseInt(splittedLine[1]));
        }
        return handsMap;
    }

    private static List<Integer> sortAndGetValues(List<List<HashMap<List<Character>, Integer>>> orderHandsList,
            String keyChars) {
        for (List<HashMap<List<Character>, Integer>> list : orderHandsList) {
            if (list.size() > 1) {
                Collections.sort(list, (o1, o2) -> {
                    return compareKeys(((HashMap<List<Character>, Integer>) o1).keySet().iterator().next(),
                            ((HashMap<List<Character>, Integer>) o2).keySet().iterator().next(), keyChars);
                });
            }
        }
        List<Integer> valuesInOrder = orderHandsList.stream()
                .flatMap(list -> list.stream().flatMap(map -> map.values().stream())).toList();
        return valuesInOrder;
    }

    private static List<List<HashMap<List<Character>, Integer>>> insertIntoOrderHandsList(int index,
            List<List<HashMap<List<Character>, Integer>>> orderHandsList, List<Character> cards, int handValue) {
        orderHandsList.get(index).add(new HashMap<List<Character>, Integer>(Map.of(cards, handValue)));
        return orderHandsList;
    }

    private static List<List<HashMap<List<Character>, Integer>>> getOrderHandsListWithJokers(
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
                orderHandsList = insertIntoOrderHandsList(6, orderHandsList, cards, hand.getValue());
            } else if (charOccurences.entrySet().size() == 2) {
                if (charOccurences.values().stream().anyMatch(x -> x == 4)) {
                    if (charOccurences.containsKey('J')) {
                        orderHandsList = insertIntoOrderHandsList(6, orderHandsList, cards, hand.getValue());
                    } else
                        orderHandsList = insertIntoOrderHandsList(5, orderHandsList, cards, hand.getValue());
                } else if (charOccurences.values().stream().anyMatch(x -> x == 3)) {
                    if (charOccurences.containsKey('J')) {
                        orderHandsList = insertIntoOrderHandsList(6, orderHandsList, cards, hand.getValue());
                    } else
                        orderHandsList = insertIntoOrderHandsList(4, orderHandsList, cards, hand.getValue());
                }
            } else if (charOccurences.entrySet().size() == 3) {
                if (charOccurences.values().stream().anyMatch(x -> x == 3)) {
                    if (charOccurences.containsKey('J')) {
                        orderHandsList = insertIntoOrderHandsList(5, orderHandsList, cards, hand.getValue());
                    } else
                        orderHandsList = insertIntoOrderHandsList(3, orderHandsList, cards, hand.getValue());
                } else {
                    if (charOccurences.containsKey('J')) {
                        if (charOccurences.get('J') == 2)
                            orderHandsList = insertIntoOrderHandsList(5, orderHandsList, cards, hand.getValue());
                        else if (charOccurences.get('J') == 1)
                            orderHandsList = insertIntoOrderHandsList(4, orderHandsList, cards, hand.getValue());
                    } else
                        orderHandsList = insertIntoOrderHandsList(2, orderHandsList, cards, hand.getValue());
                }
            } else if (charOccurences.entrySet().size() == 4) {
                if (charOccurences.containsKey('J')) {
                    orderHandsList = insertIntoOrderHandsList(3, orderHandsList, cards, hand.getValue());
                } else
                    orderHandsList = insertIntoOrderHandsList(1, orderHandsList, cards, hand.getValue());
            } else {
                if (charOccurences.containsKey('J')) {
                    orderHandsList = insertIntoOrderHandsList(1, orderHandsList, cards, hand.getValue());
                } else
                    orderHandsList = insertIntoOrderHandsList(0, orderHandsList, cards, hand.getValue());
            }
        }
        return orderHandsList;
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
                orderHandsList = insertIntoOrderHandsList(6, orderHandsList, cards, hand.getValue());
            } else if (charOccurences.entrySet().size() == 2) {
                if (charOccurences.values().stream().anyMatch(x -> x == 4)) {
                    orderHandsList = insertIntoOrderHandsList(5, orderHandsList, cards, hand.getValue());
                } else if (charOccurences.values().stream().anyMatch(x -> x == 3)) {
                    orderHandsList = insertIntoOrderHandsList(4, orderHandsList, cards, hand.getValue());
                }
            } else if (charOccurences.entrySet().size() == 3) {
                if (charOccurences.values().stream().anyMatch(x -> x == 3)) {
                    orderHandsList = insertIntoOrderHandsList(3, orderHandsList, cards, hand.getValue());
                } else {
                    orderHandsList = insertIntoOrderHandsList(2, orderHandsList, cards, hand.getValue());
                }
            } else if (charOccurences.entrySet().size() == 4) {
                orderHandsList = insertIntoOrderHandsList(1, orderHandsList, cards, hand.getValue());
            } else {
                orderHandsList = insertIntoOrderHandsList(0, orderHandsList, cards, hand.getValue());
            }
        }
        return orderHandsList;
    }

    private static int compareKeys(List<Character> key1, List<Character> key2, String keyChars) {
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
