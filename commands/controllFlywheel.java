// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Flywheel;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class controllFlywheel extends CommandBase {
  private Flywheel flywheel;

  public controllFlywheel(Flywheel flywheel) {
    super();
    this.flywheel = flywheel;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(flywheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Constants.manualOverride){
      if(RobotContainer.gunnerController.getPOV() == -1){
        flywheel.spin(-RobotContainer.gunnerController.getLeftJoyY());
      } else if(RobotContainer.gunnerController.getPOV() == 0){
        flywheel.spin(Constants.fullCourtSpeed/100);
      } else if(RobotContainer.gunnerController.getPOV() == 90){
        flywheel.spin(Constants.dumpBallSpeed/100);
      } else if(RobotContainer.gunnerController.getPOV() == 180){
        flywheel.spin(Constants.insideCircleSpeed/100);
      } else if(RobotContainer.gunnerController.getPOV() == 270){
        flywheel.spin(Constants.onCircleSpeed/100);
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
