package com.ecp.sudoku.solvers;

import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.view.SudokuFrame;

/**
 * Created by ericpass on 12/8/15.
 * Just do a DFS search
 */
public class NaiveSolver implements SudokuSolver{


    @Override
    public void SolvePuzzle(SudokuPuzzle model, SudokuFrame frame) {
        naiveSolverHelper(model, frame, 0);
        System.out.println("we done");
    }

    @Override
    public String getName() {
        return "NaiveSolverV1";
    }

    private boolean naiveSolverHelper(SudokuPuzzle model, SudokuFrame frame, int index) {
        final int width = model.getPuzzleWidth();

        if(index == width*width){
            frame.repaintSudokuPanel();
            return model.isValid();
        }
        int row = index/width;
        int col = index%width;

        if(model.isSetCell(row,col)) {
           return naiveSolverHelper(model,frame,index+1);
        } else {
            for(Integer validValue:model.getValidValuesForCell(row,col)){
                model.setValueForCell(validValue, row, col);
                frame.repaintSudokuPanel();
                if (naiveSolverHelper(model,frame, index+1)){
                    return true;
                }
                model.setValueForCell(0, row, col);

            }
        }

        return false;

    }

}
