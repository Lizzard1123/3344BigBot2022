// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class TurnTimed extends CommandBase {
  private Drivetrain drive;
  private double startTime;

  public TurnTimed(Drivetrain drive) {
    super();
    this.drive = drive;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() { 
    drive.spinLeft(Constants.autonForwardSpeed / 100);
    drive.spinRight(-Constants.autonForwardSpeed / 100);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.stopAll();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if((Timer.getFPGATimestamp() - startTime) >= Constants.autonTurnTime)
      return true;
    return false;
  }
}
