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

public class ArmsInOut extends Command {
  public ArmsInOut() {
    super("ArmsInOut");
    requires(Robot.arms);
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
    double motorSpeed = Robot.oi.secondaryJoystick.getRawAxis(Robot.oi.secondaryRTAxis);
    motorSpeed = motorSpeed - Robot.oi.secondaryJoystick.getRawAxis(Robot.oi.secondaryLTAxis);
    int armPos = Robot.arms.getArmPosition();
    if((motorSpeed < 0 && armPos <= RobotMap.armFwdRevEncoderMin) || (motorSpeed > 0 && armPos > RobotMap.armFwdRevEncoderMax)){
      if(Robot.oi.secondaryButtonStart.get() == false){
        motorSpeed = 0;
      }
    }
    Robot.arms.moveArms(motorSpeed);
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
