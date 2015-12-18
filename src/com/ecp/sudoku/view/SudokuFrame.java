package com.ecp.sudoku.view;

import com.ecp.sudoku.model.SudokuPuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by ericpass on 10/26/15.
 */
public class SudokuFrame {

    private ButtonPanel buttonPanel;

    private JFrame frame;

    private SudokuPanel sudokuPanel;

    private SudokuPuzzle model;

    public SudokuFrame(SudokuPuzzle model){
        this.model = model;
        createPartControl();

    }

    private void createPartControl() {
        sudokuPanel = new SudokuPanel(this, model);
        buttonPanel = new ButtonPanel(this, model);
        
        frame = new JFrame();
        frame.setTitle("Sudoku Puzzle ForwardCheckingSolver By Eric Pass");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitProcedure();
            }
        });
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(sudokuPanel);


        //This should probably go in the ButtonPanel class
        JPanel holderPanel = new JPanel();          // |
        holderPanel.setLayout(new FlowLayout());    // |
        holderPanel.add(buttonPanel.getPanel());    // |
        mainPanel.add(holderPanel);                 // |

        frame.setLayout(new FlowLayout());
        frame.add(mainPanel);
        frame.pack();
        frame.setBounds(getBounds());
        frame.setVisible(true);
        

    }
    
    //might not actually need this
    private void exitProcedure() {
        frame.dispose();
        System.exit(0);
    }


    //This might need to be made protected
    private Rectangle getBounds() {
        Rectangle f = frame.getBounds();
        Rectangle w = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        f.x = (w.width - f.width) / 2 ;
        f.y = (w.height - f.height) / 2;
        return f;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getSudokuPanel(){
        return sudokuPanel;
    }

    public void repaintSudokuPanel(){
        sudokuPanel.repaint();
    }

    public void paintImmediately(){
        sudokuPanel.repaint(10);
    }

    public void setModel(SudokuPuzzle model) {
        this.model = model;

    }
}
