package com.ecp.sudoku.view;

import com.ecp.sudoku.controller.ToggleListener;
import com.ecp.sudoku.model.*;
import com.ecp.sudoku.solvers.ForwardCheckingSolver;
import com.ecp.sudoku.solvers.SolvedPuzzleStatistics;
import com.ecp.sudoku.solvers.SudokuSolver;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by ericpass on 10/26/15.
 */
public class ButtonPanel {
    protected static final Insets buttonInsets = new Insets(10,10,0,10);

    private boolean isSolveButtonFirstTime;

    private JToggleButton restPuzzleButton;
    private JToggleButton setValuesButton;
    private JComboBox sizeDropDown;

    private JComboBox solverDropDown;
    private JButton solveButton;

    private JComboBox difficultyDropDown;
    private JButton loadButton;
    private JButton reloadButton;
    private PuzzleEntity lastEntity;

    private JTextField numberOfBoxesToRemove;
    private JButton generateButton;



    private JPanel panel;

    private SudokuFrame frame;

    private SudokuPuzzle model;

    private SudokuSolver solver = new ForwardCheckingSolver();

    public ButtonPanel(SudokuFrame sudokuFrame, SudokuPuzzle model) {
        this.frame = sudokuFrame;
        this.model = model;
        this.isSolveButtonFirstTime = true;
        createPartController();
    }

    private void createPartController() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        int gridy = 0;

        ToggleListener toggleListener = new ToggleListener();

        restPuzzleButton = new JToggleButton("Reset");
        restPuzzleButton.addChangeListener(toggleListener);
        restPuzzleButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(restPuzzleButton.isSelected()){
                    isSolveButtonFirstTime = true;
                    model.init();
                    frame.repaintSudokuPanel();
                    restPuzzleButton.setSelected(false);
                    setValuesButton.setSelected(true);
                }
            }
        });

        addComponent(panel, restPuzzleButton, 0, gridy++, 1, 1, buttonInsets,GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);

        setValuesButton = new JToggleButton("Set Initial Values");
        setValuesButton.addChangeListener(toggleListener);
        setValuesButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.setSetValues(setValuesButton.isSelected());
            }
        });

        addComponent(panel, setValuesButton, 0, gridy++, 1, 1, buttonInsets,GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);
        
        toggleListener.setToggleButtons(restPuzzleButton, setValuesButton);
        
        setValuesButton.setSelected(true);

        sizeDropDown = new JComboBox(SudokuPuzzleSize.getAllSupportedSizes());
        sizeDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String size = (String)cb.getSelectedItem();
                try{
                    model.setPuzzleSize(SudokuPuzzleSize.getByDisplayName(size));

                } catch (IllegalArgumentException excpt){
                    System.err.println("No Valid size for puzzle?");
                }
                restPuzzleButton.doClick();
            }
        });
        sizeDropDown.setSelectedIndex(model.getPuzzleSize().ordinal());

        addComponent(panel, sizeDropDown, 0, gridy++, 1, 1, buttonInsets, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);


        JPanel dummy =new JPanel();
        addComponent(panel, dummy, 0, gridy++, 1, 1, buttonInsets, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);

        solverDropDown = new JComboBox(SudokuSolver.getAllSupportedSolvers());
        solverDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String solverName = (String)cb.getSelectedItem();
                try{
                    Class aiClass = Class.forName("com.ecp.sudoku.solvers."+solverName);
                    solver = (SudokuSolver)aiClass.newInstance();
                } catch (ClassNotFoundException except) {
                    System.err.printf("Cannot find ai. %s\n",solverName);
                    System.exit(1);
                } catch (InstantiationException| IllegalAccessException except) {
                    System.err.println("An error occurred with making ai");
                    except.printStackTrace();
                }
            }
        });

        addComponent(panel, solverDropDown, 0, gridy++, 1, 1, buttonInsets, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);

        //TODO make solver a SwingWorker
        solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SolvedPuzzleStatistics stats = solver.SolvePuzzle(model, frame);
                System.out.println(stats);
            }


        });
        solveButton.setSelected(false);
        addComponent(panel, solveButton, 0, gridy++, 1, 1, buttonInsets, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);

        JPanel dummy1 =new JPanel();
        addComponent(panel, dummy1, 0, gridy++, 1, 1, buttonInsets, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);

        difficultyDropDown = new JComboBox(SudokuDifficulty.getAllSupportedDifficulties());
        difficultyDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String difficulty = (String)cb.getSelectedItem();
                try{
                    model.setDifficulty(SudokuDifficulty.getByDisplayName(difficulty));
                } catch (IllegalArgumentException excpt){
                    System.err.println("No valid difficulty?");
                }
            }
        });
        addComponent(panel, difficultyDropDown, 0, gridy++, 1, 1, buttonInsets, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);

        loadButton = new JButton("Load Puzzle");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    lastEntity = DatabaseManagerSingleton.getRandomPuzzel(model.getDifficulty());
                    if(lastEntity == null) {
                        return;
                    }
                    restPuzzleButton.doClick();
                    model.loadPuzzleFromEntity(lastEntity);
                    frame.repaintSudokuPanel();
                } catch (SQLException e1) {
                    System.err.println("DB error when trying to load puzzle");
                    e1.printStackTrace();
                }

            }


        });
        loadButton.setSelected(false);
        addComponent(panel, loadButton, 0, gridy++, 1, 1, buttonInsets, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);

        reloadButton = new JButton("Reload");
        reloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(lastEntity == null) {
                    return;
                }
                restPuzzleButton.doClick();
                model.loadPuzzleFromEntity(lastEntity);
                frame.repaintSudokuPanel();
            }
        });

        addComponent(panel, reloadButton, 0, gridy++, 1, 1, buttonInsets, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);

        JPanel dummy2 =new JPanel();
        addComponent(panel, dummy2, 0, gridy++, 1, 1, buttonInsets, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);

        numberOfBoxesToRemove = new JTextField();
        addComponent(panel, numberOfBoxesToRemove, 0, gridy++, 1, 1, buttonInsets, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);

        generateButton = new JButton("Generate");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restPuzzleButton.doClick();
                int i = 0;
                try {
                    i = Integer.parseInt(numberOfBoxesToRemove.getText());
                } catch (NumberFormatException excpt){
                    System.err.println("Come on and enter a number");
                }
                Generator.generate(model, i);
                frame.repaintSudokuPanel();
            }
        });

        addComponent(panel, generateButton, 0, gridy++, 1, 1, buttonInsets, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);








    }

    private void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, Insets insets, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0D, 1.0D,anchor, fill, insets,0,0);
        container.add(component, gbc);
    }


    public JPanel getPanel() {
        return panel;
    }
}
