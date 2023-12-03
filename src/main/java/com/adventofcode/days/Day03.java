package com.adventofcode.days;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adventofcode.utils.ReaderUtil;

public class Day03 {
    public static void task1(String filePath){
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<Character>> charsMatrix = lines.stream().map(line -> line.chars().mapToObj(c -> (char) c).toList()).toList();
        int i = 0;
        int partNumbersSum = 0;
        while(i < charsMatrix.size()){
            StringBuilder digitsBuilder = new StringBuilder();
            int j = 0;
            while(j < charsMatrix.get(i).size()){
                while(j != charsMatrix.get(i).size() && Character.isDigit(charsMatrix.get(i).get(j))){
                    digitsBuilder.append(charsMatrix.get(i).get(j));
                    j++;
                }
                if(digitsBuilder.length() > 0){
                    if(checkIsAdjacent(i, j, digitsBuilder.length(), charsMatrix)){         
                        partNumbersSum += Integer.parseInt(digitsBuilder.toString());
                    }
                    digitsBuilder = new StringBuilder();
                }
                j++;
            }
            i++;
        }
        System.out.println(partNumbersSum);
    }

    public static void task2(String filePath){

    }

    private static boolean checkIsAdjacent(int i, int j, int stringLength, List<List<Character>> charsMatrix){
        if(i == 0){
            if(j - stringLength == 0){
                for(int x = 0; x < i+2; x++){
                    if(iterateHelper(0, stringLength+1, charsMatrix, x)) return true;
                }
            }else if(j == charsMatrix.get(i).size()){
                for(int x = 0; x < i+2; x++){
                    if(iterateHelper(j-stringLength-1, j-1, charsMatrix, x)) return true;
                }
            } else{
                for(int x = 0; x < i+2; x++){
                    if(iterateHelper(j-stringLength-1, j+1, charsMatrix, x)) return true;
                }
            }
        }
        else if(i == charsMatrix.size()-1){
            if(j - stringLength == 0){
                for(int x = i-1; x < i+1; x++){
                    if(iterateHelper(0, stringLength+1, charsMatrix, x)) return true;
                }
            }else if(j == charsMatrix.get(i).size()){
                for(int x = i-1; x < i+1; x++){
                    if(iterateHelper(j-stringLength-1, j-1, charsMatrix, x)) return true;
                }
            }
            else{
                for(int x = i-1; x < i+1; x++){
                    if(iterateHelper(j-stringLength-1, j+1, charsMatrix, x)) return true;
                }
            }
        }else{
            for(int x = i-1; x < i+2; x++){
                if(j - stringLength == 0){
                    if(iterateHelper(0, stringLength+1, charsMatrix, x)) return true;
                }else if(j == charsMatrix.get(i).size()){
                    if(iterateHelper(j-stringLength-1, j-1, charsMatrix, x)) return true;
                }else{
                    if(iterateHelper(j-stringLength-1, j+1, charsMatrix, x)) return true;
                }
            }
        }
        return false;
    }

    private static boolean iterateHelper(int startCondition, int endCondition, List<List<Character>> charsMatrix, int verticalindex){
        String regex = "[^\\d.]";
        for(int i = startCondition; i < endCondition; i++){
            if(String.valueOf(charsMatrix.get(verticalindex).get(i)).matches(regex)){
                return true;
            }
        }
        return false;
    }
}
