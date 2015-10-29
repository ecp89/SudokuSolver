/**
 * Created by ericpass on 10/26/15.
 */
package com.ecp.sudoku.model;

import java.awt.*;

//TODO this might/should be a singleton
public class SudokuPuzzle {

    protected static SudokuPuzzleSize PUZZLE_SIZE = SudokuPuzzleSize.NINE_BY_NINE;

    private boolean isSetValues;

    private int drawWidth;
    private int puzzleWidth;

    private SudokuCell[][] cells;

    private int[][] cellPosition = PUZZLE_SIZE.getCellPositions();

    public SudokuPuzzle(){
        this.drawWidth = PUZZLE_SIZE.getDrawWidth(); //not sure how this number comes about
        this.puzzleWidth = PUZZLE_SIZE.getPuzzleWidth();
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

    public void init() {
        for (int i = 0; i < puzzleWidth; i++) {
            for (int j = 0; j < puzzleWidth; j++) {
                cells[i][j].init(puzzleWidth);
            }
        }
    }



    public int getDrawWidth() {
        return drawWidth;
    }

    public boolean isSetValues() {
        return isSetValues;
    }

    public void setIsSetValues(boolean isSetValues) {
        this.isSetValues = isSetValues;
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

    public SudokuCell getSudokuCellLocation(Point point) {
        for(int i = 0; i < puzzleWidth; i++){
            for(int j = 0; j < puzzleWidth; j++){
                if(cells[i][j].contains(point)){
                    return cells[i][j];
                }
            }
        }

        return null;
    }

    public void setSetValues(boolean isSetValues) {
        this.isSetValues = isSetValues;
    }

    public int getPuzzleWidth() {
        return puzzleWidth;
    }

    public boolean checkIfFinished(){
        int sqrtOfPuzzleWidth = (int)Math.sqrt(puzzleWidth);
        int[][] unitAccum = null;
        for (int x = 0; x < puzzleWidth; x++) {
            if(x%sqrtOfPuzzleWidth == 0){
                unitAccum = new int[sqrtOfPuzzleWidth][puzzleWidth];
            }
            int[] accumColumn = new int[puzzleWidth];
            int[] accumRow = new int[puzzleWidth];
            for (int y = 0; y < puzzleWidth; y++) {
                try{
                    int valueColumn = cells[x][y].getValue();
                    int valueRow = cells[y][x].getValue();

                    //repeat value
                    if(accumColumn[valueColumn-1] == 1 || accumRow[valueRow - 1] == 1 || unitAccum[y/3][valueColumn - 1] == 1){
                        return false;
                    }
                    //unfinished puzzle
                    if(valueColumn == 0 || valueRow == 0){
                        return false;
                    }

                    accumColumn[valueColumn - 1] = 1;
                    accumRow[valueRow - 1] = 1;
                    unitAccum[y/3][valueColumn - 1] = 1;

                } catch(ArrayIndexOutOfBoundsException e){ // number was bigger than puzzleWidth
                    return false;
                }
            }
        }
        return true;
    }

    public static void setPuzzleSize(SudokuPuzzleSize puzzleSize) {
        PUZZLE_SIZE = puzzleSize;
    }
}
