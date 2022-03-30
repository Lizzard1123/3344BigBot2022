// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Autonomous;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.Index;
import frc.robot.commands.MoveAndShoot;
import frc.robot.commands.MoveClimber;
import frc.robot.commands.ScanTurret;
import frc.robot.commands.SpinFlywheel;
import frc.robot.commands.SpinIntake;
import frc.robot.commands.SpinTurret;
import frc.robot.commands.SpinUptake;
import frc.robot.commands.controllFlywheel;
import frc.robot.commands.moveClimberGimbal;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LimelightClass;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Uptake;
import frc.robot.subsystems.XBox;
import frc.robot.subsystems.MyShuffleBoard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
 //we won lol
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static final PowerDistribution pdp = new PowerDistribution(60, ModuleType.kRev);
  //public static final FlightController flightController = new FlightController(Constants.flightControllerPort);
  public static final XBox driverController = new XBox(0);
  public static final XBox gunnerController = new XBox(1);
  public static final Drivetrain drivetrain = new Drivetrain();
  public static final Intake intake = new Intake();
  public static final Climber climber = new Climber();
  public static final Flywheel flywheel = new Flywheel();
  public static final PIDController flywheelPID = new PIDController(0, 0, 0);
  public static SpinFlywheel flywheelHandler = new SpinFlywheel(flywheel, false, flywheelPID);
  public static final Turret turret = new Turret();
  public static final SpinTurret turretHandler = new SpinTurret(turret);
  public static final Uptake uptake = new Uptake();
  public static final AnalogGyro gyro = new AnalogGyro(0); // double check port #
  public static final LimelightClass limelight = new LimelightClass();
  //public static Limelight lime = new Limelight();
  public static final Autonomous m_autoCommand = new Autonomous();
  public static final MyShuffleBoard shuffleBoardInterface = new MyShuffleBoard();

  public static final SendableChooser<SequentialCommandGroup> autoChooser = new SendableChooser<SequentialCommandGroup>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //runs execute of driveCommand function indefinately
    drivetrain.setDefaultCommand(new DriveCommand(driverController, drivetrain, false)); // Robot orientation 
    //drivetrain.setDefaultCommand(new DriveCommand(flightController, drivetrain, true)); //Field orientation
    //default commmand for spinning turret
    turret.setDefaultCommand(turretHandler);
    //flyhweel
    //flywheel.setDefaultCommand(flywheelHandler, false); if working with manual control
    //climber.setDefaultCommand(new moveClimberGimbal(climber, true, false, 0));
    //uptake indexer
    uptake.setDefaultCommand(new Index(uptake));
    //calibrate gyro
    gyro.calibrate();
    // Configure the button bindings
    configureButtonBindings();
    putChooser();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    //JoystickButton spinUptakeUp = new JoystickButton(driverController, XBox.LB_BUTTON);
    //spinUptakeUp.whileHeld(new SpinUptake(uptake, false));
    JoystickButton spinIntakeUp = new JoystickButton(driverController, XBox.LB_BUTTON);
    spinIntakeUp.whileHeld(new SpinIntake(intake, false));
    
    //JoystickButton spinUptakeDown = new JoystickButton(driverController, XBox.RB_BUTTON);
    //spinUptakeDown.whileHeld(new SpinUptake(uptake, true));
    JoystickButton spinIntakeDown = new JoystickButton(driverController, XBox.RB_BUTTON);
    spinIntakeDown.whileHeld(new SpinIntake(intake, true));
    
    JoystickButton moveClimberUp = new JoystickButton(driverController, XBox.Y_BUTTON);
    moveClimberUp.whileHeld(new MoveClimber(climber, true));
    JoystickButton moveClimberDown = new JoystickButton(driverController, XBox.A_BUTTON);
    moveClimberDown.whileHeld(new MoveClimber(climber, false));

    JoystickButton moveClimberGimbalUp = new JoystickButton(driverController, XBox.X_BUTTON);
    moveClimberGimbalUp.whileHeld(new moveClimberGimbal(climber, false, true, Constants.defaultGimbal));
    JoystickButton moveClimberGimbalDown = new JoystickButton(driverController, XBox.B_BUTTON);
    moveClimberGimbalDown.whileHeld(new moveClimberGimbal(climber, true, true, 0));

    //JoystickButton scan = new JoystickButton(driverController, XBox.B_BUTTON);
    //scan.whileHeld(new ScanTurret(turret));

    //other controller controlling the shooting
    JoystickButton gunnerSpinUptakeUp = new JoystickButton(gunnerController, XBox.Y_BUTTON);
    gunnerSpinUptakeUp.whileHeld(new SpinUptake(uptake, true));
    JoystickButton gunnerSpinUptakeDown = new JoystickButton(gunnerController, XBox.A_BUTTON);
    gunnerSpinUptakeDown.whileHeld(new SpinUptake(uptake, false));

    //JoystickButton gunnerSpinIntakeUp = new JoystickButton(gunnerController, XBox.Y_BUTTON);
    //gunnerSpinIntakeUp.whileHeld(new SpinIntake(intake, true));
    //JoystickButton gunnerSpinIntakeDown = new JoystickButton(gunnerController, XBox.A_BUTTON);
    //gunnerSpinIntakeDown.whileHeld(new SpinIntake(intake, false));
    
    JoystickButton spinFlywheel = new JoystickButton(gunnerController, XBox.X_BUTTON);
    spinFlywheel.whileHeld(new controllFlywheel(flywheel));

    //JoystickButton gunnerScan = new JoystickButton(gunnerController, XBox.B_BUTTON);
    //gunnerScan.whileHeld(new ScanTurret(turret));
    
  }

  public void putChooser() {
    autoChooser.setDefaultOption("Nothing", new SequentialCommandGroup());
    autoChooser.addOption("Shoot and Move", new MoveAndShoot());

    SmartDashboard.putData(autoChooser);
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoChooser.getSelected();
  }
}
