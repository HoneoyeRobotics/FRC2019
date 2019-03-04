/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

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
    public static final int rearRightDriveMotorCanID = 4;
    public static final int armFwdRevMotorCanID = 5;
    public static final int elevatorMotorCanID = 6;
    public static final int leftArmWheelMotorCanID = 7;
    public static final int rightArmWheelMotorCanID = 8;
    
    public static final int pneumaticControlModuleID = 10;
    public static final int clawInPCMID = 0;
    public static final int clawOutPCMID = 1;

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


    public static final int[] ballEncoderPositions = { 0, 200, 590, 590 };
    public static final int[] hatchEncoderPositions = { 0, 200, 400, 400 };
    public static final int armFwdRevEncoderMin = 1000;
    public static final int armFwdRevEncoderMax = 1110000;
    public static final int armFwdRevEncoderHatchMax = 600000;
    public static final double armFwdRevAutoSpeed = 1;

    public static final double visionCenterDeadband = 5;
    public static final double visionDistanceBall = 8.5;
    public static final double visionDistanceHatch = 3;
    public static final double visionDistanceFromCamToBumper = 40;
    public static final double visionFwdSpeed = 0.75;
    public static final double visionRotateSpeed = 0.75;
    public static final double visionDistanceOffPixelsOK = 15;
}
