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
    //Test on if it is finished on an empty puzzle
    public void testValidEmpty(){
        SudokuPuzzle model = new SudokuPuzzle();
        model.setPuzzleSize(SudokuPuzzleSize.FOUR_BY_FOUR);
        int[][] expected = {{0,0,0,0},
                            {0,0,0,0},
                            {0,0,0,0},
                            {0,0,0,0}};
        model.bulkSetValues(expected);
        assertFalse(model.isValid());
    }

    @Test
    //Test on if it is finished on an empty puzzle
    public void testValidComplete(){
        SudokuPuzzle model = new SudokuPuzzle();
        model.setPuzzleSize(SudokuPuzzleSize.FOUR_BY_FOUR);
        int[][] expected = {{1,3,4,2},
                            {2,4,1,3},
                            {3,1,2,4},
                            {4,2,3,1}};
        model.bulkSetValues(expected);
        assertTrue(model.isValid());
    }

    @Test
    //Test to catch multiple values within row
    public void testDupsInRows(){
        SudokuPuzzle model = new SudokuPuzzle();
        model.setPuzzleSize(SudokuPuzzleSize.FOUR_BY_FOUR);
        int[][] expected = {{1,1,1,1},
                            {2,2,2,3},
                            {3,3,3,3},
                            {4,4,4,4}};
        model.bulkSetValues(expected);
        assertFalse(model.isValid());
    }

    @Test
    //Test to catch multiple values within same column
    public void testDupsInCol(){
        SudokuPuzzle model = new SudokuPuzzle();
        model.setPuzzleSize(SudokuPuzzleSize.FOUR_BY_FOUR);
        int[][] expected = {{1,2,3,4},
                            {1,2,3,4},
                            {1,2,3,4},
                            {1,2,3,4}};
        model.bulkSetValues(expected);
        assertFalse(model.isValid());
    }
    @Test
    //Test to catch multiple values within same unit
    public void testIncorrectUnits(){
        SudokuPuzzle model = new SudokuPuzzle();
        model.setPuzzleSize(SudokuPuzzleSize.FOUR_BY_FOUR);
        int[][] expected = {{1,2,3,4},
                            {4,1,2,3},
                            {3,4,1,2},
                            {2,3,4,1}};
        model.bulkSetValues(expected);
        assertFalse(model.isValid());
    }
}