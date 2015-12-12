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
        if(str.equals(ANY.displayString)){
            SudokuDifficulty[] vals = SudokuDifficulty.values();
            return vals[(int)Math.random()*(vals.length-1)];
        }
        for(SudokuDifficulty difficulty: SudokuDifficulty.values()){
            if(difficulty.displayString.equals(str)){
                return difficulty;
            }
        }
        System.err.println("NO MATCHING SUDOKU DIFFICULTY");
        return null;
    }


}
