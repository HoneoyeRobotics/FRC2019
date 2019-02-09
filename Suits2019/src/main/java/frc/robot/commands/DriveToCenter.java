/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveToCenter extends Command {

  private boolean Done = false;
  private double centerOffPixels = 0;
  private double tapeLength = 0;

  public DriveToCenter() {
    super("DriveToCenter");
    requires(Robot.driveTrain);
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
    //get data from drivers station
    centerOffPixels = Robot.table.getNumber("distance", 9999);
    if(centerOffPixels >= 9999){
      Done = true;
      return;
    }    

    tapeLength = Robot.table.getNumber("tapeLength", 1);
    double xSpeed = 0;
    double zRotation = 0;

    if(tapeLength >= RobotMap.visionTapeLengthRange){
      xSpeed = RobotMap.visionFwdSpeed;
    }

    if(centerOffPixels < (-1 * RobotMap.visionCenterDeadband)){
      zRotation = -1 * RobotMap.visionRotateSpeed;
    } else if(centerOffPixels > (RobotMap.visionCenterDeadband)){
        zRotation = RobotMap.visionRotateSpeed;
    } 

    Robot.driveTrain.arcadeDrive(xSpeed, zRotation);

    if(xSpeed == 0 && zRotation == 0){
      return;
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
