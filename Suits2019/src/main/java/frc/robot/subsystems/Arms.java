/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.ArmsInOut;

/**
 * Add your docs here.
 */
public class Arms extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private TalonSRX armFwdRevMotor;
  //private Encoder armFwdRevEncoder;


  public Arms(){
    armFwdRevMotor = new TalonSRX(RobotMap.armFwdRevMotorCanID);
    armFwdRevMotor.setNeutralMode(NeutralMode.Brake);
    armFwdRevMotor.setSensorPhase(false);
    armFwdRevMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    //armFwdRevEncoder = new Encoder(RobotMap.armFwdRevEncoderADIO, RobotMap.armFwdRevEncoderBDIO);
  }

  public int getArmPosition(){
    return armFwdRevMotor.getSelectedSensorPosition();
    //return armFwdRevEncoder.get();
  }

  public void resetArmPositionEncoder(){
    armFwdRevMotor.setSelectedSensorPosition(0);
    //armFwdRevEncoder.reset();
  }

  public void stop(){
    armFwdRevMotor.set(ControlMode.PercentOutput, 0);
  }

  public void moveArms(double speed){
    armFwdRevMotor.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArmsInOut());
  }
}
