package com.ecp.sudoku.controller;

import com.ecp.sudoku.model.SudokuCell;
import com.ecp.sudoku.model.SudokuPuzzle;
import com.ecp.sudoku.view.SudokuFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by ericpass on 10/26/15.
 * TODO might want to check out extending MouseAdapter class
 */
public class SetValueListener implements MouseListener {

    private SudokuFrame frame;

    private SudokuPuzzle model;


    public SetValueListener(SudokuFrame frame, SudokuPuzzle model) {
        this.frame = frame;
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (model.isSetValues()){
            SudokuCell sudokuCell = model.getSudokuCellLocation(e.getPoint());
            if(sudokuCell != null){
                int value = getValue(sudokuCell);
                if(value > 0){
                    sudokuCell.setValue(value);
                    sudokuCell.setIsInitial(true);
                    frame.repaintSudokuPanel();
                }
            }
        }

    }

    private int getValue(SudokuCell sudokuCell){
        int value = 0;
        while(value == 0){
            //TODO when refactor to larger size boards change prompt
            String inputValue = JOptionPane.showInputDialog(frame.getFrame(), "Type a value from 1 to 9");
            if(inputValue == null){
                return 0;
            }

            try {
                value = Integer.parseInt(inputValue);
                value = testValue(sudokuCell, value);

            } catch (NumberFormatException e){
                value = 0;
            }
        }
        return value;
    }

    //TODO need to make way more robust and general for larger puzzels
    private int testValue(SudokuCell sudokuCell, int value){
        return (value < 1 || value > 9) ? 0 : value;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
