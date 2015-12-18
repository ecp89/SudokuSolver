package com.ecp.sudoku.solvers;

import com.ecp.sudoku.model.SudokuCell;
import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.view.SudokuFrame;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Created by ericpass on 12/18/15.
 */
public class HistogramSolver extends SudokuSolver {

    private int[] valueToFrequency;

    @Override
    protected long setUp(SudokuPuzzle model){
        super.setUp(model);
        final int width = model.getPuzzleWidth();
        valueToFrequency = new int[width+1];
        for (int i = 0; i <width*width; i++) {
            int row = i / width;
            int col = i % width;

            if(model.isSetCell(row, col)){
                valueToFrequency[model.getSudokuCell(row,col).getValue()]++;
            }
        }
        return System.nanoTime();
    }

    @Override
    protected SolvedPuzzleStatistics tearDown(long startTime){
        valueToFrequency = null;
        return super.tearDown(startTime);
    }

    @Override
    public SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model, SudokuFrame frame){
        long startTime = setUp(model);
        histoSolverHelper(model, frame, 0);
        return tearDown(startTime);

    }
    private boolean histoSolverHelper(SudokuPuzzle model, SudokuFrame frame, int index) {
        if(shouldAbort()){
            return true;
        }
        stats.numberOfNodesExplored++;
        final int width = model.getPuzzleWidth();
        if(index == width*width){
            if(frame != null){
                frame.repaintSudokuPanel();
            }
            return model.isValid();
        }

        int row = index / width;
        int col = index % width;

        if(model.isSetCell(row,col)){
            return histoSolverHelper(model, frame, index+1);
        } else {
            stats.numberOfNodesExplored += super.nodesExploredForGettingValidMoves(model.getPuzzleSize());
            HashSet<Integer> validValues = model.getValidValuesForCell(row,col);
            PriorityQueue<PriorityHistogramElement> orderedValidVals = new PriorityQueue<>();
            for(Integer val: validValues){
                orderedValidVals.add(new PriorityHistogramElement(val,valueToFrequency[val]));
            }
            for(PriorityHistogramElement ele: orderedValidVals){
                valueToFrequency[ele.value]++;
                model.setValueForCell(ele.value, row,col);
                if(frame != null){
                    frame.repaintSudokuPanel();
                }
                if(histoSolverHelper(model,frame, index+1)){
                    return true;
                }

                valueToFrequency[ele.value]--;
                model.setValueForCell(0, row, col);

            }
        }

        return false;


    }

    @Override
    public SolvedPuzzleStatistics SolvePuzzle(SudokuPuzzle model){
        long startTime = setUp(model);
        histoSolverHelper(model, null, 0);
        return tearDown(startTime);

    }
    @Override
    public String getName() {
        return  "HistogramSolver";
    }

    class PriorityHistogramElement implements Comparable{
        public int value;
        public int frequency;

        public PriorityHistogramElement(int value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }

        //want to order by least frequent to most frequent
        @Override
        public int compareTo(Object o) {
            return Integer.compare(((PriorityHistogramElement) o).frequency, frequency);
        }
    }
}
