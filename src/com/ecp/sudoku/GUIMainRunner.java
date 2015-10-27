/**
 * Created by ericpass on 10/26/15.
 *
 * This is just boilerplate code to get the GUI Running.
 */

package com.ecp.sudoku;

import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.view.SudokuFrame;

import javax.swing.SwingUtilities;



public class GUIMainRunner implements Runnable{


    @Override
    public void run() {
        new SudokuFrame(new SudokuPuzzle());
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new GUIMainRunner());
    }
}
