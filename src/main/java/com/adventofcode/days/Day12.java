package com.adventofcode.days;

import java.util.ArrayList;
import java.util.List;

import com.adventofcode.utils.ReaderUtil;

public class Day12 {
    private static int recursionCounter = 0;

    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<String> codes = new ArrayList<String>();
        List<List<Integer>> counts = new ArrayList<List<Integer>>();
        for (String line : lines) {
            String[] lineSplitted = line.split(" ");
            codes.add(lineSplitted[0]);
            List<Integer> countsList = new ArrayList<Integer>();
            List.of(lineSplitted[1].split(",")).stream().forEach(c -> countsList.add(Integer.parseInt(c)));
            counts.add(countsList);
        }
        int sum = 0;
        for (int i = 0; i < codes.size(); i++) {
            String currentCode = codes.get(i);
            List<Integer> currentGroupsList = counts.get(i);
            sum += recursiveCheckString(currentCode, currentGroupsList);
        }
        System.out.println(sum);
    }

    private static int recursiveCheckString(String code, List<Integer> groupsList) {
        // System.out.println("code: " + code + " groupsList: " + groupsList);
        int result = 0;
        if (code.isEmpty()) {
            return (groupsList.size() == 0) ? 1 : 0;
        }
        if (groupsList.size() == 0) {
            return (code.contains("#")) ? 0 : 1;
        }
        if (code.startsWith(".") || code.startsWith("?")) {
            result += recursiveCheckString(code.substring(1), groupsList);
        }
        if (code.startsWith("?") || code.startsWith("#")) {
            if (groupsList.get(0) <= code.length()
                    && !code.substring(0, groupsList.get(0)).contains(".")
                    && (groupsList.get(0) == code.length() || code.charAt(groupsList.get(0)) != '#')) {
                if (groupsList.get(0) == code.length()) {
                    result += recursiveCheckString("", groupsList.subList(1, groupsList.size()));
                } else {
                    result += recursiveCheckString(code.substring(groupsList.get(0) + 1),
                            groupsList.subList(1, groupsList.size()));
                }
            }
        }

        return result;
    }

    public static void task2(String filePath) {

    }
}
