// package com.suits3951.driverStationApp;

// import java.awt.BorderLayout;
// import java.awt.Color;
// import java.awt.Dimension;
// import java.awt.Font;
// import java.awt.Insets;
// import java.awt.image.BufferedImage;
// import java.awt.image.WritableRaster;
// import java.util.ArrayList;
// import java.util.Dictionary;
// import java.util.HashMap;
// import java.util.Map;
// import java.awt.Graphics;
// import java.awt.Graphics2D;
// import java.awt.Image;

// import javax.swing.JFrame;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.border.EmptyBorder;

// import com.suits3951.driverStationApp.GripPipeline.Line;

// import org.opencv.core.*;
// import org.opencv.highgui.HighGui;
// import org.opencv.imgproc.Imgproc;
// import org.opencv.videoio.VideoCapture;
// import org.opencv.videoio.Videoio;

// import java.awt.image.DataBufferByte;
// import java.awt.image.WritableRaster;

// import edu.wpi.first.networktables.NetworkTable;
// import edu.wpi.first.networktables.NetworkTableEntry;
// import edu.wpi.first.networktables.NetworkTableInstance;

// import javax.swing.JTextArea;

// // import edu.wpi.cscore.UsbCamera;

// public class DSWindowFrame extends JFrame {
//     private JPanel contentPanel;
//     private JTextArea infoTextArea;
//     private int cameraWidth = 320;
//     private int cameraHeight = 240;


//     private VideoCapture videoCap;
//     private NetworkTableInstance ntInstance;
//     private NetworkTable nTable;
//     private NetworkTableEntry nteDistance;
//     private NetworkTableEntry ntePixelsOff;
//     private NetworkTableEntry nteHatch;    
//     Mat2Image mat2Img = new Mat2Image();
//     public Mat lastMat;


//     GripPipeline pipeline = new GripPipeline();

//     Point topPoint1 = new Point(cameraWidth,cameraHeight);
//     Point rightPoint1 = new Point(0,0);    
//     Point topPoint2 = new Point(cameraWidth,cameraHeight);
//     Point rightPoint2 = new Point(0,0);
//     double center = 0;
//     double length = 0;
//     double lastCenter = 0;
//     boolean closeEnough = false;
//     double distanceFromCenter = 0;
//     boolean hatch = false;
//     double hatchMinY = 175;
//     double hatchMaxY = 185;
//     double ballMinY = 80;
//     double ballMaxY = 125;
//     JPanel mainPanel;


//     public DSWindowFrame() {
//         //  videoCap = new VideoCapture("http://10.39.51.11/mjpg/video.mjpg");
//         //  boolean opened = videoCap.open("http://10.39.51.11/mjpg/video.mjpg");

//         //  return;
//         videoCap = new VideoCapture(0);
//         videoCap.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 320);
//         videoCap.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, 240);
//         videoCap.open(0);        
        
//         infoTextArea = new JTextArea(5, 80);
//         infoTextArea.setText("Dashboard. Lots of it\nNext\nNext");
//         //infoTextArea.setMargin(new Insets(5, 5, 5,5));
//         //infoTextArea.setBounds(5,5, 300,75);
//         //infoTextArea.setVisible(true);
//         infoTextArea.setBackground(Color.LIGHT_GRAY);
       
//         infoTextArea.setLineWrap(true);
//         infoTextArea.setWrapStyleWord(true);
        
//         JScrollPane textScrollPane = new JScrollPane(infoTextArea);
//         textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//         textScrollPane.setPreferredSize(new Dimension(300, 150));

//         // JPanel infoPanel = new JPanel(new BorderLayout());
//         // infoPanel.add(infoTextArea, BorderLayout.CENTER);
//         // infoPanel.setVisible(true);

//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         //setBounds(0, 0, 1280, 720);

        
//         contentPanel = new JPanel(new BorderLayout());
//         contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        
        

//         mainPanel = new JPanel(new BorderLayout());
//         //mainPanel.add(new JTextArea("what is the up"));
//         //this.add(infoTextArea);        
//         mainPanel.add(textScrollPane, BorderLayout.PAGE_START);
//         mainPanel.add(contentPanel, BorderLayout.CENTER);
//         this.setTitle("SUITS 3951 Driver Station GRIP Parser");
//         this.setLayout(new BorderLayout());        
//         this.add(mainPanel, BorderLayout.CENTER);
        
