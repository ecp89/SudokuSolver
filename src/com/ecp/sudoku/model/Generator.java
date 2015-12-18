package com.ecp.sudoku.model;

import java.util.*;

/**
 * Created by ericpass on 12/17/15.
 */
public class Generator extends AbstractGenerator {


    public static void generate(SudokuPuzzle model, int numberOfCluesLeft){
        randomSolve(model);
        final int width = model.getPuzzleWidth();
        final int upperIndex = width*width;
        int numberOfRemovals = upperIndex - numberOfCluesLeft;
        createHolesRandomly(model, numberOfRemovals);


    }



    /**
     * Distribute numberOfHoles to keep the number of
     * @param model
     * @param numberOfRemovals
     */
    private static void createHolesSinglyBalanced(SudokuPuzzle model, int numberOfRemovals){

    }

    private static void isBlanaced(){

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
