package com.ecp.sudoku.model.tests;

import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.model.SudokuPuzzleSize;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

/**
 * Created by ericpass on 10/28/15.
 */
public class SudokuPuzzleTest {

    @Test
    public void testEmpty(){
        SudokuPuzzle model = new SudokuPuzzle();
        model.setPuzzleSize(SudokuPuzzleSize.FOUR_BY_FOUR);
        int[][] expected = {{0,0,0,0},
                            {0,0,0,0},
                            {0,0,0,0},
                            {0,0,0,0}};
    }
}