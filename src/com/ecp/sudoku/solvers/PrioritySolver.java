package com.ecp.sudoku.solvers;

import com.ecp.sudoku.model.SudokuCell;
import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.model.SudokuPuzzleSize;
import com.ecp.sudoku.view.SudokuFrame;

import java.util.*;

/**
 * Created by ericpass on 12/17/15.
 */
public class PrioritySolver extends SudokuSolver {

    PriorityQueue<PrioritySudokuCell> unsolvedQueue;
    Queue<PrioritySudokuCell> solvedQueue;

    @Override
    protected long setUp(SudokuPuzzle model){
        super.setUp(model);
        unsolvedQueue = new PriorityQueue<PrioritySudokuCell>();
        solvedQueue = new LinkedList<PrioritySudokuCell>();
        initQueues(model);
        return System.nanoTime();
    }

    @Override
    protected SolvedPuzzleStatistics tearDown(long startTime){
        SolvedPuzzleStatistics res = super.tearDown(startTime);
        unsolvedQueue = null;
        solvedQueue = null;
        return res;
    }

    @Override
    public SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model, SudokuFrame frame) {
        long startTime = setUp(model);
        solveHelper(model, frame);
        return tearDown(startTime);
    }
    private int count = 0;
    private boolean solveHelper(SudokuPuzzle model, SudokuFrame frame) {
        if(shouldAbort()){
            return true;
        }
        stats.numberOfNodesExplored ++;
        if(unsolvedQueue.size() == 0){
            return model.isValid();
        }

        PrioritySudokuCell currentCell = unsolvedQueue.poll();
        if(currentCell.getCell().isInitial()){
            solveHelper(model, frame);
        }
        int row = currentCell.getCellRow();
        int col = currentCell.getCellCol();
        stats.numberOfNodesExplored += nodesExploredForGettingValidMoves(model.getPuzzleSize());
        for(Integer val: model.getValidValuesForCell(row, col)){
            model.setValueForCell(val, row, col);
            if(frame != null){
                frame.repaintSudokuPanel();
            }
            if(solveHelper(model,frame)){
                return true;
            }
            model.setValueForCell(0,row, col);

        }

        unsolvedQueue.add(currentCell);
        return false;
    }

    private void initQueues(SudokuPuzzle model) {
        final int width = model.getPuzzleWidth();
        for (int i = 0; i < width * width; i++) {
            unsolvedQueue.add(new PrioritySudokuCell(model, i/width, i%width));
        }
    }

    @Override
    public SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model) {
        long startTime = setUp(model);
        solveHelper(model, null);
        return tearDown(startTime);
    }

    @Override
    public String getName() {
        return "PrioritySolver";
    }

    class PrioritySudokuCell implements Comparable{
        private int numAssignements;
        private SudokuCell cell;
        private int cellRow;
        private int cellCol;

        PrioritySudokuCell(SudokuPuzzle model, int row, int col){
            this.cell = model.getSudokuCell(row,col);
            this.cellRow = row;
            this.cellCol = col;
            this.numAssignements =model.getValidValuesForCell(row,col).size();
        }

        public int getNumAssignements() {
            return numAssignements;
        }

        public void setNumAssignements(int numAssignements) {
            this.numAssignements = numAssignements;
        }

        public SudokuCell getCell() {
            return cell;
        }

        public void setCell(SudokuCell cell) {
            this.cell = cell;
        }

        public int getCellRow() {
            return cellRow;
        }

        public void setCellRow(int cellRow) {
            this.cellRow = cellRow;
        }

        public int getCellCol() {
            return cellCol;
        }

        public void setCellCol(int cellCol) {
            this.cellCol = cellCol;
        }

        @Override
        public int compareTo(Object o) {
            if(this.numAssignements >  ((PrioritySudokuCell) o).getNumAssignements() ){
                return 1;
            }
            if(this.numAssignements < ((PrioritySudokuCell) o).getNumAssignements()){
                return -1;
            }
            int res = Integer.compare(this.cellRow, ((PrioritySudokuCell) o).getCellRow());
            if(res>0){
                return 1;
            }
            if(res<0){
                return -1;
            }
            res = Integer.compare(this.cellCol, ((PrioritySudokuCell) o).getCellCol());
            if(res>0){
                return 1;
            }
            if(res<0){
                return -1;
            }
            return 0;
        }
    }


}
