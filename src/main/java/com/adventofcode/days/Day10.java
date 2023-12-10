package com.adventofcode.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.adventofcode.utils.ReaderUtil;

public class Day10 {
    private static class FieldState {
        private Character currentChar;
        private AtomicInteger columnIndex;
        private AtomicInteger rowIndex;
        private int prevColumnIndex;
        private int prevRowIndex;

        public FieldState(Character currentChar, AtomicInteger columnIndex, AtomicInteger rowIndex, int prevColumnIndex,
                int prevRowIndex) {
            this.currentChar = currentChar;
            this.columnIndex = columnIndex;
            this.rowIndex = rowIndex;
            this.prevColumnIndex = prevColumnIndex;
            this.prevRowIndex = prevRowIndex;
        }
    }

    private final static Map<Character, List<Character>> rightMap = Map.of(
            'S', List.of('-', '7', 'J'),
            '|', List.of(),
            '-', List.of('-', '7', 'J', 'S'),
            'L', List.of('-', '7', 'J', 'S'),
            'J', List.of(),
            '7', List.of(),
            'F', List.of('-', '7', 'J', 'S'));

    private final static Map<Character, List<Character>> leftMap = Map.of(
            'S', List.of('-', 'F', 'L', 'S'),
            '|', List.of(),
            '-', List.of('-', 'F', 'L', 'S'),
            'L', List.of(),
            'J', List.of('-', 'F', 'L', 'S'),
            '7', List.of('-', 'F', 'L', 'S'),
            'F', List.of());

    private final static Map<Character, List<Character>> upMap = Map.of(
            'S', List.of('|', '7', 'F'),
            '|', List.of('|', '7', 'F', 'S'),
            '-', List.of(),
            'L', List.of('|', '7', 'F', 'S'),
            'J', List.of('|', '7', 'F', 'S'),
            '7', List.of(),
            'F', List.of());

    private final static Map<Character, List<Character>> downMap = Map.of(
            'S', List.of('|', 'L', 'J'),
            '|', List.of('|', 'L', 'J', 'S'),
            '-', List.of(),
            'L', List.of(),
            'J', List.of(),
            '7', List.of('|', 'L', 'J', 'S'),
            'F', List.of('|', 'L', 'J', 'S'));

    private final static List<Character> cornersList = List.of('L', 'J', '7', 'F');

    public static void task1(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<Character>> matrix = lines.stream().map(line -> line.chars().mapToObj(c -> (char) c).toList())
                .toList();

        int[] indexes = getSIndexes(matrix);
        AtomicInteger rowIndex = new AtomicInteger(indexes[0]);
        AtomicInteger columnIndex = new AtomicInteger(indexes[1]);
        int stepsCounter = 1;

        FieldState currentFieldState = makeNextSteps(matrix, new FieldState(
                'S', columnIndex, rowIndex, columnIndex.get(), rowIndex.get()));

        while (currentFieldState.currentChar != 'S') {
            stepsCounter++;
            currentFieldState = makeNextSteps(matrix, currentFieldState);
        }
        System.out.println(stepsCounter / 2);
    }

    public static void task2(String filePath) {
        List<String> lines = ReaderUtil.readLineByLineToList(filePath);
        List<List<Character>> matrix = lines.stream().map(line -> line.chars().mapToObj(c -> (char) c).toList())
                .toList();

        int[] indexes = getSIndexes(matrix);
        AtomicInteger rowIndex = new AtomicInteger(indexes[0]);
        AtomicInteger columnIndex = new AtomicInteger(indexes[1]);
        int stepsCounter = 1;
        List<List<Integer>> cornersCoordinates = new ArrayList<List<Integer>>();

        FieldState currentFieldState = makeNextSteps(matrix, new FieldState(
                'S', columnIndex, rowIndex, columnIndex.get(), rowIndex.get()));

        while (currentFieldState.currentChar != 'S') {
            Character c = currentFieldState.currentChar;
            if (cornersList.stream().anyMatch(val -> val.equals(c))) {
                cornersCoordinates.add(List.of(currentFieldState.columnIndex.get(), currentFieldState.rowIndex.get()));
            }
            stepsCounter++;
            currentFieldState = makeNextSteps(matrix, currentFieldState);
        }

        int pickFormulaResult = shoelaceFormula(cornersCoordinates) - stepsCounter / 2 + 1;
        System.out.println(pickFormulaResult);
    }

