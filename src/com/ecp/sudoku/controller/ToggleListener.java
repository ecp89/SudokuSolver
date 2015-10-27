package com.ecp.sudoku.controller;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by ericpass on 10/27/15.
 */
public class ToggleListener implements ChangeListener{

    private JToggleButton[] toggleButtons;

    public void setToggleButtons(JToggleButton... buttons){
        toggleButtons = new JToggleButton[buttons.length];
        for (int i = 0; i < buttons.length; i++) {
            toggleButtons[i] = buttons[i];
        }
    }

    /**
     * The purpose of this method is to ensure that when one togglebutton us selected
     * all other buttons are not selected.
     * @param e
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        JToggleButton toggleButton = (JToggleButton) e.getSource();
        if(toggleButton.isSelected()){
            for(JToggleButton button: toggleButtons){
                if(!button.equals(toggleButton)){
                    button.setSelected(false);
                }
            }
        }
    }
}