//         this.setSize(new Dimension(670, 680));
//         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         //this.setLocationRelativeTo(null);
//         //this.setResizable(false);
//         //this.setVisible(true);        

        
//         ntInstance = NetworkTableInstance.getDefault();
//         nTable = ntInstance.getTable("VisionTable");
//         //ntInstance.startClientTeam(3951);  // where TEAM=190, 294, etc, or use inst.startClient("hostname") or similar
//         //ntInstance.startDSClient();  // recommended if running on DS computer; this gets the robot IP from the DS
//         //nteInRange = nTable.getEntry("inRange");
//         nteDistance = nTable.getEntry("distance");
//         ntePixelsOff = nTable.getEntry("pixelsOff");
//         nteHatch = nTable.getEntry("hatch");
//         new MyThread().start();
//     }



        
//     Mat getLastMat(){
            
//         videoCap.read(mat2Img.mat);
//         lastMat = mat2Img.mat;
//         return lastMat;
//     }

//     BufferedImage getOneFrame() {        
//         videoCap.read(mat2Img.mat);
//         lastMat = mat2Img.mat;
//         BufferedImage image = MatToImage(mat2Img.mat);
//         return image;
//     }

//     BufferedImage MatToImage(Mat mat){

//         int type = 0;
//         switch (mat.channels()) {
//             case 1:
//                 type = BufferedImage.TYPE_BYTE_GRAY;
//                 break;
//             case 3:
//             type = BufferedImage.TYPE_3BYTE_BGR;
//                 break;
//         } 
        
//         int matCols = mat.cols();
//         int matRows = mat.rows();
//         BufferedImage img = null;
//         try {
//             img = new BufferedImage(matCols, matRows, type);
            
//             if(img != null) {        
//                 WritableRaster raster = img.getRaster();
//                 DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
//                 byte[] data = dataBuffer.getData();
//                 mat.get(0, 0, data);
//             }
//         }
//         catch(Exception ex) {

//         }
        
//         return img;
//     }

//     public void paint(Graphics g){
//         //if(1==1){return;}
//         hatch = nteHatch.getBoolean(false);
//         g = contentPanel.getGraphics();
//         BufferedImage image = getOneFrame();
//         Mat mat = lastMat;

//         // BufferedImage smallImage =  Thumbnails.of(image).size(640, 480).asBufferedImage();

//         // Image tmp = image.getScaledInstance(640, 480, Image.SCALE_SMOOTH);
//         // BufferedImage smallImage = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
    
//         // Graphics2D g2d = smallImage.createGraphics();
//         // g2d.drawImage(tmp, 0, 0, null);
//         // g2d.dispose();
    
        

//         pipeline.process(mat);
//         //ArrayList<GripPipeline.Line> lines = pipeline.findLinesOutput();

//         int point = 1;

//         ArrayList<MatOfPoint> contours = pipeline.findContoursOutput();

//          topPoint1 = new Point(cameraWidth,cameraHeight);
//          rightPoint1 = new Point(0,0);
//          topPoint2 = new Point(cameraWidth,cameraHeight);
//          rightPoint2 = new Point(0,0);
//          ArrayList<TopLine> points = new ArrayList<TopLine>();
//         for (MatOfPoint contour : contours) {
//             double area = Imgproc.contourArea(contour);

//             // if(area < 25)
//             //     continue;
          
//             //Rect rect = Imgproc.boundingRect(contour);
            
//             MatOfInt hull = new MatOfInt();
//             //MatOfPoint points = new MatOfPoint(pointMat);
            
//             Point topPoint = new Point(cameraWidth,cameraHeight);
//             Point rightPoint = new Point(0,0);
            
//             Imgproc.convexHull(contour, hull);
//             Point[] hp = new Point[hull.height()];
            
//             for(int i = 0; i < hull.height(); i++){
//                 int index = (int)hull.get(i,0)[0];
//                 Point currPoint  = new Point(contour.get(index,0));
//                 hp[i] = currPoint;
//                 if(currPoint.y < topPoint.y && currPoint.y > 10){
//                     topPoint = currPoint;
//                 }
//                 if(currPoint.x > rightPoint.x){
//                     rightPoint = currPoint;
//                 }
//             }

//             //filter out most of the noise automatically
//             boolean good = false;
//             if(hatch == true){
//                 good = topPoint.y >= hatchMinY && topPoint.y <= hatchMaxY;
//             }
//             else{
//                 good = topPoint.y >= ballMinY && topPoint.y <= ballMaxY;
//             }
//             if(good == false){
//                 continue;
//             }

//             points.add(new TopLine(topPoint, rightPoint));                  
//         }
  
