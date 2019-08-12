/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/*
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.lib.GamePiece;

public class DriveToCenter extends Command {

  private boolean Done = false;
  private double centerOffPixels = 0;
  private double distance = 0;

  public DriveToCenter() {
    super("DriveToCenter");
    requires(Robot.driveTrain);
    setInterruptible(true);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.driveTrain.getVisionData();
    //get data from drivers station
    centerOffPixels = Robot.driveTrain.pixelsOff;
    if(centerOffPixels >= 9999 || Robot.oi.rightStickButton2.get() == false || Robot.driveTrain.foundImage == false){
      Done = true;
      return;
    }    

    distance = Robot.driveTrain.distance;    
    double targetDistance = RobotMap.visionDistanceFromCamToBumper;
    targetDistance += (Robot.claw.currentGamePiece == GamePiece.Ball ? RobotMap.visionDistanceBall : RobotMap.visionDistanceHatch);

    double xSpeed = 0;
    double zRotation = 0;

    if(distance >= targetDistance){
      xSpeed = RobotMap.visionFwdSpeed;
    }

    if(centerOffPixels < (-1 * RobotMap.visionCenterDeadband)){
      zRotation = -1 * RobotMap.visionRotateSpeed;
    } else if(centerOffPixels > (RobotMap.visionCenterDeadband)){
        zRotation = RobotMap.visionRotateSpeed;
    } 

    SmartDashboard.putNumber("DriveToCenter xSpeed", xSpeed);
    SmartDashboard.putNumber("DriveToCenter zRotation", zRotation);
    Robot.driveTrain.arcadeDrive(0, zRotation);

    if(xSpeed == 0 && zRotation == 0){
      Done = true;
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Done;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.arcadeDrive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
*/