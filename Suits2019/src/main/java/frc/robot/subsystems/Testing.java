/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Testing extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public DigitalInput digitalSensor1 = new DigitalInput(1);
  public AnalogInput analogSensor1 = new  AnalogInput(1);


  public boolean getDigitalSensor1(){
    return digitalSensor1.get();
  }

  
  public double getAnalogSensor1() {
    return analogSensor1.getVoltage();
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
