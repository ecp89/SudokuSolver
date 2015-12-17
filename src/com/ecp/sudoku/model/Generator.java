package com.ecp.sudoku.model;

import java.util.*;

/**
 * Created by ericpass on 12/17/15.
 */
public class Generator {
    private static final Random random = new Random();

    public static void generate(SudokuPuzzle model, int numberOfRemovals){
        randomSolve(model);
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



    private static boolean randomSolveHelper(SudokuPuzzle model, int index){
        final int width = model.getPuzzleWidth();
        if(index == width*width) {
            return model.isValid();
        }


        int row =  index/width;
        int col = index%width;


        //pick a random valid assignment for this cell
        ArrayList<Integer>  validAssignment = new ArrayList<>(model.getValidValuesForCell(row,col));
        Collections.shuffle(validAssignment);
        for(Integer i: validAssignment){
            model.setValueForCell(i,row,col);
            model.setIsInitialForCell(true, row, col);
            if(randomSolveHelper(model,index+1)){
                return true;
            }
            model.setIsInitialForCell(false, row, col);
            model.setValueForCell(0,row,col);


        }

        return false;
    }

    public static void randomSolve(SudokuPuzzle model){

        randomSolveHelper(model, 0);

    }




}
