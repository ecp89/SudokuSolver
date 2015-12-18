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
        long startTime = super.setUp(model);
        init(model);
        return startTime;
    }

    private void init(SudokuPuzzle model){
        unsolvedCells = new PriorityQueue<>();
        final int width = model.getPuzzleWidth();
        for (int i = 0; i < width * width; i++) {
            stats.numberOfNodesExplored++;
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
        if(shouldAbort()){
            return true;
        }
        //Increase the number of nodes explored because we are visting this node
        stats.numberOfNodesExplored++;
        //We have explored everything
        if(unsolvedCells.size() == 0){
            if(frame != null){
                frame.repaintSudokuPanel();
            }
            //sanity check that this is a valid puzzle
            return model.isValid();
        }
        CSPEntity currentEntity = unsolvedCells.poll();
        //This was a given clue so skip it
        if(model.isSetCell(currentEntity.row,currentEntity.col)){
            return cspHelper(model,frame);
        } else {
            //For each of the valid assignments
            for(Integer val: currentEntity.validValues){
                //Get a memento of this state incase propagation fails and
                //we need to revert everything
                SudokuCell[][] memento = model.getMemento();
                model.setValueForCell(val, currentEntity.row, currentEntity.col);
                if(frame != null){
                    frame.repaintSudokuPanel();
                }
                PriorityQueue<CSPEntity> effectedCells = getAllAffected(model, currentEntity);
                boolean shouldRestore = false;
                //until we have propagated all the updates
                while(effectedCells.size() != 0){
                    //Get a entity to update
                    CSPEntity currentEffected = effectedCells.poll();
                    //There are no assignments for this and thus we cant solve
                    //the puzzle with the current assignments so restore from
                    //the memento and try a different assignment for this cell
                    if(currentEffected.validValues.size() == 0){
                        shouldRestore = true;
                        break;
                    }

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


    private PriorityQueue<CSPEntity> getAllAffected(SudokuPuzzle model, CSPEntity currentEntity){
        stats.numberOfNodesExplored += nodesExploredForGettingValidMoves(model.getPuzzleSize());
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
