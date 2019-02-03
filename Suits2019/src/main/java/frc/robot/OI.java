/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
 
  public static final int driverJoystickForwardAxis = 1;  //left stick, left and right
  public static final int driverJoystickTurnLeftAxis = 2;   // left trigger
  public static final int driverJoystickTurnRightAxis = 3;   // right trigger

  public static final int armWheelAxis = 4; //right stick, x axis
  public static final int elbowAxis = 5; //right stick, y axis
  
  public static Joystick driverJoystick = new Joystick(0); 

  public static JoystickButton driverButtonA = new JoystickButton(driverJoystick, 1);
  public static JoystickButton driverButtonB  = new JoystickButton(driverJoystick, 2);
  public static JoystickButton driverButtonX = new JoystickButton(driverJoystick, 3);
  public static JoystickButton driverButtonY = new JoystickButton(driverJoystick, 4);

  public OI() {    
    driverButtonX.whenPressed(new EnableTower());
    driverButtonY.whenPressed(new LiftTower());
    driverButtonA.whenPressed(new ResetTower());
    driverButtonB.whenPressed(new DisableTower());

  }
}

