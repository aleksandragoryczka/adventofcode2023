package com.adventofcode.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderUtil {
    public static List<String> readLineByLineToList(String filePath) {
        BufferedReader bufferedReader;
        List<String> lines = new ArrayList<String>();

        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = bufferedReader.readLine();
            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static String[] readLineByLineToArray(String filePath) {
        BufferedReader bufferedReader;
        String[] lines = new String[0];

        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = bufferedReader.readLine();
            List<String> list = new ArrayList<String>();
            while (line != null) {
                list.add(line);
                line = bufferedReader.readLine();
            }
            lines = list.toArray(new String[0]);
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}
