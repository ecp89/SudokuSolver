package com.ecp.sudoku.view;

import com.ecp.sudoku.controller.SetValueListener;
import com.ecp.sudoku.model.SudokuPuzzle;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ericpass on 10/26/15.
 *
 * You should override swing components iff you will be overriding one of
 * the component methods.
 */
public class SudokuPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private SudokuFrame frame;

    private SudokuPuzzle model;

    public SudokuPanel(SudokuFrame frame, SudokuPuzzle model){
        this.frame = frame;
        this.model = model;
        createPartController();
    }

    private void createPartController() {
        //new JPanel(); //TODO Very not sure why this is here
        int width = model.getDrawWidth() * model.getPuzzleWidth() + 1;
        addMouseListener(new SetValueListener(frame, model));
        setPreferredSize(new Dimension(width, width));

    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        model.draw(g);
    }

    public void setModel(SudokuPuzzle model) {
        this.model = model;
    }
}
