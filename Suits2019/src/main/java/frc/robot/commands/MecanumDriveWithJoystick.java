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

public class MecanumDriveWithJoystick extends Command {
  public MecanumDriveWithJoystick() {
    super("MecanumDriveWithJoystick");
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
      //this is code for the arcade style
      
      double ySpeed = Robot.oi.driverJoystick.getRawAxis(Robot.oi.driverJoystickForwardAxis);
      double leftTrigger= Robot.oi.driverJoystick.getRawAxis(Robot.oi.driverJoystickTurnLeftAxis);
      double rightTrigger = Robot.oi.driverJoystick.getRawAxis(Robot.oi.driverJoystickTurnRightAxis);				
      double zRotation = leftTrigger - rightTrigger;
      double xSpeed = Robot.oi.driverJoystick.getRawAxis(Robot.oi.driverJoystickStrafeAxis);

      // if(Robot.oi.driverButtonB.get()){
      // if(zRotation > RobotMap.slowSpeedModifier){
      //   zRotation = RobotMap.slowSpeedModifier;
      // }
      // if(xSpeed > RobotMap.slowSpeedModifier){
      //   xSpeed = RobotMap.slowSpeedModifier;
      // }
      // }
      // Robot.driveTrain.arcadeDrive(xSpeed, zRotation);

        Robot.driveTrain.mecanumDrive(ySpeed, xSpeed, zRotation);
  }

  // Make this return true when this Command no longer needs to run execute
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.mecanumDrive(0, 0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}

