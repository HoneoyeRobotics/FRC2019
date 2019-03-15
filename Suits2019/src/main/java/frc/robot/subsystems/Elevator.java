/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
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

  private Encoder elevatorEncoder;
  private WPI_TalonSRX elevatorMotor;
  private int ElevatorPosition = 0;
  private int CurrentElevatorPosition = 0;
  private double encoderValue;

  public Elevator() {
    // Intert a subsystem name and PID values here
  
    super("Elevator", 0.008, 0.0, 0.01);
    elevatorMotor = new WPI_TalonSRX(RobotMap.elevatorMotorCanID);
    elevatorEncoder = new Encoder(RobotMap.elevatorEncoderADIO, RobotMap.elevatorEncoderBDIO);
    
    //setInputRange(-10000.0, 10000.0);
    setAbsoluteTolerance(1.0);
    setOutputRange(-.20, 0.8);

    // 498 pulses per rotation.  Set distance accordingly so we read encoder pulses.
    elevatorEncoder.setDistancePerPulse(498/360);
    setSetpoint(0);
  }

  public void goToDrive(){
    RobotMap.getSetPoints();
    setSetpoint(RobotMap.hatchEncoderPositions[1]);
    CurrentElevatorPosition = 1;
    ElevatorPosition = 1;
    enable();
    displayElevatorSetpoint();
  }
  public void goToFloor(){
    setSetpoint(RobotMap.hatchEncoderPositions[0]);
    CurrentElevatorPosition = 0;
    ElevatorPosition = 0;
    enable();
    displayElevatorSetpoint();
  }
  public void getHatch(){
    RobotMap.getSetPoints();
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
    //move it up from a range of 0 <> 2
    if(ElevatorPosition < 3) {
      ElevatorPosition++;
      if(ElevatorPosition >= 3){
        ElevatorPosition = 3;
      }
    }
    displayElevatorSetpoint();
  }

  public void moveElevatorDown() {
    //move it down from a range of 0 <> 2
    if(ElevatorPosition > 0){
      ElevatorPosition--;
    }
    else{
        ElevatorPosition = 0;
    }
    //if we have a hatch, make sure you are at setpoint 1 and not 0 so it does not drag on the floor.
    if(ElevatorPosition == 0 && Robot.claw.currentGamePiece == GamePiece.Hatch) {
      ElevatorPosition = 1;
    }
    displayElevatorSetpoint();
  }

  public void runToSetpoint(){    
    int setpoint = 0;
    //if claw = out then we have a hatch
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

  public boolean atFloor(){
      return CurrentElevatorPosition == 0  && returnPIDInput() < 80;
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
    encoderValue = elevatorEncoder.get();
   // double encDistance = elevatorEncoder.getDistance();
    SmartDashboard.putNumber("Tower Encoder Value", encoderValue);
    //SmartDashboard.putNumber("Tower Encoder Dist.", encDistance);
    return encoderValue;
  }

  public void initialize(){
    setSetpoint(0);
    enable();
  }

  public void moveSetpoint(double setpoint){
    

    //Preferences prefs = Preferences.getInstance();
    //int currentSetpoint = RobotMap.hatchEncoderPositions[1];
    //currentSetpoint += setpoint;
    setSetpoint(getSetpoint() + setpoint);
    // RobotMap.hatchEncoderPositions[1] = currentSetpoint;
    // prefs.putInt("hatchEncoderPosition2", 325);
    // setSetpoint(setpoint);
  }

  @Override
  protected void usePIDOutput(double output) {
    //if the elevator is close to the floor, set output to 0 to prevent the motor from running down if it gets stuck
    if(atFloor()) {
        output = 0;
    }
    elevatorMotor.set(output);
    //display output to check on motor power
    SmartDashboard.putNumber("tower pid output", output);
  }
 
  public String getSetpointDescr(int Setpoint){
    switch(Setpoint){
      case 1:
        return "Driving (1)";        
      case 2: 
        return "Scoring (2)";
        
      case 3: 
        return "Scoring Top (3)";
      default:
        return "Floor (0)";
    }
  }
  
  public void displayElevatorSetpoint(){
      SmartDashboard.putString("Ele. Current Setpoint", getSetpointDescr(CurrentElevatorPosition));
      SmartDashboard.putString("Ele. Requested Setpoint", getSetpointDescr(ElevatorPosition));
  }
}
