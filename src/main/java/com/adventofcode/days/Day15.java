package com.adventofcode.days;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.adventofcode.utils.ReaderUtil;

public class Day15 {

    public static void task1(String filePath) {
        String[] lines = ReaderUtil.readLineByLineToList(filePath).get(0).split(",");
        long sum = 0;
        for (int i = 0; i < lines.length; i++) {
            sum += getCodeValue(lines[i]);
        }
        System.out.println(sum);
    }

    private static class Step {
        String label;
        char operator;
        int value;

        public Step(String label, char operator, int value) {
            this.label = label;
            this.operator = operator;
            this.value = value;
        }

        public Step(String label, char operator) {
            this.label = label;
            this.operator = operator;
            this.value = 0;
        }

        @Override
        public String toString() {
            return "Step [label=" + label + ", operator=" + operator + ", value=" + value + "]";
        }
    }

    private static class Len {
        String label;
        int value;

        public Len(String label, int value) {
            this.label = label;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Len [label=" + label + ", value=" + value + "]";
        }
    }

    public static void task2(String filePath) {
        String[] lines = ReaderUtil.readLineByLineToList(filePath).get(0).split(",");
        List<Step> steps = new ArrayList<Step>();
        for (int i = 0; i < lines.length; i++) {
            String label = lines[i].replaceAll("[^a-zA-Z]", "");
            char operator = lines[i].charAt(label.length());
            if (operator == '=') {
                steps.add(new Step(label, operator, Integer.parseInt(lines[i].substring(label.length() + 1))));
            } else {
                steps.add(new Step(label, operator));
            }
        }
        List<List<Len>> boxesList = new ArrayList<List<Len>>();
        for (int i = 0; i < 256; i++) {
            boxesList.add(new ArrayList<Len>());
        }
        for (Step s : steps) {
            int boxNr = (int) getCodeValue(s.label);
            if (s.operator == '=') {
                List<Len> selectedLens = boxesList.get(boxNr).stream().filter(x -> x.label.equals(s.label))
                        .collect(Collectors.toList());
                if (!selectedLens.isEmpty()) {
                    boxesList.get(boxNr).set(boxesList.get(boxNr).indexOf(selectedLens.get(0)),
                            new Len(s.label, s.value));
                } else {
                    boxesList.get(boxNr).add(new Len(s.label, s.value));
                }
            }
            if (s.operator == '-') {
                boxesList.get(boxNr).removeIf(x -> x.label.equals(s.label));
            }
        }

        long focusingPowerSSum = 0;
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < boxesList.get(i).size(); j++) {
                focusingPowerSSum += (i + 1) * (j + 1) * boxesList.get(i).get(j).value;
            }
        }
        System.out.println(focusingPowerSSum);
    }

    private static long getCodeValue(String line) {
        long currentValue = 0;
        for (int j = 0; j < line.length(); j++) {
            currentValue += (int) line.charAt(j);
            currentValue *= 17;
            currentValue = currentValue % 256;
        }
        return currentValue;
    }
}
