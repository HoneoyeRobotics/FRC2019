/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Testing extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public DigitalInput towerLimitSwitch = new DigitalInput(3);
  
  //public DigitalInput armLimitSwitch = new DigitalInput(2);
  public AnalogInput armProxySensor = new  AnalogInput(1);


  public boolean getTowerSwitch(){
    return towerLimitSwitch.get();
  }
  // public boolean getArmSwitch(){
  //   return armLimitSwitch.get();
  // }

  public boolean cubeInArms(){
    return getArmProxySensor() < 1;
  }

  public double getArmProxySensor() {
    return armProxySensor.getVoltage();
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
