/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class TeleopInitCommand extends CommandGroup {
  /**
   * Add your docs here.
   */
  public TeleopInitCommand() {
    requires(Robot.claw);
    requires(Robot.arms);
    requires(Robot.elevator);

    addSequential(new OpenClaw());
    addSequential(new Wait(0.5));
    addSequential(new MoveElevatorToDrive());
  }
}
