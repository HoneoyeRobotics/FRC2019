package com.suits3951.driverStationApp;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;

public class VideoCap {

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    VideoCapture cap;
    Mat2Image mat2Img = new Mat2Image();
    public Mat lastMat;

    VideoCap(){
        cap = new VideoCapture("http://10.39.51.11/mjpg/video.mjpg");
       cap.open("http://10.39.51.11/mjpg/video.mjpg");
    }

    BufferedImage getOneFrame() {        
        cap.read(mat2Img.mat);
        lastMat = mat2Img.mat;
        return mat2Img.getImage(mat2Img.mat);
    }


}