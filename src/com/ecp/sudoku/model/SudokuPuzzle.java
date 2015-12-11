/**
 * Created by ericpass on 10/26/15.
 */
package com.ecp.sudoku.model;

import com.ecp.sudoku.solvers.SudokuSolver;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

//TODO this might/should be a singleton
public class SudokuPuzzle {

    protected static SudokuPuzzleSize PUZZLE_SIZE = SudokuPuzzleSize.TWENTYFIVE_BY_TWENTYFIVE;

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
                cells[i][j] = new SudokuCell(puzzelWidth);
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

    public void bulkSetValues(int[][] values){
        for(int i = 0; i<values.length; i++){
            for (int j = 0; j < values.length; j++) {
                cells[i][j].setValue(values[i][j]);
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

    public boolean isValid(){
        int sqrtOfPuzzleWidth = (int)Math.sqrt(puzzleWidth);
        boolean[][] unitAccum = null;
        for (int x = 0; x < puzzleWidth; x++) {
            if(x%sqrtOfPuzzleWidth == 0){
                unitAccum = new boolean[sqrtOfPuzzleWidth][puzzleWidth];
            }
            boolean[] accumColumn = new boolean[puzzleWidth];
            boolean[] accumRow = new boolean[puzzleWidth];
            for (int y = 0; y < puzzleWidth; y++) {
                try{
                    int valueRow = cells[x][y].getValue();
                    int valueColumn = cells[y][x].getValue();

                    //unfinished puzzle
                    if(valueColumn == 0 || valueRow == 0){
                        return false;
                    }

                    //repeat value
                    if(accumColumn[valueColumn-1] || accumRow[valueRow - 1] || unitAccum[y/sqrtOfPuzzleWidth][valueRow - 1]){
                        return false;
                    }


                    accumColumn[valueColumn - 1] = true;
                    accumRow[valueRow - 1] = true;
                    unitAccum[y/sqrtOfPuzzleWidth][valueRow - 1] = true;

                } catch(ArrayIndexOutOfBoundsException e){ // number was bigger than puzzleWidth
                    return false;
                }
            }
        }
        return true;
    }



    public void printPuzzel(){
        for (int i = 0; i < puzzleWidth; i++) {
            for (int j = 0; j < puzzleWidth; j++) {
                System.out.print(cells[i][j].getValue() + " ");
            }
            System.out.print("\n");
        }

    }

    public HashSet<Integer> getValuesInUnitContainingCell(int x, int y){
        HashSet<Integer> res = new HashSet<>();
        int sqrtOfPuzzleWidth = (int)Math.sqrt(puzzleWidth);
        int startX = (x/sqrtOfPuzzleWidth)*sqrtOfPuzzleWidth;
        int startY = (y/sqrtOfPuzzleWidth)*sqrtOfPuzzleWidth;
        for(int i = startX; i<startX+sqrtOfPuzzleWidth;i++){
            for(int j = startY; j<startY+sqrtOfPuzzleWidth; j++){
                res.add(cells[i][j].getValue());
            }

        }
        return res;
    }

    public HashSet<Integer> getValidValuesForCell(int x, int y){
        HashSet<Integer> values = cells[x][y].getSetOfAllPossibleValues();
        if(values.size() == 1){
            return values;
        }
        for(int i = 0; i<puzzleWidth; i++){
            values.remove(cells[i][y].getValue());
            values.remove(cells[x][i].getValue());
        }
        values.removeAll(getValuesInUnitContainingCell(x,y));

        return values;
    }

    public static void setPuzzleSize(SudokuPuzzleSize puzzleSize) {
        PUZZLE_SIZE = puzzleSize;
    }

    public boolean isSetCell(int x, int y){
        return cells[x][y].isInitial();
    }

    public void setValueForCell(int value, int x, int y){
        cells[x][y].setValue(value);
    }


}
