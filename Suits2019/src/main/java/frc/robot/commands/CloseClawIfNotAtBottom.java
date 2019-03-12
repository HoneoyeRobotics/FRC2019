/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CloseClawIfNotAtBottom extends Command {

  public CloseClawIfNotAtBottom(double timeout){
    super("CloseClawIfNotAtBottom");
    requires(Robot.claw);
    setTimeout(timeout);
  }
  public CloseClawIfNotAtBottom() {
    this(0.1);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(Robot.elevator.atFloor() == false){
      Robot.claw.closeClaw();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.claw.stopClaw();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
    }
}
