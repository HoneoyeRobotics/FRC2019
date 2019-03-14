/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Preferences;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    public static final int frontLeftDriveMotorCanID = 1;
    public static final int rearLeftDriveMotorCanID = 2;
    public static final int frontRightDriveMotorCanID = 3;
    public static final int rearRightDriveMotorCanID = 9;//4;
    public static final int armFwdRevMotorCanID = 5;
    public static final int elevatorMotorCanID = 6;
    public static final int leftArmWheelMotorCanID = 7;
    public static final int rightArmWheelMotorCanID = 8;
    
    public static final int pneumaticControlModuleID = 10;
    public static final int clawInPCMID = 1;
    public static final int clawOutPCMID = 0;

    /* elevator encoder:
        Orange = Power
        Blue = A Channel Signal
        Black = Ground
        Yellow = B Channel Signal
    */
    public static final int elevatorEncoderADIO = 0;
    public static final int elevatorEncoderBDIO = 1;
    //not used, using the encoder on the talonsrx
    //public static final int armFwdRevEncoderADIO = 2;
    //public static final int armFwdRevEncoderBDIO = 3;


    public static final double slowSpeedModifier = 0.75;


    public static int[] ballEncoderPositions = { 0, 200, 680, 680 };
    public static int[] hatchEncoderPositions = { 0, 200, 325, 400 };
    public static int armFwdRevEncoderMin = 1000;
    public static int armFwdRevEncoderMax = 1110000;
    public static int armFwdRevEncoderHatchMax = 600000;
    public static double armFwdRevAutoSpeed = 1;

    public static double visionCenterDeadband = 5;
    public static double visionDistanceBall = 8.5;
    public static double visionDistanceHatch = 3;
    public static double visionDistanceFromCamToBumper = 40;
    public static double visionFwdSpeed = 0.75;
    public static double visionRotateSpeed = 0.75;
    public static double visionDistanceOffPixelsOK = 15;
    public static double rotateSpeed = 0.25;

    public static  void loadFromPreferences(){
        //load these settings from the driver station...
        Preferences prefs = Preferences.getInstance();

        armFwdRevEncoderMin = prefs.getInt("armFwdRevEncoderMin", 1000);
        armFwdRevEncoderMax = prefs.getInt("armFwdRevEncoderMax", 1110000);
        armFwdRevEncoderHatchMax = prefs.getInt("armFwdRevEncoderHatchMax", 600000);
        armFwdRevAutoSpeed = prefs.getDouble("armFwdRevAutoSpeed", 1);
        rotateSpeed = prefs.getDouble("rotateSpeed", 0.25);

        visionCenterDeadband = prefs.getDouble("visionCenterDeadband", 5);
        visionDistanceBall = prefs.getDouble("visionDistanceBall", 8.5);
        visionDistanceHatch = prefs.getDouble("visionDistanceHatch", 3);
        visionDistanceFromCamToBumper = prefs.getDouble("visionDistanceFromCamToBumper", 40);
        visionFwdSpeed = prefs.getDouble("visionFwdSpeed", 0.75);
        visionRotateSpeed = prefs.getDouble("visionRotateSpeed", 0.75);
        visionDistanceOffPixelsOK = prefs.getDouble("visionDistanceOffPixelsOK", 15);

        ballEncoderPositions[1] = prefs.getInt("ballEncoderPosition1", 200);
        ballEncoderPositions[2] = prefs.getInt("ballEncoderPosition2", 680);
 
        hatchEncoderPositions[1] = prefs.getInt("hatchEncoderPosition1", 200);
        hatchEncoderPositions[2] = prefs.getInt("hatchEncoderPosition2", 325);
    }

    public static void getSetPoints(){
        
        Preferences prefs = Preferences.getInstance();
        ballEncoderPositions[1] = prefs.getInt("ballEncoderPosition1", 200);
        ballEncoderPositions[2] = prefs.getInt("ballEncoderPosition2", 680);

        hatchEncoderPositions[1] = prefs.getInt("hatchEncoderPosition1", 200);
        hatchEncoderPositions[2] = prefs.getInt("hatchEncoderPosition2", 325);

    }
}
