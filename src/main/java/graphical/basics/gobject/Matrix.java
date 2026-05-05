package graphical.basics.gobject;

import graphical.basics.location.Location;

import java.awt.*;
import java.util.ArrayList;

public class Matrix extends Group {
    ArrayList<Line> lines = new ArrayList<>();

    double cellWidth, cellHeight;


    int numCol, numLine;

    Location refLocation;

    public Matrix(double cellWidth, double cellHeight, int numCol, int numLine, Location refLocation) {
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.numCol = numCol;
        this.numLine = numLine;
        this.refLocation = refLocation;
        init();
    }

    private void init() {
        var width = numCol * cellWidth;
        var height = numLine * cellHeight;

        for (int i = 0; i <= numLine; i++) {
            var line = new Line(refLocation.plus(0,i*cellHeight),refLocation.plus(width,i*cellHeight), Color.white);
            lines.add(line);
            add(line);
        }
        for (int j = 0; j <= numCol; j++) {
            var line = new Line(refLocation.plus(j*cellWidth,0),refLocation.plus(j*cellWidth,height), Color.white);
            add(line);
        }
    }

    public Location getCellLocation(int x, int y){
        return refLocation.plus(x*cellWidth,y*cellWidth).plus(cellWidth/2,cellHeight/2);
    }

    public Rect gerRectCell(int x, int y){
        return new Rect(refLocation.plus(x*cellWidth,y*cellWidth),refLocation.plus(x*cellWidth,y*cellWidth).plus(cellWidth,cellHeight),Color.white);
    }


}
