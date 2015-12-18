/**
 * Created by ericpass on 10/26/15.
 */
package com.ecp.sudoku.model;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;

//TODO this might/should be a singleton
public class SudokuPuzzle {

    protected static final SudokuPuzzleSize DEFAULT_PUZZLE_SIZE = SudokuPuzzleSize.NINE_BY_NINE;

    private SudokuPuzzleSize puzzleSize = DEFAULT_PUZZLE_SIZE;

    private boolean isSetValues;

    private SudokuCell[][] cells;

    private int[][] cellPosition;

    private PuzzleEntity entity;

    private SudokuDifficulty difficulty = SudokuDifficulty.SIMPLE;

    /**
     * This is the default constructor so since no size was specified
     * fall back to using the default size;
     */
    public SudokuPuzzle(){
        this(null);
    }

    public SudokuPuzzle(SudokuPuzzleSize puzzleSize){
        if(puzzleSize == null){
            this.puzzleSize = DEFAULT_PUZZLE_SIZE;
            this.cellPosition = DEFAULT_PUZZLE_SIZE.getCellPositions();
        } else {
            this.puzzleSize = puzzleSize;
            this.cellPosition = puzzleSize.getCellPositions();
        }

        set();


    }

    private void set(){
        this.cells = new SudokuCell[this.puzzleSize.getPuzzleWidth()][this.puzzleSize.getPuzzleWidth()];
        for(int i = 0; i< puzzleSize.getPuzzleWidth(); i++){
            for(int j = 0; j<puzzleSize.getPuzzleWidth(); j++){
                cells[i][j] = new SudokuCell(puzzleSize.getPuzzleWidth(), puzzleSize);
                cells[i][j].setCellLocation(new Point(i,j));
            }
        }
    }

    public void init() {
        for (int i = 0; i < puzzleSize.getPuzzleWidth(); i++) {
            for (int j = 0; j < puzzleSize.getPuzzleWidth(); j++) {
                cells[i][j].init(puzzleSize.getPuzzleWidth());
            }
        }
    }

    public void bulkSetValues(int[][] values){
        for(int i = 0; i<values.length; i++){
            for (int j = 0; j < values.length; j++) {
                cells[i][j].setValue(values[i][j]);
                cells[i][j].setIsInitial(values[i][j]!=0);
            }
        }
    }


    public int getDrawWidth() {
        return puzzleSize.getDrawWidth();
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
        for(int i = 0; i<puzzleSize.getPuzzleWidth(); i++){
            int y = 0;
            for (int j = 0; j < puzzleSize.getPuzzleWidth(); j++) {
                Rectangle r = new Rectangle(x, y, puzzleSize.getDrawWidth(), puzzleSize.getDrawWidth());
                cells[i][j].setBounds(r);
                cells[i][j].draw(g, x, y, puzzleSize.getDrawWidth(), cellPosition[i][j]);
                y+=puzzleSize.getDrawWidth();
            }
            x+=puzzleSize.getDrawWidth();
        }
    }

    public SudokuCell getSudokuCellLocation(Point point) {
        for(int i = 0; i < puzzleSize.getPuzzleWidth(); i++){
            for(int j = 0; j < puzzleSize.getPuzzleWidth(); j++){
                if(cells[i][j].contains(point)){
                    return cells[i][j];
                }
            }
        }

        return null;
    }

    public SudokuCell getSudokuCell(int row, int col){
        return this.cells[row][col];
    }

    public void setSetValues(boolean isSetValues) {
        this.isSetValues = isSetValues;
    }

    public int getPuzzleWidth() {
        return puzzleSize.getPuzzleWidth();
    }

    public boolean isValid(){
        int sqrtOfPuzzleWidth = (int)Math.sqrt(puzzleSize.getPuzzleWidth());
        boolean[][] unitAccum = null;
        for (int x = 0; x < puzzleSize.getPuzzleWidth(); x++) {
            if(x%sqrtOfPuzzleWidth == 0){
                unitAccum = new boolean[sqrtOfPuzzleWidth][puzzleSize.getPuzzleWidth()];
            }
            boolean[] accumColumn = new boolean[puzzleSize.getPuzzleWidth()];
            boolean[] accumRow = new boolean[puzzleSize.getPuzzleWidth()];
            for (int y = 0; y < puzzleSize.getPuzzleWidth(); y++) {
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
        for (int i = 0; i < puzzleSize.getPuzzleWidth(); i++) {
            for (int j = 0; j < puzzleSize.getPuzzleWidth(); j++) {
                System.out.print(cells[i][j].getValue() + " ");
            }
            System.out.print("\n");
        }

    }

    public HashSet<Integer> getValuesInUnitContainingCell(int x, int y){
        HashSet<Integer> res = new HashSet<>();
        int sqrtOfPuzzleWidth = (int)Math.sqrt(puzzleSize.getPuzzleWidth());
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
        for(int i = 0; i<puzzleSize.getPuzzleWidth(); i++){
            values.remove(cells[i][y].getValue());
            values.remove(cells[x][i].getValue());
        }
        values.removeAll(getValuesInUnitContainingCell(x,y));

        return values;
    }

    public void setPuzzleSize(SudokuPuzzleSize puzzleSize) {
        this.puzzleSize = puzzleSize;
        this.cellPosition = puzzleSize.getCellPositions();
        set();
    }

    public boolean isSetCell(int x, int y){
        return cells[x][y].isInitial();
    }

    public void setValueForCell(int value, int x, int y){
        cells[x][y].setValue(value);
    }

    public SudokuPuzzleSize getPuzzleSize(){
        return this.puzzleSize;
    }

    public void loadPuzzleFromEntity(PuzzleEntity entity){
        this.entity = entity;
        bulkSetValues(this.entity.getPuzzle());
    }

    public SudokuDifficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(SudokuDifficulty difficulty){
        this.difficulty = difficulty;
    }

    public void setIsInitialForCell(boolean isInitial, int row, int col){
        this.cells[row][col].setIsInitial(isInitial)
        ;
    }

    public void setSudokuCell(SudokuCell cell, int row, int col){
        this.cells[row][col] = cell;
    }

    public SudokuCell[][] getMemento(){
        SudokuCell[][] res = new SudokuCell[getPuzzleWidth()][getPuzzleWidth()];
        for (int i = 0; i < res.length; i++) {
            res[i] = Arrays.copyOf(cells[i], cells[i].length);
        }
        return res;
    }

    public void restoreFromMemento(SudokuCell[][] cells){
        this.cells = cells;
    }

}
