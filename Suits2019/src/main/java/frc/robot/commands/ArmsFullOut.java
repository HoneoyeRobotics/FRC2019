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
import frc.robot.lib.GamePiece;

public class ArmsFullOut extends Command {

  private int endEncoderValue = 0;
  private boolean AtEnd = false;
  public ArmsFullOut() {
    super("ArmsFullOut");
    requires(Robot.arms);
    setTimeout(6);
    setInterruptible(true);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(Robot.claw.currentGamePiece == GamePiece.Hatch) {
      endEncoderValue = RobotMap.armFwdRevEncoderHatchMax;
    } else {
      endEncoderValue = RobotMap.armFwdRevEncoderMax;
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.arms.getArmPosition() <= (endEncoderValue)){
      Robot.arms.moveArms(RobotMap.armFwdRevAutoSpeed);
    }
    else{
     Robot.arms.stop();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.arms.getArmPosition() >= endEncoderValue || isTimedOut();
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
