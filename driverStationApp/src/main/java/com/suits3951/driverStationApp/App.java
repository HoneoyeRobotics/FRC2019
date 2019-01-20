package com.suits3951.driverStationApp;

// import java.awt.BorderLayout;
// import java.awt.Color;
// import java.awt.Dimension;
// import java.awt.Insets;

import java.awt.EventQueue;
// import java.awt.Graphics;
// import javax.swing.JFrame;
// import javax.swing.JPanel;
// import javax.swing.border.EmptyBorder;
// import javax.swing.JTextArea;

// import edu.wpi.cscore.UsbCamera;

public class App {

    
    public static void main(String[] args) throws Exception {    
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MyFrame frame = new MyFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}