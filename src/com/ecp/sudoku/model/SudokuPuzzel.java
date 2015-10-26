/**
 * Created by ericpass on 10/26/15.
 */
package com.ecp.sudoku.model;


import java.awt.*;

public class SudokuPuzzel {

    private int drawWidth;
    private int puzzelWidth;

    private SudokuCell[][] cells;

    public SudokuPuzzel(){
        this.drawWidth = 80;
        this.puzzelWidth = 9;
        this.cells = new SudokuCell[puzzelWidth][puzzelWidth];
        set(puzzelWidth);
    }

    private void set(int puzzelWidth){
        for(int i = 0; i< puzzelWidth; i++){
            for(int j = 0; j<puzzelWidth; j++){
                cells[i][j] = new SudokuCell();
                cells[i][j].setCellLocation(new Point(i,j));
            }
        }
    }




}
