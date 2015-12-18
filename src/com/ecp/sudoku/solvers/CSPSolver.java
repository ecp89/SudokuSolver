package com.ecp.sudoku.solvers;

import com.ecp.sudoku.model.SudokuCell;
import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.view.SudokuFrame;

import java.awt.*;
import java.util.*;


/**
 * Created by ericpass on 12/17/15.
 */
public class CSPSolver extends SudokuSolver {
    Map<MyPoint, MyHashSet> cellToValidValues;

    @Override
    protected long setUp(SudokuPuzzle model) {
        cellToValidValues = new TreeMap<>();
        cellToValidValues

        return super.setUp(model);
    }

    @Override
    protected SolvedPuzzleStatistics tearDown(long startTime) {
        cellToValidValues = null;
        return super.tearDown(startTime);
    }

    @Override
    public SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model, SudokuFrame frame) {
        return null;
    }

    @Override
    public SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model) {
        return null;
    }




    @Override
    public String getName() {
        return "CSPSolver";
    }

    //https://stackoverflow.com/questions/2864840/treemap-sort-by-value
    static <K,V extends Comparable<? super V>>
    SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res = e1.getValue().compareTo(e2.getValue());
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    private Map<MyPoint, MyHashSet> getAllAffected(SudokuPuzzle model, MyPoint currentPoint){
        Map<MyPoint, MyHashSet> res = new TreeMap<>();
        for (int i = 0; i < model.getPuzzleWidth(); i++) {
            SudokuCell currentRowCell = model.getSudokuCell(i,currentPoint.col);

            SudokuCell currentColCell = model.getSudokuCell(currentPoint.row, i);

            if(currentRowCell.getValue() == 0){
                res.put(new MyPoint(i, currentPoint.col, currentRowCell.getCellLocation()),
                        (MyHashSet) model.getValidValuesForCell(i, currentPoint.col));
            }

            if(currentColCell.getValue() == 0){
                res.put(new MyPoint(currentPoint.row, i, currentColCell.getCellLocation()),
                        (MyHashSet) model.getValidValuesForCell(currentPoint.row, i));
            }

        }
        int sqrtOfPuzzleWidth = (int)Math.sqrt(model.getPuzzleWidth());
        int startX = (currentPoint.row/sqrtOfPuzzleWidth)*sqrtOfPuzzleWidth;
        int startY = (currentPoint.col/sqrtOfPuzzleWidth)*sqrtOfPuzzleWidth;
        for(int i = startX; i<startX+sqrtOfPuzzleWidth;i++){
            for(int j = startY; j<startY+sqrtOfPuzzleWidth; j++){
                SudokuCell currentUnitCell = model.getSudokuCell(i,j);
                if(currentUnitCell.getValue() == 0){
                    res.put(new MyPoint(i,j, currentUnitCell.getCellLocation()), (MyHashSet)model.getValidValuesForCell(i,j))
                }
            }

        }
        return res;


    }
    class MyPoint {
        public int row;
        public int col;
        public Point point;

        public MyPoint(int row, int col, Point point) {
            this.row = row;
            this.col = col;
            this.point = point;
        }

        @Override
        public int hashCode() {
            return point.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return point.equals(obj);
        }
    }

    class MyHashSet extends HashSet<Integer> implements Comparable{
        @Override
        public int compareTo(Object o) {
            if (this.size() < ((MyHashSet) o).size()) {
                return -1;
            }
            if (this.size() > ((MyHashSet) o).size()) {
                return 1;
            }
            return 0;
        }
    }

}
