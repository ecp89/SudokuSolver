package com.ecp.sudoku.solvers;

import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.view.SudokuFrame;

/**
 * Created by ericpass on 12/8/15.
 * Just do a backtrack search
 */
public class NaiveSolver extends SudokuSolver{



    @Override
    public SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model, SudokuFrame frame) {
        SolvedPuzzleStatistics stats = new SolvedPuzzleStatistics();
        stats.solverName = getName();
        long startTime = System.nanoTime();
        naiveSolverHelper(model, frame, true, 0, stats);
        System.out.println("we done");
        long endTime = System.nanoTime();
        stats.timeTaken = (endTime-startTime);
        return stats;
    }

    @Override
    public String getName() {
        return "NaiveSolver";
    }

    private boolean naiveSolverHelper(SudokuPuzzle model, SudokuFrame frame,boolean shouldPaint, int index, SolvedPuzzleStatistics stats) {
        final int width = model.getPuzzleWidth();
        stats.numberOfNodesExplored ++;

        if(index == width*width){
            if(shouldPaint){
                frame.repaintSudokuPanel();
            }
            return model.isValid();
        }
        int row = index/width;
        int col = index%width;

        if(model.isSetCell(row,col)) {
           return naiveSolverHelper(model,frame,shouldPaint,index+1, stats);
        } else {
            for(Integer validValue:model.getValidValuesForCell(row,col)){
                model.setValueForCell(validValue, row, col);
                if(shouldPaint){
                    frame.repaintSudokuPanel();
                }
                if (naiveSolverHelper(model,frame, shouldPaint, index+1, stats)){
                    return true;
                }
                model.setValueForCell(0, row, col);

            }
        }

        return false;

    }

    @Override
    public SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model){
        SolvedPuzzleStatistics stats = new SolvedPuzzleStatistics();
        stats.solverName = getName();
        long startTime = System.nanoTime();
        naiveSolverHelper(model, null, false, 0, stats);
        long endTime = System.nanoTime();
        stats.timeTaken = (endTime-startTime);
        return stats;

    }

}
