package com.ecp.sudoku.model;

import java.awt.*;

/**
 * Created by ericpass on 10/26/15.
 */
public class SudokuCell {

    private boolean isInital;

    private int value;
    private int maxValue;

    private Rectangle bounds; //This is the square that the suduko cell occupys

    private Point cellLocation;

    public SudokuCell(){
        this.maxValue = SudokuPuzzel.PUZZLE_WIDTH;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setIsInital(boolean isInital) {
        this.isInital = isInital;
    }

    public Point getCellLocation() {
        return cellLocation;
    }

    public void setCellLocation(Point cellLocation) {
        this.cellLocation = cellLocation;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void draw(Graphics g, int x, int y, int width, int cellPosition) {
        g.setColor(Color.WHITE);
        g.fillRect(x,y,width,width); //make background white
        g.setColor(Color.BLACK);
        drawBorder(g,x,y,width,cellPosition);
    }

    private void drawBorder(Graphics g, int x, int y, int width,
                            int cellPosition) {
        switch (cellPosition) {
            case 1:
                drawLeftBorder(g, x, y, width);
                drawTopBorder(g, x, y, width);
                break;
            case 2:
                drawTopBorder(g, x, y, width);
                break;
            case 3:
                drawTopBorder(g, x, y, width);
                drawRightBorder(g, x, y, width);
                break;
            case 4:
                drawLeftBorder(g, x, y, width);
                break;
            case 6:
                drawRightBorder(g, x, y, width);
                break;
            case 7:
                drawLeftBorder(g, x, y, width);
                drawBottomBorder(g, x, y, width);
                break;
            case 8:
                drawBottomBorder(g, x, y, width);
                break;
            case 9:
                drawBottomBorder(g, x, y, width);
                drawRightBorder(g, x, y, width);
                break;
        }
    }

    private void drawTopBorder(Graphics g, int x, int y, int width) {
        g.drawLine(x, y + 1, x + width, y + 1);
        g.drawLine(x, y + 2, x + width, y + 2);
    }

    private void drawRightBorder(Graphics g, int x, int y, int width) {
        g.drawLine(x + width - 1, y, x + width - 1, y + width);
        g.drawLine(x + width - 2, y, x + width - 2, y + width);
    }

    private void drawBottomBorder(Graphics g, int x, int y, int width) {
        g.drawLine(x, y + width - 1, x + width, y + width - 1);
        g.drawLine(x, y + width - 2, x + width, y + width - 2);
    }

    private void drawLeftBorder(Graphics g, int x, int y, int width) {
        g.drawLine(x + 1, y, x + 1, y + width);
        g.drawLine(x + 2, y, x + 2, y + width);
    }

}
