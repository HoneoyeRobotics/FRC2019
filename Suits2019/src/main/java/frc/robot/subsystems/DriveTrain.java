/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.ArcadeDriveWithJoystick;
import frc.robot.lib.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.I2C;

public class DriveTrain extends Subsystem {
  private WPI_VictorSPX frontLeftDriveMotor;
	private WPI_VictorSPX rearLeftDriveMotor;
	private SpeedControllerGroup leftDriveMotorGroup;	
	private WPI_VictorSPX frontRightDriveMotor;
  //private WPI_VictorSPX rearRightDriveMotor;	
  private WPI_TalonSRX rearRightDriveMotor;	
  private SpeedControllerGroup rightDriveMotorGroup;
  private DifferentialDrive drivetrain;
  public boolean inRange = false;
  public double pixelsOff = 9999;
  public double distance = 9999;
  public boolean foundImage = false;
  //public LIDARLite distanceSensor;
  

  public DriveTrain(){
    frontLeftDriveMotor = new WPI_VictorSPX(RobotMap.frontLeftDriveMotorCanID);
    frontLeftDriveMotor.setInverted(true);
    rearLeftDriveMotor = new WPI_VictorSPX(RobotMap.rearLeftDriveMotorCanID);
    rearLeftDriveMotor.setInverted(true);
	  leftDriveMotorGroup = new SpeedControllerGroup(frontLeftDriveMotor, rearLeftDriveMotor);
    
    frontRightDriveMotor = new WPI_VictorSPX(RobotMap.frontRightDriveMotorCanID);
    frontRightDriveMotor.setInverted(true);
    //rearRightDriveMotor = new WPI_VictorSPX(RobotMap.rearRightDriveMotorCanID);
    rearRightDriveMotor = new WPI_TalonSRX(RobotMap.rearRightDriveMotorCanID);
    rearRightDriveMotor.setInverted(true);
    rightDriveMotorGroup = new SpeedControllerGroup(frontRightDriveMotor, rearRightDriveMotor);
    drivetrain = new DifferentialDrive(leftDriveMotorGroup, rightDriveMotorGroup);
    
    // distanceSensor = new LIDARLite (I2C.Port.kOnboard);
    // distanceSensor.startMeasuring();

  }

  public void getVisionData(){
    
    distance = Robot.visionTable.getNumber("distance", 9999);
    pixelsOff = Robot.visionTable.getNumber("pixelsOff", 9999);
    foundImage = Robot.visionTable.getBoolean("foundImage", false);
    if(foundImage == true){
      inRange = (pixelsOff < 0 ? pixelsOff * -1 : pixelsOff) <= RobotMap.visionDistanceOffPixelsOK;
    }
    else{
      inRange = false;
    }
    SmartDashboard.putBoolean("Found Image?", foundImage);
    SmartDashboard.putBoolean("In Range?", inRange);
    SmartDashboard.putNumber("Pixels from Center", pixelsOff);
    SmartDashboard.putNumber("Distance from Target", distance);
  }
  
	public void arcadeDrive(double xSpeed,double zRotation) {				
		drivetrain.arcadeDrive( xSpeed, zRotation);
  }	

  public void tankDrive(double leftSpeed, double rightSpeed){
    drivetrain.tankDrive(leftSpeed, rightSpeed);
  }
  
  // public int getDistanceCM(){
  //   return distanceSensor.getDistance();
  // }
	
  // public double getDistance(){    
  //   int distance = distanceSensor.getDistance();
  //   double inches = distance / 2.54;
  //   return inches;
  // }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDriveWithJoystick());
  }

}
