/**
 * Created by ericpass on 10/26/15.
 *
 * This is just boilerplate code to get the GUI Running.
 */

package com.ecp.sudoku;

import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.view.SudokuFrame;

import javax.swing.SwingUtilities;

/**
 * TODO General
 *   Sudoku generator (generate unique, non-unique, and specifically faulty solution states)
 *   Sudoku grader (to determine difficulty)
 *   DB to save specific problems (inital problem, solution(maybe), difficulty, AI name, date solved, time taken to sol)
 *   Logging
 *   No GUI Mode
 *   Drop down of strategy to solve sudoku
 *   Sudoku Size drop down in GUI
 *   Watch AI mode with step function/print what its doing
 *   Interchangable models for how puzzle abstracted (arrays, linked list, double linked, graph, tree ect)
 *   Stat collection mode
 *   Strategy head to head mode
 */


public class GUIMainRunner implements Runnable{


    @Override
    public void run() {
        new SudokuFrame(new SudokuPuzzle());
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new GUIMainRunner());
    }
}
