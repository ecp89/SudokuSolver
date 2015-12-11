package com.ecp.sudoku.model;

/**
 * Created by ericpass on 10/27/15.
 */
public enum SudokuPuzzleSize {
    FOUR_BY_FOUR(4, 80, "4 by 4"),
    NINE_BY_NINE(9,80, "9 by 9"),
    SIXTEEN_BY_SIXTEEN(16,44, "16 by 16"),
    TWENTYFIVE_BY_TWENTYFIVE(25,28, "25 by 25");

    private final int puzzleWidth;
    private final int drawWidth;
    private final String displayString;

    SudokuPuzzleSize(int puzzleWidth, int drawWidth, String displayString){
        this.puzzleWidth = puzzleWidth;
        this.drawWidth = drawWidth;
        this.displayString = displayString;
    }

    public int getPuzzleWidth() {
        return puzzleWidth;
    }

    public int getDrawWidth() {
        return drawWidth;
    }

    public String getDisplayString() {
        return displayString;
    }

    public SudokuPuzzleSize getSudokuPuzzleSize(String displayString){
        for(SudokuPuzzleSize sudokuPuzzleSize: SudokuPuzzleSize.values()){
            if(sudokuPuzzleSize.displayString.equals(displayString)){
                return sudokuPuzzleSize;
            }
        }
        assert false : "In getSudokuPuzzleSize could not find value for display string";
        return null;
    }

    public int[][] getCellPositions(){
        int width = this.puzzleWidth;
        int sqrtWidth = (int) Math.sqrt(width);
        int[][] res = new int[width][width];
        int value = 1;
        for(int y = 0; y<width; y++){
            for(int x = 0; x<width; x++) {
                if (x % sqrtWidth == 0) {
                    if (y % sqrtWidth == 0) {
                        value = 1;
                    } else if ( (y + 1) % sqrtWidth == 0) {
                        value = 7;
                    } else {
                        value = 4;
                    }
                    res[x][y] = value;
                    value++;
                } else
                if ((x + 1)% sqrtWidth == 0) {
                    if (y % sqrtWidth == 0) {
                        value = 3;
                    } else if ((y + 1 )% sqrtWidth == 0) {
                        value = 9;
                    } else {
                        value = 6;
                    }
                    res[x][y] = value;
                    value = x + 1 < width ? 1 : width;
                } else {
                    res[x][y] = value;
                }
            }
            value = value + 1 > 9? 1 : value + 1;
        }

        return res;
    }

    public static String[] getAllSupportedSizes(){
        SudokuPuzzleSize[] sizes = SudokuPuzzleSize.values();
        String[] res = new String[sizes.length];
        for (int i = 0; i < sizes.length; i++) {
            res[i] = sizes[i].displayString;
        }
        return res;
    }

    public static SudokuPuzzleSize getByDisplayName(String displayString){
        for(SudokuPuzzleSize size: SudokuPuzzleSize.values()){
            if(size.displayString.equals(displayString)){
                return size;
            }
        }
        throw new IllegalArgumentException();
    }
}
