package com.ecp.sudoku.solvers;

import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.view.SudokuFrame;

/**
 * Created by ericpass on 12/8/15.
 */
public abstract class SudokuSolver {

    /**
     * Solves the prefilled in model returning when all of
     * the model is filled in in a valid sudoku
     * @param model
     */
    //TODO This should probably return some stat object like time taken to
    //solve nodes need to evaluate ect
    public abstract SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model, SudokuFrame frame);

    public abstract SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model);

    public abstract String getName();

    public static String[] getAllSupportedSolvers(){
        String[] solvers = new String[2];
        solvers[0] = "NaiveSolver";
        solvers[1] = "PrioritySolver";
        return solvers;

    }



}
