// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import frc.robot.commands.Autonomous;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.Index;
import frc.robot.commands.ScanTurret;
import frc.robot.commands.SpinFlywheel;
import frc.robot.commands.SpinTurret;
import frc.robot.commands.SpinUptake;
import frc.robot.commands.holdArm;
import frc.robot.commands.moveArm;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.FlightController;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LimelightClass;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Uptake;
import frc.robot.subsystems.MyShuffleBoard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static final PowerDistribution pdp = new PowerDistribution(60, ModuleType.kRev);
  public static final FlightController flightController = new FlightController(Constants.flightControllerPort);
  public static final Drivetrain drivetrain = new Drivetrain();
  public static final Intake intake = new Intake();
  public static final Climber climber = new Climber();
  public static final Flywheel flywheel = new Flywheel();
  public static final SpinFlywheel flywheelHandler = new SpinFlywheel(flywheel);
  public static final Turret turret = new Turret();
  public static final SpinTurret turretHandler = new SpinTurret(turret);
  public static final Uptake uptake = new Uptake();
  public static final AnalogGyro gyro = new AnalogGyro(0); // double check port #
  public static final LimelightClass limelight = new LimelightClass();
  //public static Limelight lime = new Limelight();
  public static final Autonomous m_autoCommand = new Autonomous();
  public static final MyShuffleBoard shuffleBoardInterface = new MyShuffleBoard();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //runs execute of driveCommand function indefinately
    drivetrain.setDefaultCommand(new DriveCommand(flightController, drivetrain, false)); // Robot orientation 
    //drivetrain.setDefaultCommand(new DriveCommand(flightController, drivetrain, true)); //Field orientation
    //default commmand for spinning turret
    turret.setDefaultCommand(turretHandler);
    //intake 
    intake.setDefaultCommand(new holdArm(intake));
    //flyhweel
    //flywheel.setDefaultCommand(flywheelHandler);
    //uptake indexer
    uptake.setDefaultCommand(new Index(uptake));
    //calibrate gyro
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

    JoystickButton spinUptakeUp = new JoystickButton(flightController, FlightController.Button5);
    spinUptakeUp.whileHeld(new SpinUptake(uptake, intake, false));

    JoystickButton spinUptakeDown = new JoystickButton(flightController, FlightController.Button6);
    spinUptakeDown.whileHeld(new SpinUptake(uptake, intake, true));

    JoystickButton armUp = new JoystickButton(flightController, FlightController.ButtonR2);
    armUp.whileHeld(new moveArm(intake, false));

    JoystickButton armDown = new JoystickButton(flightController, FlightController.ButtonL2);
    armDown.whileHeld(new moveArm(intake, true));

    JoystickButton scan = new JoystickButton(flightController, FlightController.Button8);
    scan.whileHeld(new ScanTurret(turret));
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
