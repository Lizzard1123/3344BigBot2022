// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Uptake;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class spinUptakeTimed extends CommandBase {
  private Uptake uptake;
  private double startTime;

  public spinUptakeTimed(Uptake uptake) {
    this.uptake = uptake;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(uptake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    uptake.uptake.set(ControlMode.PercentOutput, -(Constants.uptakeMaxSpeed/100));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    uptake.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if((Timer.getFPGATimestamp() - startTime) >= Constants.shootTime)
      return true;
    return false;
  }
}
