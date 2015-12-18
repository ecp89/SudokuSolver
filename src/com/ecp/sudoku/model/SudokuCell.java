package com.ecp.sudoku.model;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;

/**
 * Created by ericpass on 10/26/15.
 */
public class SudokuCell {

    private boolean isInitial;

    private int value;
    private int maxValue;
    private SudokuPuzzleSize puzzleSize;

    private Rectangle bounds; //This is the square that the suduko cell occupys

    private Point cellLocation;

    private  HashSet<Integer> setOfAllPossibleValues;

    public SudokuCell(int maxValue, SudokuPuzzleSize puzzleSize) {
        this.puzzleSize = puzzleSize;
        init(maxValue);
    }

    public void init(int maxValue) {
        this.value = 0;
        this.maxValue = maxValue;
        this.isInitial = false;
        this.setOfAllPossibleValues = getSetOfAllPossibleValues();
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setIsInitial(boolean isInitial) {
        this.isInitial = isInitial;
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
        g.drawRect(x, y, width, width);
        drawBorder(g, x, y, width, cellPosition);
        Font font = g.getFont();
        FontRenderContext frc = new FontRenderContext(null, true, true);
        if (value > 0) {
            String s = Integer.toString(value);

            BufferedImage image = createImage(font, frc, width, s);

            //I think this is for centering the image in the box
            int xx = x + (width - image.getWidth()) / 2;
            int yy = y + (width - image.getHeight()) / 2;
            g.drawImage(image, xx, yy, null);

        }
    }

    public boolean isInitial() {
        return isInitial;
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

    private BufferedImage createImage(Font font, FontRenderContext frc,
                                      int width, String s) {
        int margin = (int) (0.075 * this.puzzleSize.getDrawWidth());
        double extra = (double) margin + margin;

        Font largeFont = font.deriveFont((float) (width * 1 / 2));
        Rectangle2D r = largeFont.getStringBounds(s, frc);

        BufferedImage image = new BufferedImage((int) Math.round(r.getWidth()
                + extra), (int) Math.round(extra - r.getY()),
                BufferedImage.TYPE_INT_RGB);
        Graphics gg = image.getGraphics();
        gg.setColor(Color.WHITE);
        gg.fillRect(0, 0, image.getWidth(), image.getHeight());

        if (isInitial) {
            gg.setColor(Color.BLUE);
        } else {
            gg.setColor(Color.RED);
        }
        int x = margin;
        int y = -(int) Math.round(r.getY());
        gg.setFont(largeFont);
        gg.drawString(s, x, y);
        gg.dispose();
        return image;
    }

    public HashSet<Integer> getSetOfAllPossibleValues(){
        HashSet<Integer> res = new HashSet<>(maxValue);
        if(isInitial){
            res.add(value);
            return res;
        }
        for(int i = 1; i<=maxValue; i++){
            res.add(i);
        }
        return res;
    }

    public boolean contains(Point point) {
        return bounds.contains(point);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SudokuCell that = (SudokuCell) o;

        return !(cellLocation != null ? !cellLocation.equals(that.cellLocation) : that.cellLocation != null);

    }

    @Override
    public int hashCode() {
        return cellLocation != null ? cellLocation.hashCode() : 0;
    }
}
