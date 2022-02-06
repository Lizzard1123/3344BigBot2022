// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.FlightController;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class DriveCommand extends CommandBase {
  private final Drivetrain drivetrain;
  private final FlightController ctr;
  private final Boolean fieldOrientated;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveCommand(FlightController flightController, Drivetrain drivetrain, Boolean fieldOrientated) {
    this.drivetrain = drivetrain;
    this.ctr = flightController;
    this.fieldOrientated = fieldOrientated;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.drive(ctr.getAxisX(), ctr.getAxisY(), ctr.getAxisRZ(), 
                      fieldOrientated?RobotContainer.gyro.getAngle():0.0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; //always false bc its driver program
  }
}
