/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ArmsFullIn extends Command {
  private boolean AtEnd = false;

  public ArmsFullIn() {
    super("ArmsFullIn");
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
    int armPos = Robot.arms.getArmPosition();
    if(armPos >= RobotMap.armFwdRevEncoderMin) {
      Robot.arms.moveArms(RobotMap.armFwdRevAutoSpeed * -1);      
    }
    else{
      Robot.arms.stop(); 
       }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    int pos = Robot.arms.getArmPosition();
    return pos <= RobotMap.armFwdRevEncoderMin;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.arms.stop();
    SmartDashboard.putBoolean("ArmsAutoMove", false);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
