package com.adventofcode;

import java.lang.reflect.InvocationTargetException;

import com.adventofcode.utils.AppHelper;

public class App 
{
    public static void main( String[] args )
    {
        try {
            Class<?> dayClass  = Class.forName("com.adventofcode.day" + args[0] + ".Day" + args[0]);
            dayClass.getMethod("task" + args[1], String.class).invoke(null, AppHelper.getAbsolutePathToInput(args));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            System.out.println("There is no solution for task" + args[1] + ". Try to change execution parameter 'exec.task'.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("There is no solution for day" + args[0] + ". Try to change execution parameter 'exec.day'.");
            e.printStackTrace();
        }
  
    }
}
