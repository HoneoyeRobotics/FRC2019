/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class ArcadeDriveWithJoystick extends Command {
  public ArcadeDriveWithJoystick() {
    requires(Robot.driveTrain);
    setInterruptible(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double xSpeed = OI.driverJoystick.getRawAxis(OI.driverJoystickForwardAxis);
    double leftTrigger= OI.driverJoystick.getRawAxis(OI.driverJoystickTurnLeftAxis);
		double rightTrigger = OI.driverJoystick.getRawAxis(OI.driverJoystickTurnRightAxis);				
    double zRotation = leftTrigger - rightTrigger;
    Robot.driveTrain.arcadeDrive(xSpeed, zRotation);


    //publish lidar to dashboard
    double distance = Robot.driveTrain.getDistance();
    SmartDashboard.putNumber("Distance (in)", distance);
    int distanceCM = Robot.driveTrain.getDistanceCM();
    SmartDashboard.putNumber("Distance (cm)", distanceCM);
  }

  // Make this return true when this Command no longer needs to run execute
  @Override
  protected boolean isFinished() {
    return false;
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

