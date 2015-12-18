package com.ecp.sudoku.solvers;

import com.apple.concurrent.Dispatch;
import com.ecp.sudoku.model.SudokuCell;
import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.view.SudokuFrame;

import java.awt.*;
import java.util.*;


/**
 * Created by ericpass on 12/17/15.
 */
public class CSPSolver extends SudokuSolver {
    PriorityQueue<CSPEntity> unsolvedCells;

    @Override
    protected long setUp(SudokuPuzzle model) {
        init(model);
        return super.setUp(model);
    }

    private void init(SudokuPuzzle model){
        unsolvedCells = new PriorityQueue<>();
        final int width = model.getPuzzleWidth();
        for (int i = 0; i < width * width; i++) {
            int row =  i / width;
            int col = i % width;
            unsolvedCells.add(new CSPEntity(row, col, model.getValidValuesForCell(row,col)));
        }
    }

    @Override
    protected SolvedPuzzleStatistics tearDown(long startTime) {
        unsolvedCells = null;
        return super.tearDown(startTime);
    }

    @Override
    public SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model, SudokuFrame frame) {
        long startTime = setUp(model);
        cspHelper(model,frame);
        return tearDown(startTime);
    }

    @Override
    public SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model) {
        long startTime = setUp(model);
        cspHelper(model,null);
        return tearDown(startTime);
    }

    private boolean cspHelper(SudokuPuzzle model, SudokuFrame frame){
        stats.numberOfNodesExplored++;
        if(unsolvedCells.size() == 0){
            if(frame != null){
                frame.repaintSudokuPanel();
            }
            return model.isValid();
        }
        CSPEntity currentEntity = unsolvedCells.poll();
        if(model.isSetCell(currentEntity.row,currentEntity.col)){
            return cspHelper(model,frame);
        } else {
            for(Integer val: currentEntity.validValues){
                SudokuCell[][] memento = model.getMemento();
                model.setValueForCell(val, currentEntity.row, currentEntity.col);
                PriorityQueue<CSPEntity> effectedCells = getAllAffected(model, currentEntity);
                boolean shouldRestore = false;
                while(effectedCells.size() != 0){
                    CSPEntity currentEffected = effectedCells.poll();
                    if(currentEffected.validValues.size() == 0){
                        shouldRestore = true;
                        break;
                    }
                    if(currentEffected.validValues.size() == 0){
                        for(Integer singleEffected: currentEffected.validValues){
                            model.setValueForCell(singleEffected, currentEffected.row,currentEffected.col);
                            effectedCells.addAll(getAllAffected(model,currentEffected));
                        }
                    }
                    //This needs to be verified that we are removing it
                    unsolvedCells.remove(currentEffected);
                    unsolvedCells.add(currentEffected);
                }
                if(shouldRestore){
                    model.restoreFromMemento(memento);
                } else if(cspHelper(model,frame)){
                    return true;
                }
                model.setValueForCell(0,currentEntity.row,currentEntity.col);
            }
        }
        return false;
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

    private PriorityQueue<CSPEntity> getAllAffected(SudokuPuzzle model, CSPEntity currentEntity){
        PriorityQueue<CSPEntity> res = new PriorityQueue<>();
        for (int i = 0; i < model.getPuzzleWidth(); i++) {
            SudokuCell currentRowCell = model.getSudokuCell(i,currentEntity.col);

            SudokuCell currentColCell = model.getSudokuCell(currentEntity.row, i);

            if(currentRowCell.getValue() == 0){
                res.add(new CSPEntity(i, currentEntity.col, model.getValidValuesForCell(i,currentEntity.col)));
            }

            if(currentColCell.getValue() == 0){
                res.add(new CSPEntity(currentEntity.row, i, model.getValidValuesForCell(currentEntity.row,i)));
            }

        }
        int sqrtOfPuzzleWidth = (int)Math.sqrt(model.getPuzzleWidth());
        int startX = (currentEntity.row/sqrtOfPuzzleWidth)*sqrtOfPuzzleWidth;
        int startY = (currentEntity.col/sqrtOfPuzzleWidth)*sqrtOfPuzzleWidth;
        for(int i = startX; i<startX+sqrtOfPuzzleWidth;i++){
            for(int j = startY; j<startY+sqrtOfPuzzleWidth; j++){
                SudokuCell currentUnitCell = model.getSudokuCell(i,j);
                if(currentUnitCell.getValue() == 0){
                    res.add(new CSPEntity(i,j, model.getValidValuesForCell(i,j)));
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

    class CSPEntity implements Comparable{
        public int row;
        public int col;
        public HashSet<Integer> validValues;

        public CSPEntity(int row, int col, HashSet<Integer> validValues) {
            this.row = row;
            this.col = col;
            this.validValues = validValues;
        }

        @Override
        public int compareTo(Object o) {
            if(this.validValues.size() > ((CSPEntity) o).validValues.size()){
                return 1;
            }
            if(this.validValues.size() < ((CSPEntity) o).validValues.size()){
                return -1;
            }
            int res = Integer.compare(row, ((CSPEntity) o).row);
            if(res>0){
                return 1;
            }
            if(res<0){
                return -1;
            }
            res = Integer.compare(col, ((CSPEntity) o).col);
            if(res>0){
                return 1;
            }
            if(res<0){
                return -1;
            }
            return 0;

        }

        @Override
        public boolean equals(Object o){
            return ((CSPEntity) o).row == row && ((CSPEntity) o).col == col;
        }
    }

}
