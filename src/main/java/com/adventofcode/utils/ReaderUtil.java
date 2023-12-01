package com.adventofcode.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderUtil {
    public static List<String> readLinyByLineToList(String filePath){
        BufferedReader bufferedReader;
        List<String> lines = new ArrayList<String>();
        
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = bufferedReader.readLine();
            while(line != null){
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
    
}
