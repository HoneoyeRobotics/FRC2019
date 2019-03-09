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
 
  public final int driverJoystickForwardAxis = 1;  //left stick, left and right
  public final int driverJoystickTurnLeftAxis = 2;   // left trigger
  public final int driverJoystickTurnRightAxis = 3;   // right trigger

  public final int armWheelAxis = 4; //right stick, x axis
  public final int elbowAxis = 5; //right stick, y axis
  
  // public Joystick driverJoystick = new Joystick(0); 
  public Joystick secondaryJoystick = new Joystick(1);

  public Joystick leftTankStick = new Joystick(0);
  public Joystick rightTankStick = new Joystick(2);
  // nyi
  // public JoystickButton driverButtonA = new JoystickButton(driverJoystick, 1);  
  // public JoystickButton driverButtonB  = new JoystickButton(driverJoystick, 2); // drive slow button
  // public JoystickButton driverButtonX = new JoystickButton(driverJoystick, 3);   
  // public JoystickButton driverButtonY = new JoystickButton(driverJoystick, 4); // drive to center



  public JoystickButton secondaryButtonA = new JoystickButton(secondaryJoystick, 1);  
  public JoystickButton secondaryButtonB  = new JoystickButton(secondaryJoystick, 2);
  public JoystickButton secondaryButtonX = new JoystickButton(secondaryJoystick, 3);
  public JoystickButton secondaryButtonY = new JoystickButton(secondaryJoystick, 4);
  public JoystickButton secondaryButtonLB = new JoystickButton(secondaryJoystick, 5);
  public JoystickButton secondaryButtonRB  = new JoystickButton(secondaryJoystick, 6);
  public JoystickButton secondaryButtonBack = new JoystickButton(secondaryJoystick, 7);
  public JoystickButton secondaryButtonStart = new JoystickButton(secondaryJoystick, 8);


  public final int secondaryLStickXAxis = 0;
  public final int secondaryLStickYAxis = 1;
  public final int secondaryLTAxis = 2;
  public final int secondaryRTAxis = 3;
  public final int secondaryRStickXAxis = 4;
  public final int secondaryRStickYAxis = 5;


  public OI() {    

    //driverButtonY.whileHeld(new DriveToCenter());

    secondaryButtonB.whileHeld(new GetBall());
    secondaryButtonB.whenReleased(new GetBallFinished());

    secondaryButtonLB.whenPressed(new ArmsFullIn());
    secondaryButtonRB.whenPressed(new ArmsFullOut());
    
    secondaryButtonX.whenPressed(new OpenClaw());
    secondaryButtonY.whenPressed(new CloseClaw());

    secondaryButtonBack.whenPressed(new GetHatch());
  }
}

