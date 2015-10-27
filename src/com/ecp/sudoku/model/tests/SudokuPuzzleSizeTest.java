package com.ecp.sudoku.model.tests;

import com.ecp.sudoku.model.SudokuPuzzleSize;
import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by ericpass on 10/27/15.
 */
public class SudokuPuzzleSizeTest extends TestCase {


    @Test
    public void testNineByNineCellPosition() {
        int[][] expected = {
                {1, 4, 7, 1, 4, 7, 1, 4, 7},
                {2, 5, 8, 2, 5, 8, 2, 5, 8},
                {3, 6, 9, 3, 6, 9, 3, 6, 9},
                {1, 4, 7, 1, 4, 7, 1, 4, 7},
                {2, 5, 8, 2, 5, 8, 2, 5, 8},
                {3, 6, 9, 3, 6, 9, 3, 6, 9},
                {1, 4, 7, 1, 4, 7, 1, 4, 7},
                {2, 5, 8, 2, 5, 8, 2, 5, 8},
                {3, 6, 9, 3, 6, 9, 3, 6, 9}};
        assertArrayEquals(expected, SudokuPuzzleSize.NINE_BY_NINE.getCellPositions());
    }

}