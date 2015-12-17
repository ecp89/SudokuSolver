package com.ecp.sudoku.model;

import java.util.Random;

/**
 * Created by ericpass on 12/12/15.
 */
public enum SudokuDifficulty {
    SIMPLE("Simple"),
    EASY("Easy"),
    INTERMEDIATE("Intermediate"),
    EXPERT("Expert"),
    ANY("Any");

    private String displayString;

     SudokuDifficulty(String displayString){
        this.displayString = displayString;
    }

    public String getDisplayString(){
        return this.displayString;
    }

    public static SudokuDifficulty getForDisplayString(String str){
        if(str.equalsIgnoreCase(ANY.displayString)){
            SudokuDifficulty[] vals = SudokuDifficulty.values();
            return vals[(int)Math.random()*(vals.length-1)];
        }
        for(SudokuDifficulty difficulty: SudokuDifficulty.values()){
            if(difficulty.displayString.equalsIgnoreCase(str)){
                return difficulty;
            }
        }
        System.err.println("NO MATCHING SUDOKU DIFFICULTY");
        return null;
    }

    public static String[] getAllSupportedDifficulties(){
        SudokuDifficulty[] difficulties = SudokuDifficulty.values();
        String[] res = new String[difficulties.length];
        for (int i = 0; i < difficulties.length; i++) {
            res[i] = difficulties[i].displayString;
        }
        return res;
    }

    public static SudokuDifficulty getByDisplayName(String displayString){
        for(SudokuDifficulty difficulty: SudokuDifficulty.values()){
            if(difficulty.displayString.equals(displayString)){
                return difficulty;
            }
        }
        throw new IllegalArgumentException();
    }


}
