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
       new MyThread().start();
    }
    private NetworkTableInstance ntInstance;
    private NetworkTable nTable;
    private NetworkTableEntry nteInRange;
    private NetworkTableEntry nteDistance;

    private VideoCap videoCap = new VideoCap();

    GripPipeline pipeline = new GripPipeline();

    Point topPoint1 = new Point(320,240);
    Point topPoint2 = new Point(320,240);
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

         topPoint1 = new Point(320,240);
         topPoint2 = new Point(320,240);
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);

            if(area < 500)
                continue;
            //Rect rect = Imgproc.boundingRect(contour);
            
            MatOfInt hull = new MatOfInt();
            //MatOfPoint points = new MatOfPoint(pointMat);
            
            Point topPoint = new Point(320,240);
            
            Imgproc.convexHull(contour, hull);
            Point[] hp = new Point[hull.height()];
            
            for(int i = 0; i < hull.height(); i++){
                int index = (int)hull.get(i,0)[0];
                Point currPoint  = new Point(contour.get(index,0));
                hp[i] = currPoint;
                if(currPoint.y < topPoint.y && currPoint.y > 10){
                    topPoint = currPoint;
                }
            }
            if(point == 1){
                topPoint1 = topPoint;
                point++;
            }
            else{
                topPoint2 = topPoint;
            }



            String foo = "";
        }

        if(topPoint1.x < topPoint2.x){
            
            length = topPoint2.x - topPoint1.x;
            center = topPoint1.x + (length / 2);
        }
        else{

            length = topPoint1.x - topPoint2.x;
            center = topPoint2.x + (length / 2);
        }

        distanceFromCenter =  center - 160;

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
            "; close? " + closeEnough + "\n");

            nteDistance.setDouble(distanceFromCenter);
            nteInRange.setBoolean(closeEnough);
        
            
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