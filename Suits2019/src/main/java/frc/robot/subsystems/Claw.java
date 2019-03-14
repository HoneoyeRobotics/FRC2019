/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import  frc.robot.lib.*;
import frc.robot.commands.*;

/**
 * Add your docs here.
 */
public class Claw extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private Compressor compressor;
  public ClawInOutState clawInOutState;
  private DoubleSolenoid  clawInOutSolenoid;
  public GamePiece currentGamePiece = GamePiece.None;
  private WPI_VictorSPX leftArmWheelMotor;
  private WPI_VictorSPX rightArmWheelMotor;
  private SpeedControllerGroup armWheelMotors;

  public Claw(){
    leftArmWheelMotor = new WPI_VictorSPX(RobotMap.leftArmWheelMotorCanID);
    rightArmWheelMotor = new WPI_VictorSPX(RobotMap.rightArmWheelMotorCanID);
    leftArmWheelMotor.setInverted(true);
    
    armWheelMotors = new SpeedControllerGroup(leftArmWheelMotor, rightArmWheelMotor);
    clawInOutSolenoid = new DoubleSolenoid (RobotMap.pneumaticControlModuleID, RobotMap.clawOutPCMID, RobotMap.clawInPCMID);
    SmartDashboard.putData("Claw In/Out Solenoid", clawInOutSolenoid);
    compressor = new Compressor(RobotMap.pneumaticControlModuleID);
    compressor.setClosedLoopControl(true);
    

  }

  public void openClaw(){
    if(clawInOutState == ClawInOutState.IN) {
      return;
    }
    clawInOutState = ClawInOutState.IN;
    clawInOutSolenoid.set(DoubleSolenoid.Value.kReverse);
    currentGamePiece = GamePiece.Hatch;
    
     //Robot.visionTable.putBoolean("hatch", true);
    displayGamePiece();
  }

  public void stopClaw(){
    clawInOutSolenoid.set(DoubleSolenoid.Value.kOff);
  }

  public void closeClaw(){
    if(clawInOutState == ClawInOutState.OUT){
      return;
    }
    clawInOutState = ClawInOutState.OUT;
    //Robot.visionTable.putBoolean("hatch", false);
    clawInOutSolenoid.set(DoubleSolenoid.Value.kForward);
    currentGamePiece = GamePiece.Ball;
    displayGamePiece();

  }


  public void runArmWheels(double speed){
    armWheelMotors.set(speed);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new RunClawWheels());
  }


  public void displayGamePiece() {
    String name = "";
    switch(currentGamePiece) {
      case Hatch:
        name = "hatch";
        break;
      case Ball:
        name = "ball";
        break;
      default:
        name = "none";
        break;
    }
    SmartDashboard.putString("gamepiece", name);
  }
}

