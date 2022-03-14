// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Uptake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Shoot extends CommandBase {
  private Uptake uptake;
  private boolean rightColor;
  private boolean hasShot = false;

  public Shoot(Uptake uptake, boolean rightColor) {
    super();
    this.uptake = uptake;
    this.rightColor = rightColor;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(uptake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(!rightColor){
      eject();
    } else {
      tryToShoot();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return hasShot;
  }

  private void eject(){
    new spinUptakeTimed(uptake);
    hasShot = true;
  }

  private void tryToShoot(){
    if(RobotContainer.flywheelHandler.withinTolerance() && RobotContainer.turretHandler.withinTolerance())
      new spinUptakeTimed(uptake);
      hasShot = true;
  }
}
