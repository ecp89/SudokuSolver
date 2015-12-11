package com.ecp.sudoku.solvers;

import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.view.SudokuFrame;

/**
 * Created by ericpass on 12/8/15.
 */
public interface SudokuSolver {

    /**
     * Solves the prefilled in model returning when all of
     * the model is filled in in a valid sudoku
     * @param model
     */
    void SolvePuzzle(SudokuPuzzle model, SudokuFrame frame);
}
