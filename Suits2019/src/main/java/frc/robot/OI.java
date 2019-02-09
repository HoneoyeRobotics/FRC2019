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
  public static Joystick secondaryJoystick = new Joystick(1);

  public static JoystickButton driverButtonA = new JoystickButton(driverJoystick, 1);
  public static JoystickButton driverButtonB  = new JoystickButton(driverJoystick, 2);
  public static JoystickButton driverButtonX = new JoystickButton(driverJoystick, 3);
  public static JoystickButton driverButtonY = new JoystickButton(driverJoystick, 4);



  public static JoystickButton secondaryButtonA = new JoystickButton(secondaryJoystick, 1);
  public static JoystickButton secondaryButtonB  = new JoystickButton(secondaryJoystick, 2);
  public static JoystickButton secondaryButtonX = new JoystickButton(secondaryJoystick, 3);
  public static JoystickButton secondaryButtonY = new JoystickButton(secondaryJoystick, 4);
  public static JoystickButton secondaryButtonLB = new JoystickButton(secondaryJoystick, 5);
  public static JoystickButton secondaryButtonRB  = new JoystickButton(secondaryJoystick, 6);
  public static JoystickButton secondaryButtonBack = new JoystickButton(secondaryJoystick, 7);
  public static JoystickButton secondaryButtonStart = new JoystickButton(secondaryJoystick, 8);


  public static final int secondaryLStickXAxis = 0;
  public static final int secondaryLStickYAxis = 1;
  public static final int secondaryLTAxis = 2;
  public static final int secondaryRTAxis = 3;
  public static final int secondaryRStickXAxis = 4;
  public static final int secondaryRStickYAxis = 5;


  public OI() {    


  }
}

