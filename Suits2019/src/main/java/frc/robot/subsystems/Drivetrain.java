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
import frc.robot.RobotMap;
import frc.robot.commands.ArcadeDriveWithJoystick;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {
  private WPI_TalonSRX frontLeftDriveMotor;
	private WPI_VictorSPX rearLeftDriveMotor;
	private SpeedControllerGroup leftDriveMotorGroup;
	
	private WPI_TalonSRX frontRightDriveMotor;
	private WPI_VictorSPX rearRightDriveMotor;	
  private SpeedControllerGroup rightDriveMotorGroup;
  
  private DifferentialDrive drivetrain;

  public Drivetrain(){
    frontLeftDriveMotor = new WPI_TalonSRX(RobotMap.frontLeftDriveMotorCanID);
    rearLeftDriveMotor = new WPI_VictorSPX(RobotMap.frontLeftDriveMotorCanID);
    leftDriveMotorGroup = new SpeedControllerGroup(frontLeftDriveMotor, rearLeftDriveMotor);
    
    frontRightDriveMotor = new WPI_TalonSRX(RobotMap.frontRightDriveMotorCanID);
    rearRightDriveMotor = new WPI_VictorSPX(RobotMap.frontRightDriveMotorCanID);
    rightDriveMotorGroup = new SpeedControllerGroup(frontRightDriveMotor, rearRightDriveMotor);
    
    drivetrain = new DifferentialDrive(leftDriveMotorGroup, rightDriveMotorGroup);
    SmartDashboard.putData("LF Drive (" + RobotMap.frontLeftDriveMotorCanID + ")",  frontLeftDriveMotor);
    SmartDashboard.putData("LR Drive (" + RobotMap.rearLeftDriveMotorCanID + ")",  rearLeftDriveMotor);
    SmartDashboard.putData("RF Drive (" + RobotMap.frontRightDriveMotorCanID + ")",  frontRightDriveMotor);
    SmartDashboard.putData("RR Drive (" + RobotMap.rearRightDriveMotorCanID + ")",  rearRightDriveMotor);
  }

  
	public void arcadeDrive(double xSpeed,double zRotation) {				
		drivetrain.arcadeDrive( xSpeed, zRotation);
	}	
	

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDriveWithJoystick());
  }
}
