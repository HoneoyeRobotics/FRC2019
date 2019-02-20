// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.robot.subsystems;

// import com.ctre.phoenix.motorcontrol.NeutralMode;
// import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// import edu.wpi.first.wpilibj.Encoder;
// import edu.wpi.first.wpilibj.command.Subsystem;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.RobotMap;
// import frc.robot.commands.RunElevatorJoystick;

// /**
//  * Add your docs here.
//  */
// public class Elevator extends Subsystem {
//   // Put methods for controlling this subsystem
//   // here. Call these from Commands.

//   private Encoder elevatorEncoder;

//   private WPI_TalonSRX elevatorMotor;
//   public Elevator(){
//     elevatorMotor = new WPI_TalonSRX(RobotMap.elevatorMotorCanID);
//     elevatorEncoder = new Encoder(RobotMap.elevatorEncoderADIO, RobotMap.elevatorEncoderBDIO);
//     elevatorMotor.setNeutralMode(NeutralMode.Brake);
//     //SmartDashboard.putData("Elevator Motor", elevatorMotor);
//     SmartDashboard.putData("Elevator Encoder", elevatorEncoder);
//   }

//   public void runElevator(double  speed){
//     elevatorMotor.set(speed);
//     SmartDashboard.putNumber("Elevator Enc. Value", elevatorEncoder.get());
//   }
//   @Override
//   public void initDefaultCommand() {
//     // Set the default command for a subsystem here.
//     setDefaultCommand(new RunElevatorJoystick());;
//   }
// }
