package com.ecp.sudoku.model.tests;

import com.ecp.sudoku.model.PuzzleEntity;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.*;

import static org.junit.Assert.*;

/**
 * Created by ericpass on 12/11/15.
 */
public class PuzzleEntityTest {
    private static PuzzleEntity puzzleEntity1;
    private static final String entity1Puzzle ="........5.4........5......972...3.....4.9..383..4..5274.9.57.8.2....4.......381..";
    private static int[][] expectedEntity1 = {
        {0, 0, 0, 7, 0, 3, 4, 2, 0},
        {0, 4, 5, 2, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 4, 0, 9, 0, 0},
        {0, 0, 0, 0, 0, 4, 0, 0, 0},
        {0, 0, 0, 0, 9, 0, 5, 0, 3},
        {0, 0, 0, 3, 0, 0, 7, 4, 8},
        {0, 0, 0, 0, 0, 5, 0, 0, 1},
        {0, 0, 0, 0, 3, 2, 8, 0, 0},
        {5, 0, 9, 0, 8, 7, 0, 0, 0}};

    @Before
    public void init(){
        try {
            puzzleEntity1 = new PuzzleEntity(entity1Puzzle);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetPuzzle() throws Exception {
        int[][] res = puzzleEntity1.getPuzzle();
        for (int i = 0; i < res.length; i++) {
            assertArrayEquals("In iteration "+i,expectedEntity1[i],res[i]);
        }
    }


    @Test
    public void testConvertToString() throws Exception {
        String res = puzzleEntity1.convertToString(expectedEntity1);
        assertEquals(entity1Puzzle, res);}
    }




