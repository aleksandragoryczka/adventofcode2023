package com.adventofcode.days;


import java.util.Arrays;
import java.util.List;

import com.adventofcode.utils.ReaderUtil;

public class Day01 {
    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<String> cleanedLines = lines.stream().map(line -> line.replaceAll("[^0-9]", "")).toList();
        int calibrationValuesSum = cleanedLines.stream().map(line -> String.valueOf(line.charAt(0)) + String.valueOf(line.charAt(line.length() - 1))).mapToInt(Integer::parseInt).sum();
        System.out.println(calibrationValuesSum);
    }

    public static void task2(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<String> wordDigitsList = Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

        List<String> cleanedLines = lines.stream().map(line -> {
            StringBuilder beginningBuilder = new StringBuilder();
            StringBuilder endBuilder = new StringBuilder();
            StringBuilder resultBuilder = new StringBuilder();
            for(int i=0; resultBuilder.length() == 0; i++){
                if(Character.isDigit(line.charAt(i))){
                    resultBuilder.append(line.charAt(i));
                    break;
                }else{
                    beginningBuilder.append(line.charAt(i));
                }
                wordDigitsList.stream().forEach(num -> {
                    if(beginningBuilder.toString().contains(num)) {
                        resultBuilder.append(Integer.toString(wordDigitsList.indexOf(num) + 1));
                    }
                });
            }
            for(int i=line.length()-1; resultBuilder.length() == 1; i--){
                if(Character.isDigit(line.charAt(i))){
                    resultBuilder.append(line.charAt(i));
                    break;
                }else{
                    endBuilder.insert(0, line.charAt(i));
                }
                wordDigitsList.stream().forEach(num -> {
                    if(endBuilder.toString().contains(num)) {
                        resultBuilder.append(Integer.toString(wordDigitsList.indexOf(num) + 1));
                    }
                });
            }
            return resultBuilder.toString();
        }).toList();
        System.out.println(cleanedLines.stream().mapToInt(Integer::parseInt).sum());
    }
}
