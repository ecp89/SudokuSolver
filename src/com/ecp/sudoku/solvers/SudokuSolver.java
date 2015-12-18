package com.ecp.sudoku.solvers;

import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.model.SudokuPuzzleSize;
import com.ecp.sudoku.view.SudokuFrame;

import java.util.List;

/**
 * Created by ericpass on 12/8/15.
 */
public abstract class SudokuSolver {

    protected SolvedPuzzleStatistics stats;
    private static final int ABORT_DEPTH = 5000000;

    /**
     * Solves the prefilled in model returning when all of
     * the model is filled in in a valid sudoku
     * @param model
     */
    //solve nodes need to evaluate ect
    public abstract SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model, SudokuFrame frame);

    public abstract SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model);

    public abstract String getName();

    public static String[] getAllSupportedSolvers(){
        String[] solvers = new String[5];
        solvers[0] = "ForwardCheckingSolver";
        solvers[1] = "PrioritySolver";
        solvers[2] = "BacktrackingSolver";
        solvers[3] = "HistogramSolver";
        solvers[4] = "CSPSolver";
        return solvers;

    }



    protected long setUp(SudokuPuzzle model){
        stats= new SolvedPuzzleStatistics();
        stats.solverName = getName();
        return System.nanoTime();
    }

    protected SolvedPuzzleStatistics tearDown(long startTime){
        long endTime = System.nanoTime();
        stats.timeTaken = endTime - startTime;
        SolvedPuzzleStatistics res = stats;
        stats = null;
        return res;
    }

    /**
     * This is worked out from the equation (N-1) +(sqrt(N) - 1)(2*sqrt(N))
     * Sanity check is this equals 20 for N=9
     * @param size
     * @return
     */
    protected int nodesExploredForGettingValidMoves(SudokuPuzzleSize size){
        final int width = size.getPuzzleWidth();
        int numInUnit = width - 1;
        int sqrtOfWidth = (int)Math.sqrt(width);
        int rowsAndCol = (sqrtOfWidth - 1)*(2 * sqrtOfWidth);
        return numInUnit + rowsAndCol;
    }

    protected boolean shouldAbort(){
        return stats.numberOfNodesExplored >= ABORT_DEPTH;
    }


}
