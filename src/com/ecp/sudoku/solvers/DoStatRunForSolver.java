
package com.ecp.sudoku.solvers;

import com.ecp.sudoku.model.DatabaseManagerSingleton;
import com.ecp.sudoku.model.PuzzleEntity;
import com.ecp.sudoku.model.SudokuDifficulty;
import com.ecp.sudoku.model.SudokuPuzzle;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by ericpass on 12/18/15.
 */
public class DoStatRunForSolver {
    private static final String FILE_HEADER = "Ai Name,Puzzle id,Difficulty,Nodes Explored,Time\n";
    private static final int TIMEOUT = 2; //5 seconds
    private static final int NUMBER_SIMPLE = 200;
    private static final int NUMBER_EASY = 200;
    private static final int NUMBER_INTERMEDIATE = 200;
    private static final int NUMBER_EXPERT = 400;
    private static final int TOTAL_TESTS = NUMBER_SIMPLE + NUMBER_EASY + NUMBER_INTERMEDIATE + NUMBER_EXPERT;
    private int phase = 0;
    private String FILE_NAME;
    private SudokuPuzzle model;
    private SudokuSolver solver;

    public DoStatRunForSolver(SudokuSolver solver){
        this.solver = solver;
        this.FILE_NAME = solver.getName() + "_RUN.csv";

    }



    public void run(){

        try{
            FileWriter writer  = new FileWriter(FILE_NAME);
            writer.append(FILE_HEADER);
            this.model = new SudokuPuzzle();
            DatabaseManagerSingleton manager = DatabaseManagerSingleton.getInstance();
            for(RunPhases phase: RunPhases.values()) {
                try {

                    PuzzleEntity[] entitys = manager.getBulk(phase.getDifficulty(), phase.getNumRuns());
                    for(PuzzleEntity E: entitys){
                        model.loadPuzzleFromEntity(E);
                        SolvedPuzzleStatistics stats =  solver.SolvePuzzle(model);
                        writer.append(toOutput(E,stats));
                    }

                } catch (SQLException e) {
                    System.err.println("A SQL error happened here's the stack trace.");
                    e.printStackTrace();
                }
            }
            try{
                writer.flush();
                writer.close();
            } catch (IOException e){
                System.err.println("Problems closing and flushing");
            }
        } catch (IOException e) {
            System.err.println("Could not create file.");
            e.printStackTrace();
            System.exit(1);
        }
    }







    private static String toOutput(PuzzleEntity entity, SolvedPuzzleStatistics stats){
        String res = String.format("%s,%d,%s,%d,%d\n", stats.solverName, entity.getPuzzleId(),
                entity.getPuzzleRating(),stats.numberOfNodesExplored, stats.timeTaken);
        return res;

    }

    enum RunPhases{
        SIMPLE(SudokuDifficulty.SIMPLE, 200),
        EASY(SudokuDifficulty.EASY, 200),
        INTERMEDIATE(SudokuDifficulty.INTERMEDIATE, 200),
        EXPERT(SudokuDifficulty.EXPERT, 400);

        private SudokuDifficulty difficulty;
        private int numRuns;
        RunPhases(SudokuDifficulty difficulty, int numRuns) {
            this.difficulty = difficulty;
            this.numRuns = numRuns;
        }

        public SudokuDifficulty getDifficulty() {
            return difficulty;
        }

        public int getNumRuns() {
            return numRuns;
        }
    }
}


