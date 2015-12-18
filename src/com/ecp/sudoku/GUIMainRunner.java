/**
 * Created by ericpass on 10/26/15.
 *
 * This is just boilerplate code to get the GUI Running.
 */

package com.ecp.sudoku;

import com.ecp.sudoku.model.SudokuDifficulty;
import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.solvers.DoStatRunForSolver;
import com.ecp.sudoku.solvers.SolverContext;
import com.ecp.sudoku.solvers.SudokuSolver;
import com.ecp.sudoku.view.SudokuFrame;

import javax.swing.SwingUtilities;

/**
 * TODO General
 *   Sudoku generator (generate unique, non-unique, and specifically faulty solution states)
 *   Sudoku grader (to determine difficulty)
 *   Logging
 *   No GUI Mode
 *   Drop down of strategy to solve sudoku
 *   Watch AI mode with step function/print what its doing
 *   Interchangable models for how puzzle abstracted (arrays, linked list, double linked, graph, tree ect)
 *   Stat collection mode
 *   Strategy head to head mode
 *
 *   ALL THE ROW AND COL STUFF IS WRONG
 */


public class GUIMainRunner implements Runnable{
    //Command line arguments
    private static final String headlessArg = "-headless";
    private static final String aiNameArg = "-ai=";
    private static final String numTrialsArg = "-trials=";
    private static final String difficultArg = "-diff=";
    private static final String fileOutputArg = "-out=";

    private static final String doRunForArg = "-doRun=";


    private static boolean USE_GUI = true;
    private static boolean DO_RUN = false;
    private static int numberOfTrials = -1;
    private static SudokuSolver solver = null;
    private static SudokuDifficulty difficulty = SudokuDifficulty.SIMPLE;
    private static String fileOutput = null;


    @Override
    public void run() {
        new SudokuFrame(new SudokuPuzzle());
    }

    public static void main(String[] args){
        if(args.length != 0){
            if(args[0].contains(doRunForArg)){
                if (args[0].length() <= doRunForArg.length()) {
                    System.err.println("You need to specify an ai name");
                    System.exit(1);
                }
                String aiName = args[0].substring(doRunForArg.length());
                try {
                    Class aiClass = Class.forName("com.ecp.sudoku.solvers." + aiName);
                    solver = (SudokuSolver) aiClass.newInstance();
                } catch (ClassNotFoundException e) {
                    System.err.printf("Cannot find ai. %s\n", aiName);
                    System.exit(1);
                } catch (InstantiationException | IllegalAccessException e) {
                    System.err.println("An error occurred with making ai");
                    e.printStackTrace();
                }
                DO_RUN = true;

            } else {


                for (String arg : args) {
                    if (arg.equals(headlessArg)) {
                        USE_GUI = false;
                    }
                    if (arg.contains(aiNameArg)) {
                        if (arg.length() <= aiNameArg.length()) {
                            System.err.println("You need to specify an ai name");
                            System.exit(1);
                        }
                        String aiName = arg.substring(aiNameArg.length());
                        try {
                            Class aiClass = Class.forName("com.ecp.sudoku.solvers." + aiName);
                            solver = (SudokuSolver) aiClass.newInstance();
                        } catch (ClassNotFoundException e) {
                            System.err.printf("Cannot find ai. %s\n", aiName);
                            System.exit(1);
                        } catch (InstantiationException | IllegalAccessException e) {
                            System.err.println("An error occurred with making ai");
                            e.printStackTrace();
                        }
                    }
                    if (arg.contains(numTrialsArg)) {
                        if (arg.length() <= numTrialsArg.length()) {
                            System.err.println("You need to specify a number of trial > 0");
                            System.exit(1);
                        }
                        numberOfTrials = Integer.parseInt(arg.substring(numTrialsArg.length()));

                    }
                    if (arg.contains(difficultArg)) {
                        if (arg.length() <= difficultArg.length()) {
                            System.err.println("You need to specify a difficultly");
                            System.exit(1);
                        }
                        SudokuDifficulty diff = SudokuDifficulty.getForDisplayString(arg.substring(difficultArg.length()));
                        if (diff == null) {
                            System.err.println("Could not find difficultly");
                            System.exit(1);
                        }
                        difficulty = diff;
                    }
                    if (arg.contains(fileOutputArg)) {
                        if (arg.length() <= fileOutputArg.length()) {
                            System.err.println("You need to specify an output file name");
                            System.exit(1);
                        }
                        fileOutput = arg.substring(fileOutputArg.length());
                    }
                }
            }
        }
        if(DO_RUN){
            new DoStatRunForSolver(solver).run();
        } else {
            boolean shouldEnd = false;
            if(args.length>0){
                if(solver == null){
                    System.err.println("Missing AI name");
                    shouldEnd = true;
                }
                if(numberOfTrials <0){
                    System.err.println("Missing or invalid number of trials");
                    shouldEnd = true;
                }
                if(fileOutput == null){
                    System.err.println("Missing output file name");
                    shouldEnd = true;
                }

            }
            if(shouldEnd){
                System.err.println("There were one or more problems preventing the run. Quitting...");
                System.exit(1);
            } else {
                if(USE_GUI) {
                    SwingUtilities.invokeLater(new GUIMainRunner());
                } else {
                    SolverContext context = new SolverContext(solver, numberOfTrials, difficulty, fileOutput);
                    context.run();
                }
            }
        }



    }
}
