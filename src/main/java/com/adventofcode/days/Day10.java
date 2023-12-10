package com.adventofcode.days;

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
            System.out.println("right");
        } else if (leftMap.get(currentChar).stream()
                .anyMatch(x -> x.equals(matrix.get(currentRowIndex).get(currentColumnIndex - 1)))
                && prevColumnIndex != currentColumnIndex - 1) {
            currentFieldState = updateFieldState(currentFieldState, currentColumnIndex - 1, currentRowIndex,
                    matrix.get(currentRowIndex).get(currentColumnIndex - 1));
            System.out.println("left");
        } else if (upMap.get(currentChar).stream()
                .anyMatch(x -> x.equals(matrix.get(currentRowIndex - 1).get(currentColumnIndex)))
                && prevRowIndex != currentRowIndex - 1) {
            currentFieldState = updateFieldState(currentFieldState, currentColumnIndex, currentRowIndex - 1,
                    matrix.get(currentRowIndex - 1).get(currentColumnIndex));
            System.out.println("up");
            System.out.println("prevColumnIndex: " + prevColumnIndex + " prevRowIndex: " + prevRowIndex);
            System.out.println("nowy char: " + currentChar);
        } else if (downMap.get(currentChar).stream()
                .anyMatch(x -> x.equals(matrix.get(currentRowIndex + 1).get(currentColumnIndex)))
                && prevRowIndex != currentRowIndex + 1) {
            currentFieldState = updateFieldState(currentFieldState, currentColumnIndex, currentRowIndex + 1,
                    matrix.get(currentRowIndex + 1).get(currentColumnIndex));
            System.out.println("prevColumnIndex: " + prevColumnIndex + " prevRowIndex: " + prevRowIndex);
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
