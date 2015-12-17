package com.ecp.sudoku.solvers;

import com.ecp.sudoku.model.DatabaseManagerSingleton;
import com.ecp.sudoku.model.PuzzleEntity;
import com.ecp.sudoku.model.SudokuDifficulty;
import com.ecp.sudoku.model.SudokuPuzzle;

import java.sql.SQLException;


/**
 * Created by ericpass on 12/12/15.
 */
public class SolverContext {

    //TODO no size
    private SudokuPuzzle model;
    private SudokuSolver solver;
    private int numberTrials;
    private SudokuDifficulty difficulty;
    private String fileName;

    public SolverContext(SudokuSolver solver, int numberTrials, SudokuDifficulty difficulty, String fileName){
        this.solver = solver;
        this.numberTrials =numberTrials;
        this.difficulty = difficulty;
        this.fileName = fileName;
    }

    public void run(){
         this.model = new SudokuPuzzle();
        DatabaseManagerSingleton manager = DatabaseManagerSingleton.getInstance();
        for(int i=0; i<numberTrials; i++){
            try {
                PuzzleEntity entity = manager.getRandomPuzzel(difficulty);
                model.loadPuzzleFromEntity(entity);
                solver.SolvePuzzle(model);
            } catch (SQLException e) {
                System.err.println("A SQL error happened here's the stack trace.");
                e.printStackTrace();
            }
        }

    }


}
