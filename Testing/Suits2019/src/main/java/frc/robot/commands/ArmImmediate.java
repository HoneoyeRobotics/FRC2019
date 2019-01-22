/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class ArmImmediate extends Command {
  public ArmImmediate() {
    requires(Robot.arms);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //get raw axis for the elbow
    double elbowAxis = OI.driverJoystick.getRawAxis(OI.elbowAxis);
    if((elbowAxis > 0 && elbowAxis < 0.10) || (elbowAxis < 0 && elbowAxis > -0.10)){
      elbowAxis = 0;
    }
    //if it is a positive value, call the raise arm function
    if(elbowAxis >= 0) {
      Robot.arms.raiseArms(elbowAxis);
    }
    else {
      Robot.arms.lowerArms(elbowAxis);
    }
    double armWheelSpeed = OI.driverJoystick.getRawAxis(OI.armWheelAxis);
    //if arm speed is betweeen 0 and 0.10, or arm speed is between 0 and -0.10, set to 0
    if((armWheelSpeed > 0 && armWheelSpeed < 0.10) || (armWheelSpeed < 0 && armWheelSpeed > -0.10)){
      armWheelSpeed = 0;
    }
    Robot.arms.runArmWheels(armWheelSpeed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.arms.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
