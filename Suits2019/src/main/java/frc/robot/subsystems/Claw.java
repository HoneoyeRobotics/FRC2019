/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import  frc.robot.lib.*;

/**
 * Add your docs here.
 */
public class Claw extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private Compressor compressor = new Compressor(RobotMap.pneumaticControlModuleID);
  public ClawInOutState clawInOutState;

  private DoubleSolenoid  clawInOutSolenoid = new DoubleSolenoid (RobotMap.pneumaticControlModuleID, RobotMap.clawOutPCMID, RobotMap.clawInPCMID);

  public GamePiece currentGamePiece = GamePiece.None;

  public Claw(){
    SmartDashboard.putData("Claw In/Out Solenoid", clawInOutSolenoid);
    compressor.setClosedLoopControl(true);
  }

  public void openClaw(){
    if(clawInOutState == ClawInOutState.IN) {
      return;
    }
    clawInOutState = ClawInOutState.IN;
    clawInOutSolenoid.set(DoubleSolenoid.Value.kReverse);
    currentGamePiece = GamePiece.Hatch;
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
    clawInOutSolenoid.set(DoubleSolenoid.Value.kForward);
    currentGamePiece = GamePiece.Ball;
    displayGamePiece();

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
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

