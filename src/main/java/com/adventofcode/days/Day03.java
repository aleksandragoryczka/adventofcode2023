package com.adventofcode.days;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<Character>> charsMatrix = lines.stream().map(line -> line.chars().mapToObj(c -> (char) c).toList()).toList();
        int i = 0;
        HashMap<List<Integer>, List<Integer>> gearsCoordinatesMap = new HashMap<List<Integer>, List<Integer>>();
        while(i < charsMatrix.size()){
            StringBuilder digitsBuilder = new StringBuilder();
            int j = 0;
            while(j < charsMatrix.get(i).size()){
                while(j != charsMatrix.get(i).size() && Character.isDigit(charsMatrix.get(i).get(j))){
                    digitsBuilder.append(charsMatrix.get(i).get(j));
                    j++;
                }
                if(digitsBuilder.length() > 0){
                    List<Integer> gearsCoordinates = findGears(i, j, digitsBuilder.length(), charsMatrix);
                    if(gearsCoordinates != null){
                        gearsCoordinatesMap = updateGearsMap(gearsCoordinatesMap, gearsCoordinates, Integer.parseInt(digitsBuilder.toString()));
                    }
                    digitsBuilder = new StringBuilder();
                }
                j++;
            }
            i++;
        }
        int gearsRatiosSum = gearsCoordinatesMap.values().stream().filter(values -> values.size() == 2).map(values -> values.get(0) * values.get(1)).mapToInt(Integer::intValue).sum();
        System.out.println(gearsRatiosSum);
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

    private static List<Integer> findGears(int i, int j, int stringLength, List<List<Character>> charsMatrix){
        if(i == 0){
            if(j - stringLength == 0){
                for(int x = 0; x < i+2; x++){
                    List<Integer> gearCoordinates = iterateHelperFindGear(0, stringLength+1, charsMatrix, x);
                    if(gearCoordinates != null){
                        return gearCoordinates;
                    }
                }
            }else if(j == charsMatrix.get(i).size()){
                for(int x = 0; x < i+2; x++){
                    List<Integer> gearCoordinates = iterateHelperFindGear(j-stringLength, j-1, charsMatrix, x);
                    if(gearCoordinates != null){
                        return gearCoordinates;
                    }
                }
            } else{
                for(int x = 0; x < i+2; x++){
                    List<Integer> gearCoordinates = iterateHelperFindGear(j-stringLength-1, j+1, charsMatrix, x);
                    if(gearCoordinates != null){
                        return gearCoordinates;
                    }
                }
            }
        }
        else if(i == charsMatrix.size()-1){
            if(j - stringLength == 0){
                for(int x = i-1; x < i+1; x++){
                    List<Integer> gearCoordinates = iterateHelperFindGear(0, stringLength+1, charsMatrix, x);
                    if(gearCoordinates != null){
                        return gearCoordinates;
                    }
                }
            }else if(j == charsMatrix.get(i).size()){
                for(int x = i-1; x < i+1; x++){
                    List<Integer> gearCoordinates = iterateHelperFindGear(j-stringLength-1, j-1, charsMatrix, x);
                    if(gearCoordinates != null){
                        return gearCoordinates;
                    }
                }
            }
            else{
                for(int x = i-1; x < i+1; x++){
                    List<Integer> gearCoordinates = iterateHelperFindGear(j-stringLength-1, j+1, charsMatrix, x);
                    if(gearCoordinates != null){
                        return gearCoordinates;
                    }
                }
            }
        }else{
            for(int x = i-1; x < i+2; x++){
                if(j - stringLength == 0){
                    List<Integer> gearCoordinates = iterateHelperFindGear(0, stringLength+1, charsMatrix, x);
                    if(gearCoordinates != null){
                        return gearCoordinates;
                    }
                }else if(j == charsMatrix.get(i).size()){
                    List<Integer> gearCoordinates = iterateHelperFindGear(j-stringLength-1, j-1, charsMatrix, x);
                    if(gearCoordinates != null){
                        return gearCoordinates;
                    }
                }else{
                    List<Integer> gearCoordinates = iterateHelperFindGear(j-stringLength-1, j+1, charsMatrix, x);
                    if(gearCoordinates != null){
                        return gearCoordinates;
                    }
                }
            }
        }
        return null;
    }

    private static HashMap<List<Integer>, List<Integer>> updateGearsMap(HashMap<List<Integer>, List<Integer>> gearsCoordinatesMap, List<Integer> gearsCoordinates, Integer partNumber){
        if(gearsCoordinatesMap.containsKey(gearsCoordinates)){
            gearsCoordinatesMap.put(gearsCoordinates, Arrays.asList(partNumber, gearsCoordinatesMap.get(gearsCoordinates).get(0)));  
        }else{
            gearsCoordinatesMap.put(gearsCoordinates, Arrays.asList(partNumber));
        }
        return gearsCoordinatesMap;
    }

    private static List<Integer> iterateHelperFindGear(int startCondition, int endCondition, List<List<Character>> charsMatrix, int verticalindex){
        for(int i = startCondition; i < endCondition; i++){
            if(charsMatrix.get(verticalindex).get(i) == '*'){
                return Arrays.asList(verticalindex, i);
            }
        }
        return null;
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
