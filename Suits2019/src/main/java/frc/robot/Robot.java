/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.*;
import edu.wpi.first.cameraserver.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.subsystems.*;
import frc.robot.commands.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //declare subsystems
  public static Claw claw;
  public static DriveTrain driveTrain;
  public static OI oi;
  Thread m_visionThread;
  public static Tower tower;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    driveTrain  = new DriveTrain();
    claw = new Claw();
    tower = new Tower();
    oi = new OI();
    //add commands to dashboard    
    SmartDashboard.putData(Robot.tower);
    SmartDashboard.putData("Open Claw", new OpenClaw());    
    SmartDashboard.putData("Close Claw", new CloseClaw());
  }

  public void cameraInit() {
    m_visionThread = new Thread(() -> {
      // Get the Axis camera from CameraServer
      AxisCamera camera
          = CameraServer.getInstance().addAxisCamera("axis-camera.local");
      // Set the resolution
      camera.setResolution(640, 480);

      // Get a CvSink. This will capture Mats from the camera
      CvSink cvSink = CameraServer.getInstance().getVideo();
      // Setup a CvSource. This will send images back to the Dashboard
      CvSource outputStream
          = CameraServer.getInstance().putVideo("Rectangle", 640, 480);

      // Mats are very memory expensive. Lets reuse this Mat.
      Mat mat = new Mat();

      // This cannot be 'true'. The program will never exit if it is. This
      // lets the robot stop this thread when restarting robot code or
      // deploying.
      while (!Thread.interrupted()) {
        // Tell the CvSink to grab a frame from the camera and put it
        // in the source mat.  If there is an error notify the output.
        if (cvSink.grabFrame(mat) == 0) {
          // Send the output the error.
          outputStream.notifyError(cvSink.getError());
          // skip the rest of the current iteration
          continue;
        }
        // Put a rectangle on the image
        Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400),
            new Scalar(255, 255, 255), 5);
        // Give the output stream a new image to display
        outputStream.putFrame(mat);
      }
    });
    m_visionThread.setDaemon(true);
    m_visionThread.start();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }


  @Override
  public void disabledInit() {
    tower.disable();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    tower.setSetpoint(0);
    tower.enable();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
