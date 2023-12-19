package com.adventofcode.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adventofcode.utils.ReaderUtil;

public class Day19 {
    private static class Condition {
        char variable;
        char operator;
        int value;
        String nextStep;

        public Condition(char variable, char operator, String stringValue, String nextStep) {
            this.variable = variable;
            this.operator = operator;
            this.value = Integer.parseInt(stringValue);
            this.nextStep = nextStep;
        }

        public Condition(String nextStep) {
            char variable = ' ';
            char operator = ' ';
            int value = 0;
            this.nextStep = nextStep;

        }

        public String compareByCondition(int comparedValue, char comparedVariable) {
            if (comparedVariable == variable) {
                if (operator == '>') {
                    return comparedValue > value ? nextStep : "";
                } else if (operator == '<') {
                    return comparedValue < value ? nextStep : "";
                }
            }
            return "";
        }

        @Override
        public String toString() {
            return "Condition [variable=" + variable + ", operator=" + operator + ", value=" + value
                    + ", nextStep=" + nextStep + "]";
        }

    }

    private static class ConditionsList {
        List<Condition> conditionsList;
        String defaultNextStep;

        public ConditionsList() {
            this.conditionsList = new ArrayList<Condition>();
        }

        public void addConditionToConditionsList(Condition condition) {
            conditionsList.add(condition);
        }

        public void setDefaultNextStep(String defaultNextStep) {
            this.defaultNextStep = defaultNextStep;
        }

        public String compareConditions(Part part) {
            for (Condition condition : conditionsList) {
                String nextStep = condition.compareByCondition(part.x, 'x');
                if (!nextStep.isEmpty()) {
                    return nextStep;
                }
                nextStep = condition.compareByCondition(part.m, 'm');
                if (!nextStep.isEmpty()) {
                    return nextStep;
                }
                nextStep = condition.compareByCondition(part.a, 'a');
                if (!nextStep.isEmpty()) {
                    return nextStep;
                }
                nextStep = condition.compareByCondition(part.s, 's');
                if (!nextStep.isEmpty()) {
                    return nextStep;
                }
            }
            return defaultNextStep;
        }

        @Override
        public String toString() {
            return "ConditionsList [conditionsList=" + conditionsList + ", defaultNextStep=" + defaultNextStep
                    + "]";
        }
    }

    private static class Part {
        int x, m, a, s;

        public Part(int x, int m, int a, int s) {
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
        }

        public int getSumOfRating() {
            return x + m + a + s;
        }

        @Override
        public String toString() {
            return "Part [x=" + x + ", m=" + m + ", a=" + a + ", s=" + s + "]";
        }
    }

    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);

        HashMap<String, ConditionsList> workflowsMap = new HashMap<String, ConditionsList>();
        List<Part> partList = new ArrayList<Part>();
        for (String line : lines) {
            if (!line.startsWith("{") && !line.isEmpty()) {

                ConditionsList conditionList = new ConditionsList();
                String[] parts = line.replace("}", "").split("\\{");
                String[] conditionsSplitted = parts[1].split(",");
                for (String condition : conditionsSplitted) {
                    String[] splittedSingleCondition = condition.split(":");
                    if (splittedSingleCondition.length == 2) {
                        conditionList.addConditionToConditionsList(new Condition(splittedSingleCondition[0].charAt(0),
                                splittedSingleCondition[0].charAt(1), splittedSingleCondition[0].substring(2),
                                splittedSingleCondition[1]));
                    } else {
                        conditionList.setDefaultNextStep(splittedSingleCondition[0]);
                    }
                }
                workflowsMap.put(parts[0], conditionList);
            } else if (line.startsWith("{") && !line.isEmpty()) {
                int[] numbers = extractNumbersForPart(line);
                partList.add(new Part(numbers[0], numbers[1], numbers[2], numbers[3]));
            }
        }
        int sum = 0;
        for (Part part : partList) {
            ConditionsList condition = workflowsMap.get("in");
            String comparingResult = condition.compareConditions(part);
            while (!comparingResult.equals("A") && !comparingResult.equals("R")) {
                condition = workflowsMap.get(comparingResult);
                comparingResult = condition.compareConditions(part);
            }
            if (comparingResult.equals("A")) {
                sum += part.getSumOfRating();
            }
        }
        System.out.println(sum);
    }

    public static void task2(String filePath) {

    }

    private static int[] extractNumbersForPart(String line) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(line);
        return matcher.results().mapToInt(match -> Integer.parseInt(match.group())).toArray();
    }
}
