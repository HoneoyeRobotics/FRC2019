
package com.suits3951.driverStationApp;

import java.util.ArrayList;

import org.opencv.core.*;

public class SimilarHeightObjects {
    
    public double y = 0;
    public int num = 0;
    public ArrayList<TopLine> lines;

    public SimilarHeightObjects(){
        lines = new ArrayList<TopLine>();
    }
    public SimilarHeightObjects(double Y, int Num, TopLine line){
        y = Y;
        num= Num;
        lines = new ArrayList<TopLine>();
        lines.add(line);
    }
}