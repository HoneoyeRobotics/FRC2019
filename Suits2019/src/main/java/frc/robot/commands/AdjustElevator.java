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
import frc.robot.lib.GamePiece;

public class AdjustElevator extends Command {

  double LastPOV = -1;

  public AdjustElevator() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    LastPOV = -1;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double POV = Robot.oi.secondaryJoystick.getPOV();
   // double origSetpoint = Robot.elevator.getSetpoint();
    SmartDashboard.putNumber("POV",POV);
    if(POV == LastPOV)
      return;
    LastPOV = POV;
    if(POV == 0){
      Robot.elevator.moveElevatorUp();
    } else if (POV == 180){
      Robot.elevator.moveElevatorDown();
    }
  
    Robot.elevator.runToSetpoint();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
