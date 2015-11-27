package com.ecp.sudoku.model.tests;

import com.ecp.sudoku.model.SudokuPuzzleSize;
import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * The purpose of this test is just to check that we generate the correct
 * generaic "cellPosition" arrays which are used to render the gui. Particularly,
 * it is for the thick lines in the grid.
 */


/**
 * Created by ericpass on 10/27/15.
 */
public class SudokuPuzzleSizeTest {


    @Test
    public void testFourByFourCellPosition(){
        int[][] expected = {
                {1,7,1,7},
                {3,9,3,9},
                {1,7,1,7},
                {3,9,3,9}
        };
        assertArrayEquals(expected, SudokuPuzzleSize.FOUR_BY_FOUR.getCellPositions());

    }

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

    @Test
    public void testSixteenBySixteen() {
        int[][] expected = {
                {1, 4, 4, 7, 1, 4, 4, 7, 1, 4, 4, 7, 1, 4, 4, 7},
                {2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8},
                {2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8},
                {3, 6, 6, 9, 3, 6, 6, 9, 3, 6, 6, 9, 3, 6, 6, 9},
                {1, 4, 4, 7, 1, 4, 4, 7, 1, 4, 4, 7, 1, 4, 4, 7},
                {2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8},
                {2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8},
                {3, 6, 6, 9, 3, 6, 6, 9, 3, 6, 6, 9, 3, 6, 6, 9},
                {1, 4, 4, 7, 1, 4, 4, 7, 1, 4, 4, 7, 1, 4, 4, 7},
                {2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8},
                {2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8},
                {3, 6, 6, 9, 3, 6, 6, 9, 3, 6, 6, 9, 3, 6, 6, 9},
                {1, 4, 4, 7, 1, 4, 4, 7, 1, 4, 4, 7, 1, 4, 4, 7},
                {2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8},
                {2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8, 2, 5, 5, 8},
                {3, 6, 6, 9, 3, 6, 6, 9, 3, 6, 6, 9, 3, 6, 6, 9}};
        assertArrayEquals(expected, SudokuPuzzleSize.SIXTEEN_BY_SIXTEEN.getCellPositions());
    }
}