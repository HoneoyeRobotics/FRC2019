/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.*;
import frc.robot.lib.ClawInOutState;

/**
 * Add your docs here.
 */
public class Elevator extends PIDSubsystem {
  public boolean IsEnabled =  false;

  private Encoder elevatorEncoder;
  private WPI_VictorSPX elevatorMotor;
  private int ElevatorPosition = 0;
  private int CurrentElevatorPosition = 0;

  public Elevator() {
    // Intert a subsystem name and PID values here
  
    super("Elevator", 0.025, 0.0, 0.01);
    elevatorMotor = new WPI_VictorSPX(RobotMap.elevatorMotorCanID);
    elevatorEncoder = new Encoder(RobotMap.elevatorEncoderADIO, RobotMap.elevatorEncoderBDIO);

    setInputRange(-10000.0, 10000.0);
    setAbsoluteTolerance(1.0);
    setOutputRange(-1.0, 1.0);

    // 498 pulses per rotation.  Set distance accordingly so we read encoder pulses.
    elevatorEncoder.setDistancePerPulse(498/360);
    setSetpoint(0);

    // Use these to get going:
    // setSetpoint() - Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.
  }

  public int getElevatorSetpoint(){
    return ElevatorPosition;
  }

  public void moveElevatorUp(){
    if(ElevatorPosition < 3) {
      ElevatorPosition++;
    }
  }

  public void moveElevatorDown() {
    if(ElevatorPosition > 0){
      ElevatorPosition--;
    }
  }

  public void displayElevatorSetpoint(){

  }

  public void runToSetpoint(){
    //if claw = out then we have a hatch
    if(Robot.claw.clawInOutState == ClawInOutState.OUT){
      setSetpoint(RobotMap.hatchEncoderPositions[ElevatorPosition]);
    } else {
      setSetpoint(RobotMap.ballEncoderPositions[ElevatorPosition]);
    }
    CurrentElevatorPosition = ElevatorPosition;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new AdjustElevator());
  }

  @Override
  protected double returnPIDInput() {
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    double encValue = elevatorEncoder.get();
    double encDistance = elevatorEncoder.getDistance();
    SmartDashboard.putNumber("Tower Encoder Value", encValue);
    SmartDashboard.putNumber("Tower Encoder Dist.", encDistance);
    return encValue;
  }

public void setDisabled(){
  disable();
  IsEnabled = false;
}

public void setEnabled(){
  enable();
  IsEnabled = true;
}

  public void initialize(){
    setSetpoint(0);
    enable();
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    elevatorMotor.set(output);
    SmartDashboard.putNumber("tower pid output", output);
  }
}
