package com.ecp.sudoku.solvers;

import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.view.SudokuFrame;

/**
 * Created by ericpass on 12/17/15.
 */
public class BacktrackingSolver extends SudokuSolver {

    @Override
    public SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model, SudokuFrame frame) {
        long startTime = setUp(model);
        solverHelper(model,frame,0);
        return tearDown(startTime);

    }

    @Override
    public SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model) {
        long startTime = setUp(model);
        solverHelper(model,null,0);
        return tearDown(startTime);
    }

    private boolean solverHelper(SudokuPuzzle model, SudokuFrame frame, int index){
        if(shouldAbort()){
            return true;
        }
        stats.numberOfNodesExplored ++;
        final int width = model.getPuzzleWidth();
        if(index == width*width){
            return model.isValid();
        }
        int row = index / width;
        int col = index % width;
        if(model.isSetCell(row,col)){
            return solverHelper(model, frame, index+1);
        } else {
            for (int i = 1; i <= width; i++) {
                if(doesNotConflict(model, i, row, col)){
                    model.setValueForCell(i, row, col);
                    if(frame != null){
                        frame.repaintSudokuPanel();
                    }
                    if(solverHelper(model,frame,index+1)){
                        return true;
                    }
                    model.setValueForCell(0,row,col);
                }
            }
        }

        return false;

    }

    private boolean doesNotConflict(SudokuPuzzle model, int value, int row, int col){
        stats.numberOfNodesExplored += this.nodesExploredForGettingValidMoves(model.getPuzzleSize());
        return model.getValidValuesForCell(row, col).contains(value);
    }

    @Override
    public String getName() {
        return "BacktrackingSolver";
    }
}
