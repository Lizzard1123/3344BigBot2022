// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.Autonomous;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.FlightController;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.myShuffleBoard;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static FlightController flightController = new FlightController(Constants.flightControllerPort);
  public static Drivetrain drivetrain = new Drivetrain();
  public static Intake intake = new Intake();
  public static Climber climber = new Climber();
  public static Flywheel flywheel = new Flywheel();
  public static Turret turret = new Turret();
  public static AnalogGyro gyro = new AnalogGyro(0); // double check port #
  //public static Limelight lime = new Limelight();
  public static Autonomous m_autoCommand = new Autonomous();
  public static myShuffleBoard shuffleBoardInterface = new myShuffleBoard();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //runs execute of driveCommand function indefinately
    drivetrain.setDefaultCommand(new DriveCommand(flightController, drivetrain, false)); // Robot orientation 
    //drivetrain.setDefaultCommand(new DriveCommand(flightController, drivetrain, true)); //Field orientation
    gyro.calibrate();
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //JoystickButton liftArm = new JoystickButton(flightController, FlightController.ButtonA);

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
