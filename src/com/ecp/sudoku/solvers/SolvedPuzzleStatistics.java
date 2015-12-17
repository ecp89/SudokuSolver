package com.ecp.sudoku.solvers;

/**
 * Created by ericpass on 12/17/15.
 */
public class SolvedPuzzleStatistics {
    public int numberOfNodesExplored = 0;
    public String solverName = "";
    public long timeTaken = -1;

    @Override
    public String toString(){
        String res = String.format("[SolverName: %s, NumberOfNodesExplored: %d, Time taken %d ms]",solverName, numberOfNodesExplored, timeTaken/1000000);
        return res;
    }
}
