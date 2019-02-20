/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class ToggleElevator extends InstantCommand {
  /**
   * Add your docs here.
   */
  public ToggleElevator() {
    super("ToggleElevator");
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.elevator);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    if(Robot.elevator.IsEnabled) {
      Robot.elevator.setDisabled();
    }
    else {
      Robot.elevator.setEnabled();
    }
  }

}