    private static int shoelaceFormula(List<List<Integer>> cornersCoordinates) {
        int sum = 0;
        for (int i = 0; i < cornersCoordinates.size() - 1; i++) {
            sum += cornersCoordinates.get(i).get(0) * cornersCoordinates.get(i + 1).get(1)
                    - cornersCoordinates.get(i + 1).get(0) * cornersCoordinates.get(i).get(1);
        }
        sum += cornersCoordinates.get(cornersCoordinates.size() - 1).get(0) * cornersCoordinates.get(0).get(1)
                - cornersCoordinates.get(0).get(0) * cornersCoordinates.get(cornersCoordinates.size() - 1).get(1);
        return Math.abs(sum / 2);
    }

    private static int[] getSIndexes(List<List<Character>> matrix) {
        AtomicInteger rowIndex = new AtomicInteger();
        AtomicInteger columnIndex = new AtomicInteger();
        outerLoop: for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j) == 'S') {
                    rowIndex.set(i);
                    columnIndex.set(j);
                    break outerLoop;
                }
            }
        }
        return new int[] { rowIndex.get(), columnIndex.get() };
    }

    private static FieldState makeNextSteps(List<List<Character>> matrix, FieldState currentFieldState) {
        Character currentChar = currentFieldState.currentChar;
        int currentRowIndex = currentFieldState.rowIndex.get();
        int currentColumnIndex = currentFieldState.columnIndex.get();
        int prevColumnIndex = currentFieldState.prevColumnIndex;
        int prevRowIndex = currentFieldState.prevRowIndex;
        if (rightMap.get(currentChar).stream()
                .anyMatch(x -> x.equals(matrix.get(currentRowIndex).get(currentColumnIndex + 1)))
                && prevColumnIndex != currentColumnIndex + 1) {
            currentFieldState = updateFieldState(currentFieldState, currentColumnIndex + 1, currentRowIndex,
                    matrix.get(currentRowIndex).get(currentColumnIndex + 1));
        } else if (leftMap.get(currentChar).stream()
                .anyMatch(x -> x.equals(matrix.get(currentRowIndex).get(currentColumnIndex - 1)))
                && prevColumnIndex != currentColumnIndex - 1) {
            currentFieldState = updateFieldState(currentFieldState, currentColumnIndex - 1, currentRowIndex,
                    matrix.get(currentRowIndex).get(currentColumnIndex - 1));
        } else if (upMap.get(currentChar).stream()
                .anyMatch(x -> x.equals(matrix.get(currentRowIndex - 1).get(currentColumnIndex)))
                && prevRowIndex != currentRowIndex - 1) {
            currentFieldState = updateFieldState(currentFieldState, currentColumnIndex, currentRowIndex - 1,
                    matrix.get(currentRowIndex - 1).get(currentColumnIndex));
        } else if (downMap.get(currentChar).stream()
                .anyMatch(x -> x.equals(matrix.get(currentRowIndex + 1).get(currentColumnIndex)))
                && prevRowIndex != currentRowIndex + 1) {
            currentFieldState = updateFieldState(currentFieldState, currentColumnIndex, currentRowIndex + 1,
                    matrix.get(currentRowIndex + 1).get(currentColumnIndex));
        }
        return currentFieldState;
    }

    private static FieldState updateFieldState(FieldState currentFieldState, int newColumnIndex, int newRowIndex,
            Character newCharacter) {
        currentFieldState.currentChar = newCharacter;
        currentFieldState.prevColumnIndex = currentFieldState.columnIndex.get();
        currentFieldState.prevRowIndex = currentFieldState.rowIndex.get();
        currentFieldState.columnIndex.set(newColumnIndex);
        currentFieldState.rowIndex.set(newRowIndex);
        return currentFieldState;

    }
}
