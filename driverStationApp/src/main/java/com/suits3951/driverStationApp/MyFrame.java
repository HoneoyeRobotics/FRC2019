package com.suits3951.driverStationApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.suits3951.driverStationApp.GripPipeline.Line;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import javax.swing.JTextArea;

// import edu.wpi.cscore.UsbCamera;

public class MyFrame extends JFrame {
    private JPanel contentPanel;
    private JTextArea infoTextArea;
    private int cameraWidth = 320;
    private int cameraHeight = 240;
    public MyFrame() {
     
        
        infoTextArea = new JTextArea();
        infoTextArea.setText("what is the up.");
        //infoTextArea.setBackground(new Color(241,241,241));
        //infoTextArea.setEditable(false);
        infoTextArea.setMargin(new Insets(5, 5, 5,5));
        //infoTextArea.setBounds(5,5, 300,75);
        infoTextArea.setVisible(true);
        
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(infoTextArea, BorderLayout.CENTER);
        infoPanel.setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1280, 720);

        
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        


        JPanel mainPanel = new JPanel(new BorderLayout());
        //mainPanel.add(new JTextArea("what is the up"));
        //mainPanel.add(infoTextArea);        
        mainPanel.add(contentPanel);
        this.setTitle("SUITS 3951 Driver Station GRIP Parser");
        this.setLayout(new BorderLayout());        
        this.add(mainPanel, BorderLayout.CENTER);
        this.setSize(new Dimension(800, 650));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);        

        
        ntInstance = NetworkTableInstance.getDefault();
        nTable = ntInstance.getTable("VisionTable");
        ntInstance.startClientTeam(3951);  // where TEAM=190, 294, etc, or use inst.startClient("hostname") or similar
        ntInstance.startDSClient();  // recommended if running on DS computer; this gets the robot IP from the DS
        nteInRange = nTable.getEntry("inRange");
        nteDistance = nTable.getEntry("distance");
        ntePixelsOff = nTable.getEntry("pixelsOff");
       new MyThread().start();
    }
    private NetworkTableInstance ntInstance;
    private NetworkTable nTable;
    private NetworkTableEntry nteInRange;
    private NetworkTableEntry nteDistance;
    private NetworkTableEntry ntePixelsOff;

    private VideoCap videoCap = new VideoCap();

    GripPipeline pipeline = new GripPipeline();

    Point topPoint1 = new Point(cameraWidth,cameraHeight);
    Point rightPoint1 = new Point(0,0);    
    Point topPoint2 = new Point(cameraWidth,cameraHeight);
    Point rightPoint2 = new Point(0,0);
    double center = 0;
    double length = 0;
    double lastCenter = 0;
    boolean closeEnough = false;
    double distanceFromCenter = 0;

    public void paint(Graphics g){
        g = contentPanel.getGraphics();
        BufferedImage image = videoCap.getOneFrame();
        Mat mat = videoCap.lastMat; //videoCap.getLastMat();

        pipeline.process(mat);
        //ArrayList<GripPipeline.Line> lines = pipeline.findLinesOutput();

        int point = 1;

        ArrayList<MatOfPoint> contours = pipeline.findContoursOutput();

         topPoint1 = new Point(cameraWidth,cameraHeight);
         rightPoint1 = new Point(0,0);
         topPoint2 = new Point(cameraWidth,cameraHeight);
         rightPoint2 = new Point(0,0);
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);

            if(area < 100)
                continue;
            //Rect rect = Imgproc.boundingRect(contour);
            
            MatOfInt hull = new MatOfInt();
            //MatOfPoint points = new MatOfPoint(pointMat);
            
            Point topPoint = new Point(cameraWidth,cameraHeight);
            Point rightPoint = new Point(0,0);
            
            Imgproc.convexHull(contour, hull);
            Point[] hp = new Point[hull.height()];
            
            for(int i = 0; i < hull.height(); i++){
                int index = (int)hull.get(i,0)[0];
                Point currPoint  = new Point(contour.get(index,0));
                hp[i] = currPoint;
                if(currPoint.y < topPoint.y && currPoint.y > 10){
                    topPoint = currPoint;
                }
                if(currPoint.x > rightPoint.x){
                    rightPoint = currPoint;
                }
            }
            if(point == 1){
                topPoint1 = topPoint;
                rightPoint1 = rightPoint;
                point++;
            }
            else{
                topPoint2 = topPoint;
                rightPoint2 = rightPoint;
            }



            String foo = "";
        }

        if((topPoint1.x == cameraWidth && topPoint1.y == cameraHeight) || (topPoint2.x == cameraWidth && topPoint2.y == cameraHeight)){
            System.out.print("Not found!");
            g.drawImage(image, 50, 100, this);
            return;
        }
        if(topPoint1.x < topPoint2.x){
            
            length = topPoint2.x - topPoint1.x;
            center = topPoint1.x + (length / 2);
        }
        else{

            length = topPoint1.x - topPoint2.x;
            center = topPoint2.x + (length / 2);
        }

        Line tapeTop = new Line(topPoint1.x, topPoint1.y, rightPoint1.x, rightPoint1.y);
        //formula found from https://wpilib.screenstepslive.com/s/currentCS/m/vision/l/288985-identifying-and-processing-the-targets
        double width = tapeTop.length();
        double tapeWidthIn = 2.0;
        double tapeWidthFt = tapeWidthIn / 12;
        double fovPixels = cameraWidth;
        double cameraViewAngle = 41.7; //this in their example what worked.
        //double cameraViewAngle = 54; //this is set my the camera
        double  bottomMath = fovPixels /(2 * width * Math.tan(cameraViewAngle));
        double distance = tapeWidthFt * bottomMath;
        
        double targetWidth = 2; // width of tape
        double targetPixelWidth = width;
        //double cameraFieldOfView = 47.5;
        double cameraFieldOfView = 48.6;
        distance = (((targetWidth*cameraWidth)/targetPixelWidth)/2)/Math.tan(((cameraFieldOfView*3.14159)/180.0)/2.0);


    //distance in feet!
    //SetVariable "/SmartDashboard/Frisbee_Distance", CInt((distance*100)/12)/100



        distanceFromCenter =  center - (cameraWidth /  2);

        if(distanceFromCenter < 0){
            closeEnough = (distanceFromCenter > -10);
        }
        else{
            closeEnough = (distanceFromCenter < 10);
        }


        if(lastCenter != center){
            lastCenter = center;

            System.out.print("Points are now " + topPoint1.x + "," + topPoint1.y + " and " + topPoint2.x + "," + topPoint2.y + 
            "; Center: " + center + "; length: " + length + "; DFC: " + distanceFromCenter +  
            "; close? " + closeEnough + ";  Distance: " +  distance + "; Tape width: "  + width + "\n");

            nteDistance.setDouble(distance);
            nteInRange.setBoolean(closeEnough);
            ntePixelsOff.setDouble(distanceFromCenter);                                    
        }
        // if(lines.size() != numlines){
        //     System.out.print("Num Lines now " + lines.size() + "\n");
        //     int num = 0;
        // //   for (GripPipeline.Line line : lines) {
        // //       num++;
        // //     System.out.print("      line " + num + " x1:"+line.x1 + ", y1:"+line.y1 + ", x2:"+line.x2+ ", y2:"+line.y2 + ", len: " + line.length() + ", ang: " + line.angle() + "\n");
        // //   }          
        //     numlines = lines.size();            
        //     nteNumLines.setNumber(numlines);        

        // }        
            
        g.drawImage(image, 50, 100, this);
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            for (;;){
                repaint();
                try {                     
                    Thread.sleep(100);                                
                    System.gc(); 

                } catch (InterruptedException e) {    }
            }
        }
    }


   



}