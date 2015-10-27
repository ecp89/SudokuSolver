package com.ecp.sudoku.model;

/**
 * Created by ericpass on 10/27/15.
 */
public enum SudokuPuzzleSize {
    FOUR_BY_FOUR(4, 80),
    NINE_BY_NINE(9,80),
    SIXTEEN_BY_SIXTEEN(16,80),
    TWENTYFIVE_BY_TWENTYFIVE(25,80);

    private final int puzzelWidth;
    private final int drawWidth;

    SudokuPuzzleSize(int puzzelWidth, int drawWidth){
        this.puzzelWidth = puzzelWidth;
        this.drawWidth = drawWidth;
    }

    public int getPuzzelWidth() {
        return puzzelWidth;
    }

    public int getDrawWidth() {
        return drawWidth;
    }

    public int[][] getCellPositions(){
        int width = this.puzzelWidth;
        int sqrtWidth = (int) Math.sqrt(width);
        int[][] res = new int[width][width];
        int value = 1;
        for(int y = 0; y<width; y++){
            for(int x = 0; x<width; x++) {
                if (x % sqrtWidth == 0) {
                    if (y % sqrtWidth == 0) {
                        value = 1;
                    } else if (y + 1 % sqrtWidth == 0) {
                        value = 7;
                    } else {
                        value = 4;
                    }
                    res[x][y] = value;
                    value++;
                } else
                if (x + 1% sqrtWidth == 0) {
                    if (y % sqrtWidth == 0) {
                        value = 3;
                    } else if (y + 1 % sqrtWidth == 0) {
                        value = 9;
                    } else {
                        value = 6;
                    }
                    res[x][y] = value;
                    value = x + 1 < width ? value : 1;
                } else {
                    res[x][y] = value;
                }
            }
            value = value + 1 > 9? 1 : value + 1;
        }

        return res;
    }
}
