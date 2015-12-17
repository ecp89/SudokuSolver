package com.ecp.sudoku.solvers;

import com.ecp.sudoku.model.DatabaseManagerSingleton;
import com.ecp.sudoku.model.PuzzleEntity;
import com.ecp.sudoku.model.SudokuDifficulty;
import com.ecp.sudoku.model.SudokuPuzzle;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;


/**
 * Created by ericpass on 12/12/15.
 */
public class SolverContext {
    private static final String FILE_HEADER = "Ai Name,Puzzle id,Difficulty,Nodes Explored,Time\n";

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

        try{
            FileWriter writer  = new FileWriter(fileName);
            writer.append(FILE_HEADER);
            this.model = new SudokuPuzzle();
            DatabaseManagerSingleton manager = DatabaseManagerSingleton.getInstance();
            for(int i=0; i<numberTrials; i++){
                try {
                    PuzzleEntity entity = manager.getRandomPuzzel(difficulty);
                    model.loadPuzzleFromEntity(entity);
                   SolvedPuzzleStatistics stats =  solver.SolvePuzzle(model);
                    writer.append(toOutput(entity,stats));
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




}
