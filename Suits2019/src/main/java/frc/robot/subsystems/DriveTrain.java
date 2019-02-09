/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.ArcadeDriveWithJoystick;
import frc.robot.lib.*;
import edu.wpi.first.wpilibj.I2C;

public class DriveTrain extends Subsystem {
  private WPI_VictorSPX frontLeftDriveMotor;
	private WPI_VictorSPX rearLeftDriveMotor;
	private SpeedControllerGroup leftDriveMotorGroup;	
	private WPI_VictorSPX frontRightDriveMotor;
	private WPI_VictorSPX rearRightDriveMotor;	
  private SpeedControllerGroup rightDriveMotorGroup;
  private DifferentialDrive drivetrain;
  
  public LIDARLite distanceSensor;
  

  public DriveTrain(){
    frontLeftDriveMotor = new WPI_VictorSPX(RobotMap.frontLeftDriveMotorCanID);
    rearLeftDriveMotor = new WPI_VictorSPX(RobotMap.rearLeftDriveMotorCanID);
	  leftDriveMotorGroup = new SpeedControllerGroup(frontLeftDriveMotor, rearLeftDriveMotor);
    
    frontRightDriveMotor = new WPI_VictorSPX(RobotMap.frontRightDriveMotorCanID);
    frontRightDriveMotor.setInverted(true);
    rearRightDriveMotor = new WPI_VictorSPX(RobotMap.rearRightDriveMotorCanID);
    rearRightDriveMotor.setInverted(true);
    rightDriveMotorGroup = new SpeedControllerGroup(frontRightDriveMotor, rearRightDriveMotor);
    drivetrain = new DifferentialDrive(leftDriveMotorGroup, rightDriveMotorGroup);
    
    distanceSensor = new LIDARLite (I2C.Port.kOnboard);
    distanceSensor.startMeasuring();

    //put raw data of motor controllers to dashboard
    SmartDashboard.putData("LF Drive (" + RobotMap.frontLeftDriveMotorCanID + ")", rearLeftDriveMotor);
    SmartDashboard.putData("LR Drive (" + RobotMap.rearLeftDriveMotorCanID + ")",  rearLeftDriveMotor);
    SmartDashboard.putData("RF Drive (" + RobotMap.frontRightDriveMotorCanID + ")",  frontRightDriveMotor);
    SmartDashboard.putData("RR Drive (" + RobotMap.rearRightDriveMotorCanID + ")",  rearRightDriveMotor);
  }

  
	public void arcadeDrive(double xSpeed,double zRotation) {				
		drivetrain.arcadeDrive( xSpeed, zRotation);
  }	
  
  public int getDistanceCM(){
    return distanceSensor.getDistance();
  }
	
  public double getDistance(){    
    int distance = distanceSensor.getDistance();
    double inches = distance / 2.54;
    return inches;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDriveWithJoystick());
  }

}
