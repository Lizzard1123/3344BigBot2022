// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.LimelightClass;
import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ScanTurret extends CommandBase {
  private Turret turret;
  boolean lastCheckedLeft = false;

  public ScanTurret(Turret turret) {
    super();
    this.turret = turret;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Constants.flywheelAnalog = false; //set to PID auto
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(lastCheckedLeft){ //TODO check if this is the right orientation for the turret to spin ||  I dont think it matters tho
      turret.spin(Constants.turretMaxSpeed);
    } else {
      turret.spin(-1 * Constants.turretMaxSpeed);
    }
    if(turret.leftPressing() || turret.rightPressing())
      lastCheckedLeft = !lastCheckedLeft;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    LimelightClass Limelight = RobotContainer.limelight;
    if((Limelight.getWidth() >= Constants.scanWidth) && (Limelight.hasSight()))
      return true;
    return false;
  }
}
