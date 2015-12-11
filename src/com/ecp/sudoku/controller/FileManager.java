package com.ecp.sudoku.controller;

import com.ecp.sudoku.model.SudokuPuzzle;

import java.util.regex.Pattern;

/**
 * Created by ericpass on 11/27/15.
 */
public class FileManager {
    //Note that for puzzel sizes greater than 9 in the puzzel string
    //each number will be seperated by ,
    //Note that a . denotes not given
    private String schema = "size|grade|puzzel_string";

    /**
     * Takes in a String according to the schema and returns a value array to be used
     * by the models bulkSet function.
     * @param input
     * @return
     */
    public static int[][] puzzelStringToValues(String input){
        String[] params = input.split(Pattern.quote("|"));
        int size = Integer.parseInt(params[0]);
        int[][] res = new int[size][size];
        if(size>9){
            String[] numbers = params[2].split(Pattern.quote(","));
            for (int i = 0; i < numbers.length; i++) {
                int num = numbers[i].equals(".") ? 0: Integer.parseInt(numbers[i]);
                res[i/size][i%size] = num;
            }


        } else {
            params[2] = params[2].trim();
            for (int i = 0; i < params[2].length(); i++) {
                //if it is a period default to 0 otherwise using char number get the
                //int value. This asssumes that the string will only contain . and
                //digits
                int num = params[2].charAt(i) == '.' ? 0: params[2].charAt(i) - '0';
                res[i/size][i%size] = num;
            }
        }

        return res;
    }
}
