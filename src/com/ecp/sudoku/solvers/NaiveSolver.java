package com.ecp.sudoku.solvers;

import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.view.SudokuFrame;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Created by ericpass on 12/8/15.
 * Just do a backtrack search
 */
public class NaiveSolver extends SudokuSolver{


    SolvedPuzzleStatistics stats;

    public long setUp(SudokuPuzzle model){
        stats= new SolvedPuzzleStatistics();
        stats.solverName = getName();
        return System.nanoTime();
    }

    public SolvedPuzzleStatistics tearDown(long startTime){
        long endTime = System.nanoTime();
        stats.timeTaken = endTime - startTime;
        SolvedPuzzleStatistics res = stats;
        stats = null;
        return res;
    }


    @Override
    public SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model, SudokuFrame frame) {
        long startTime = setUp(model);
        naiveSolverHelper(model, frame, 0, stats);
        System.out.println("we done");
        return tearDown(startTime);
    }

    @Override
    public String getName() {
        return "NaiveSolver";
    }

    private boolean naiveSolverHelper(SudokuPuzzle model, SudokuFrame frame, int index, SolvedPuzzleStatistics stats) {
        final int width = model.getPuzzleWidth();
        stats.numberOfNodesExplored ++;

        if(index == width*width){
            if(frame != null){
                frame.repaintSudokuPanel();
            }
            return model.isValid();
        }
        int row = index/width;
        int col = index%width;

        if(model.isSetCell(row,col)) {
           return naiveSolverHelper(model,frame,index+1, stats);
        } else {
            for(Integer validValue:model.getValidValuesForCell(row,col)){
                model.setValueForCell(validValue, row, col);
                if(frame != null){
                    frame.repaintSudokuPanel();
                }
                if (naiveSolverHelper(model,frame, index+1, stats)){
                    return true;
                }
                model.setValueForCell(0, row, col);

            }
        }

        return false;

    }

    @Override
    public SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model){
        long startTime = setUp(model);
        naiveSolverHelper(model, null, 0, stats);
        return tearDown(startTime);

    }

}
