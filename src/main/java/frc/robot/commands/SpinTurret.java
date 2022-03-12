// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Turret;

/** An example command that uses an example subsystem. */
public class SpinTurret extends CommandBase {
  private final Turret turret;
  public PIDController pid;

  //construtor
  public SpinTurret(Turret turret) {
    super();
    this.turret = turret;
    pid = new PIDController(0,0,0);
    addRequirements(turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = pid.calculate(RobotContainer.limelight.getXOffset(), 0);
    if(Math.abs(speed) > (20 * (Constants.turretMaxSpeed/100))){
      turret.spin(speed);
    } else {
      turret.stop();
    }
    /*
    if(Constants.flywheelAnalog){ // manual controlls
      turret.spin(RobotContainer.flightController.getAxisSlider());
    } else { //PIDS
      turret.spin(pid.calculate(RobotContainer.limelight.getXOffset(), 0));
    }*/
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
