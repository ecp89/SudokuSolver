package com.ecp.sudoku.model;

import java.util.Random;

/**
 * Created by ericpass on 12/18/15.
 */
public class AbstractGenerator {

    private static final Random random = new Random();

    protected static void createHolesRandomly(SudokuPuzzle model, int numberOfRemovals){
        final int width = model.getPuzzleWidth();
        final int upperIndex = width*width;
        int randomIndex = 0;
        while(numberOfRemovals != 0){
            randomIndex = random.nextInt(upperIndex);
            int row = randomIndex / width;
            int col = randomIndex % width;
            SudokuCell currentCell = model.getSudokuCell(row, col);
            if(currentCell.isInitial()){
                currentCell.setIsInitial(false);
                currentCell.setValue(0);
                numberOfRemovals--;
                model.setSudokuCell(currentCell, row, col);
            }

        }
    }
}
