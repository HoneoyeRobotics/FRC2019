
package com.suits3951.driverStationApp;

import org.opencv.core.*;

public class TopLine {
    public Point topPoint;
    public Point rightPoint;
    public boolean good = false;

    public TopLine(){

    }

    public TopLine(Point TopPoint, Point RightPoint){
        topPoint = TopPoint;
        rightPoint = RightPoint;
    }
}