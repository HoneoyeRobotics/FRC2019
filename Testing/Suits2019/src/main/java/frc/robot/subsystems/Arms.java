/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.ArmImmediate;

/**
 * Add your docs here.
 */
public class Arms extends Subsystem {
  private WPI_VictorSPX elbowMotor;
  private WPI_VictorSPX leftArmWheelMotor;
  private WPI_VictorSPX rightArmWheelMotor;
  private SpeedControllerGroup armWheelMotors;
  private DigitalInput armPositionLimitSwitch;

  public Arms(){
    elbowMotor = new WPI_VictorSPX(RobotMap.elbowMotorCanID);
    leftArmWheelMotor = new WPI_VictorSPX(RobotMap.leftArmWheelCanID);
    rightArmWheelMotor = new WPI_VictorSPX(RobotMap.rightArmWheelCanID);  
      
    armWheelMotors = new SpeedControllerGroup(leftArmWheelMotor, rightArmWheelMotor);
    armPositionLimitSwitch = new DigitalInput(RobotMap.armLimitSwitchDIO);

    SmartDashboard.putData("Elbow Motor", elbowMotor);
    SmartDashboard.putData("Left Arm Motor", leftArmWheelMotor);
    SmartDashboard.putData("Right Arm Motor", rightArmWheelMotor);
  }


  public void runArmWheels(double speed){
    armWheelMotors.set(speed);
  }

  
  public boolean armsAtTop(){
    return armPositionLimitSwitch.get();
  }

  public void raiseArms(double speed){
    //if the arms are at the top, stop the motors.
    if(armsAtTop())
      speed = 0;
    //if speed is negative, make positive as we want to run the motor up.
    if(speed < 0)
      speed = speed * -1;
    elbowMotor.set(speed);
  }

  public void lowerArms(double speed){
    //if speed is a positive number, make it negative to run the motor down.
    if(speed > 0)
      speed = speed * -1;    
    elbowMotor.set(speed);
  }

  public void stop(){
    elbowMotor.set(0);
    armWheelMotors.set(0);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArmImmediate());
  }
}
