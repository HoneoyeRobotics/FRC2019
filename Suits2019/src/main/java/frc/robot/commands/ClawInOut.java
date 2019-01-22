/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ClawInOut extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ClawInOut() {
    addSequential(new ClawOut(0.1));
    addSequential(new Wait(1));
    addSequential(new ClawIn(0.1));
    
  }
}
