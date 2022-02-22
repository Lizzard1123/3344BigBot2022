// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class SpinFlywheel extends CommandBase {
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public SpinFlywheel() {
    super();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Constants.flyhweelAnalog){
      RobotContainer.flywheel.manualControl(RobotContainer.flightController.getThrottle());
    } else {
      //going to be the place where pid controlls it
      //RobotContainer.flywheel.automatedControl(Constants.flywheelMaxSpeed);
      //RobotContainer.flywheel.manualControl(Constants.flywheelMaxSpeed);
    }
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
