/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.*;
import frc.robot.lib.ClawInOutState;
import frc.robot.lib.GamePiece;

/**
 * Add your docs here.
 */
public class Elevator extends PIDSubsystem {
  public boolean IsEnabled =  false;

  private Encoder elevatorEncoder;
  private WPI_TalonSRX elevatorMotor;
  private int ElevatorPosition = 0;
  private int CurrentElevatorPosition = 0;


  public Elevator() {
    // Intert a subsystem name and PID values here
  
    super("Elevator", 0.025, 0.0, 0.01);
    elevatorMotor = new WPI_TalonSRX(RobotMap.elevatorMotorCanID);
    elevatorEncoder = new Encoder(RobotMap.elevatorEncoderADIO, RobotMap.elevatorEncoderBDIO);
    
    //setInputRange(-10000.0, 10000.0);
    setAbsoluteTolerance(1.0);
    setOutputRange(-.20, 1);

    // 498 pulses per rotation.  Set distance accordingly so we read encoder pulses.
    elevatorEncoder.setDistancePerPulse(498/360);
    setSetpoint(0);
  }
  public void goToFloor(){
    setSetpoint(RobotMap.hatchEncoderPositions[0]);
    CurrentElevatorPosition = 0;
    ElevatorPosition = 0;
    enable();
    displayElevatorSetpoint();
  }
  public void getHatch(){
    setSetpoint(RobotMap.hatchEncoderPositions[2]);
    CurrentElevatorPosition = 2;
    ElevatorPosition = 2;
    enable();
    displayElevatorSetpoint();
  }
  public int getElevatorSetpoint(){
    displayElevatorSetpoint();
    return ElevatorPosition;
  }

  public void moveElevatorUp(){
    if(ElevatorPosition < 2) {
      ElevatorPosition++;
      if(ElevatorPosition >= 2){
        ElevatorPosition = 2;
      }
    }
    displayElevatorSetpoint();
  }

  public void moveElevatorDown() {
    if(ElevatorPosition > 0){
      ElevatorPosition--;
    }
    else{
        ElevatorPosition = 0;
    }

    if(ElevatorPosition == 0 && Robot.claw.currentGamePiece == GamePiece.Hatch) {
      ElevatorPosition = 1;
    }
    displayElevatorSetpoint();
  }

  public String getSetpointDescr(int Setpoint){
    switch(Setpoint){
      case 1:
        return "Driving (1)";        
      case 2: 
        return "Scoring (2)";
      case 3:
        return "Top (3)";
      default:
        return "Floor (0)";
    }
  }
  public void displayElevatorSetpoint(){
      SmartDashboard.putString("Ele. Current Setpoint", getSetpointDescr(CurrentElevatorPosition));
      SmartDashboard.putString("Ele. Requested Setpoint", getSetpointDescr(ElevatorPosition));
  }

  public void runToSetpoint(){
    //if claw = out then we have a hatch
    int setpoint = 0;
    if(Robot.claw.currentGamePiece == GamePiece.Hatch){
        setpoint = RobotMap.hatchEncoderPositions[ElevatorPosition];
    } else {
      setpoint = RobotMap.ballEncoderPositions[ElevatorPosition];
    }
    setSetpoint(setpoint);
    CurrentElevatorPosition = ElevatorPosition;
    enable();
    displayElevatorSetpoint();
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
   // double encDistance = elevatorEncoder.getDistance();
    SmartDashboard.putNumber("Tower Encoder Value", encValue);
    //SmartDashboard.putNumber("Tower Encoder Dist.", encDistance);
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

    if(CurrentElevatorPosition == 0  && returnPIDInput() < 10){
        output = 0;
    }
    elevatorMotor.set(output);
    SmartDashboard.putNumber("tower pid output", output);
  }
}
