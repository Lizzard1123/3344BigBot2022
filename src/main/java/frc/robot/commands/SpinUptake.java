// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Uptake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class SpinUptake extends CommandBase {
  private final Uptake uptake;
  private final Intake intake;
  private boolean reverse = false;

  public SpinUptake(Uptake uptake, Intake intake, boolean reverse) {
    super();
    this.uptake = uptake;
    this.intake = intake;
    this.reverse = reverse;
    addRequirements(uptake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(!Constants.holdingBall)
      uptake.spin(Constants.uptakeMaxSpeed * (reverse?-1:1));
    intake.intakeSpin(Constants.intakeMaxSpeed * (reverse?-1:1));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    uptake.stop();
    intake.intakeStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
