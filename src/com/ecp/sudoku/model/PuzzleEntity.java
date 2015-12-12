package com.ecp.sudoku.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by ericpass on 12/11/15.
 */
public class PuzzleEntity {
    //todo this only works for 9 by 9
    private static final int WIDTH = 9;
    private final int puzzleId;
    private final String puzzleStr;
    private final String puzzleSolution;
    private final String puzzleRating;
    private final Timestamp timestamp;

    public PuzzleEntity(ResultSet rs) throws SQLException {
        if(rs.next()){
            this.puzzleId = rs.getInt("puzzle_id");
            this.puzzleStr = rs.getString("puzzle");
            this.puzzleSolution = rs.getString("solution");
            this.puzzleRating = rs.getString("rating");
            this.timestamp = rs.getTimestamp("creation_time");
            System.out.println("Generated "+this.toString());
        } else {
            this.puzzleId = -1;
            this.puzzleStr = null;
            this.puzzleSolution = null;
            this.puzzleRating = null;
            this.timestamp = null;
            System.err.println("What is happening in PuzzleEntity");
        }


    }
    public PuzzleEntity(String puzzleStr){
        System.err.println("NEVER SHOULD BE CALLED UNLESS TESTING");
        this.puzzleId = -1;
        this.puzzleStr = puzzleStr;
        this.puzzleSolution = null;
        this.puzzleRating = null;
        this.timestamp = null;
    }

    public int getPuzzleId() {
        return puzzleId;
    }

    public String getPuzzleStr() {
        return puzzleStr;
    }

    public String getPuzzleSolution() {
        return puzzleSolution;
    }

    public String getPuzzleRating() {
        return puzzleRating;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    private int[][] getArrayPuzzle(String str){
        int[][] res = new int[WIDTH][WIDTH];
        if(str == null){
            System.err.println("Why are we trying to get an array of a puzzle entity when str is null.");
            return null;
        }
        char[] puzzelEntrys = str.toCharArray();
        int count = 0;
        for(int i = 0; i<WIDTH; i++){
            for (int j = 0; j <WIDTH; j++) {
                res[j][i] = puzzelEntrys[count] == '.'? 0:puzzelEntrys[count]-'0';
                count++;
            }
        }
        return res;

    }

    public int[][] getPuzzle(){
        return getArrayPuzzle(puzzleStr);
    }

    public int[][] getSolution(){
        return getArrayPuzzle(puzzleSolution);
    }

    public static String convertToString(int[][] puzzle){
        StringBuilder stringBuilder = new StringBuilder(puzzle.length*puzzle.length);
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle.length; j++) {
                stringBuilder.append(puzzle[j][i]==0?".":puzzle[j][i]);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "PuzzleEntity{" +
                "puzzleId=" + puzzleId +
                ", puzzleStr='" + puzzleStr + '\'' +
                ", puzzleSolution='" + puzzleSolution + '\'' +
                ", puzzleRating='" + puzzleRating + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
