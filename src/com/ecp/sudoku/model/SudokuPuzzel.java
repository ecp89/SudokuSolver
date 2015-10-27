/**
 * Created by ericpass on 10/26/15.
 */
package com.ecp.sudoku.model;

import java.awt.*;




public class SudokuPuzzel {

    protected static final int PUZZLE_WIDTH = 9;

    private int drawWidth;
    private int puzzleWidth;

    private SudokuCell[][] cells;

    private int[][]         cellPosition    = { { 1, 4, 7, 1, 4, 7, 1, 4, 7 },
            { 2, 5, 8, 2, 5, 8, 2, 5, 8 }, { 3, 6, 9, 3, 6, 9, 3, 6, 9 },
            { 1, 4, 7, 1, 4, 7, 1, 4, 7 }, { 2, 5, 8, 2, 5, 8, 2, 5, 8 },
            { 3, 6, 9, 3, 6, 9, 3, 6, 9 }, { 1, 4, 7, 1, 4, 7, 1, 4, 7 },
            { 2, 5, 8, 2, 5, 8, 2, 5, 8 }, { 3, 6, 9, 3, 6, 9, 3, 6, 9 } };

    public SudokuPuzzel(){
        this.drawWidth = 80; //not sure how this number comes about
        this.puzzleWidth = PUZZLE_WIDTH;
        this.cells = new SudokuCell[puzzleWidth][puzzleWidth];
        set(puzzleWidth);
    }

    private void set(int puzzelWidth){
        for(int i = 0; i< puzzelWidth; i++){
            for(int j = 0; j<puzzelWidth; j++){
                cells[i][j] = new SudokuCell();
                cells[i][j].setCellLocation(new Point(i,j));
            }
        }
    }

    public int getDrawWidth() {
        return drawWidth;
    }

    public static int getPuzzleWidth() {
        return PUZZLE_WIDTH;
    }



    /**
     * The x and y being incremented by the drawWidth makes is so we
     * traverse box to box and have the correct bounds for each cell.
     * @param g
     */
    public void draw(Graphics g){
        int x = 0;
        for(int i = 0; i<puzzleWidth; i++){
            int y = 0;
            for (int j = 0; j < puzzleWidth; j++) {
                Rectangle r = new Rectangle(x, y, drawWidth, drawWidth);
                cells[i][j].setBounds(r);
                cells[i][j].draw(g, x, y, drawWidth, cellPosition[i][j]);
                y+=drawWidth;
            }
            x+=drawWidth;
        }
    }
}