//         if(points.size() < 2){
//             System.out.print("Only found " + points.size() + " points.\n");
//             g.drawImage(image, 0, 0, this);
//             return;
//         } else if(points.size() == 2){
//             //2 points
            
//             topPoint1 = points.get(0).topPoint;
//             rightPoint1 = points.get(0).rightPoint;
//             topPoint2 = points.get(1).topPoint;
//             rightPoint2 = points.get(1).rightPoint;
//         } else {
//             //more than 2 points... lets figure this junk out.
//            ArrayList<SimilarHeightObjects> topYs = new ArrayList<SimilarHeightObjects>();
//            double lineDeadband = 2;
//            for (TopLine line : points) {
//                boolean found = false;
//                for (SimilarHeightObjects currentY : topYs) {
//                 if(line.topPoint.y <= currentY.y + lineDeadband && line.topPoint.y >= currentY.y - lineDeadband){
//                     currentY.num++;
//                     currentY.lines.add(line);
//                     found = true;
//                    }
//                }
             
//                if(found == false){
//                 topYs.add(new SimilarHeightObjects(line.topPoint.y, 1, line));
//                }
//            }

//            // now we have a list of points, get the one with 2

//            SimilarHeightObjects goodY = new SimilarHeightObjects(0,0, null);
//            for (SimilarHeightObjects topY : topYs) {
//                if(topY.num > goodY.num){
//                    goodY = topY;
//                }
//            }
//            //found the good Ys now get points;
//            if(goodY.num >= 2){
//             topPoint1 = goodY.lines.get(0).topPoint;
//             rightPoint1 = goodY.lines.get(0).rightPoint;
//             topPoint2 = goodY.lines.get(1).topPoint;
//             rightPoint2 = goodY.lines.get(1).rightPoint;
//            }
//         }
        
//         if(topPoint1.x < topPoint2.x){
            
//             length = topPoint2.x - topPoint1.x;
//             center = topPoint1.x + (length / 2);
//         }
//         else{

//             length = topPoint1.x - topPoint2.x;
//             center = topPoint2.x + (length / 2);
//         }

//         Line tapeTop = new Line(topPoint1.x, topPoint1.y, rightPoint1.x, rightPoint1.y);
//         //formula found from https://wpilib.screenstepslive.com/s/currentCS/m/vision/l/288985-identifying-and-processing-the-targets
//         double width = tapeTop.length();
//         double tapeWidthIn = 2.0;
//         double tapeWidthFt = tapeWidthIn / 12;
//         double fovPixels = cameraWidth;
//         double cameraViewAngle = 41.7; //this in their example what worked.
//         //double cameraViewAngle = 54; //this is set my the camera
//         double  bottomMath = fovPixels /(2 * width * Math.tan(cameraViewAngle));
//         double distance = tapeWidthFt * bottomMath;
        
//         double targetWidth = 2; // width of tape
//         double targetPixelWidth = width;
//         //double cameraFieldOfView = 47.5;
//         double cameraFieldOfView = 48.6;
//         distance = (((targetWidth*cameraWidth)/targetPixelWidth)/2)/Math.tan(((cameraFieldOfView*3.14159)/180.0)/2.0);
//         distanceFromCenter =  center - (cameraWidth /  2);

//         if(distanceFromCenter < 0){
//             closeEnough = (distanceFromCenter > -10);
//         }
//         else{
//             closeEnough = (distanceFromCenter < 10);
//         }


//         if(lastCenter != center){
//             lastCenter = center;

//             String Output = "Points are now " + topPoint1.x + "," + topPoint1.y + " and " + topPoint2.x + "," + topPoint2.y + 
//             "; Center: " + center + "; length: " + length + "; DFC: " + distanceFromCenter +  
//             "; close? " + closeEnough + ";  Distance: " +  distance + "; Tape width: "  + width;
//             System.out.print(Output + "\n");
//             infoTextArea.setText(Output);
//             infoTextArea.setCaretPosition(infoTextArea.getText().length() - 1);

//             nteDistance.setDouble(distance);
//             mainPanel.revalidate();
//             mainPanel.repaint();
//            // nteInRange.setBoolean(closeEnough);
//             ntePixelsOff.setDouble(distanceFromCenter);                                    
//         }  
            
//         g.drawImage(image, 0, 0, this);
//     }

//     class MyThread extends Thread{
//         @Override
//         public void run() {
//             for (;;){
//                 repaint();
//                 try {                     
//                     Thread.sleep(50);                                
//                     System.gc(); 

//                 } catch (InterruptedException e) {    }
//             }
//         }
//     }


   



// }