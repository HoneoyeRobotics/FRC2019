/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AdjustTower;

/**
 * Add your docs here.
 */
public class Tower extends PIDSubsystem {
  /**
   * Add your docs here.
   */
  private static final int kMotorChannel = 7;
  private static final int kEncoderPortA = 1;
  private static final int kEncoderPortB = 2;

  private Encoder m_encoder;
  private WPI_VictorSPX m_elevatorMotor;

  public Tower() {
    // Intert a subsystem name and PID values here
  
    super("Tower", 0.025, 0.0, 0.01);
    m_elevatorMotor = new WPI_VictorSPX(kMotorChannel);
    m_encoder = new Encoder(kEncoderPortA, kEncoderPortB);

    setInputRange(-10000.0, 10000.0);
    setAbsoluteTolerance(1.0);
    setOutputRange(-1.0, 1.0);

    // 498 pulses per rotation.  Set distance accordingly so we read encoder pulses.
    m_encoder.setDistancePerPulse(498/360);

    // Use these to get going:
    // setSetpoint() - Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new AdjustTower());
  }

  @Override
  protected double returnPIDInput() {
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    double encValue = m_encoder.get();
    double encDistance = m_encoder.getDistance();
    SmartDashboard.putNumber("Tower Encoder Value", encValue);
    SmartDashboard.putNumber("Tower Encoder Dist.", encDistance);
    return encValue;
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    m_elevatorMotor.set(output);
    SmartDashboard.putNumber("tower pid output", output);
  }
}
