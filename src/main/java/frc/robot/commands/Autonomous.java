// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.RobotContainer;

/** An example command that uses an example subsystem. */
public class Autonomous extends SequentialCommandGroup {

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public Autonomous() {
   if(Constants.goForAuton){
    addCommands(
      //turret to the right
      new SpinTurretSpeed(RobotContainer.turret, .4),
      new WaitCommand(.2),
      new SpinTurretSpeed(RobotContainer.turret, 0),
      //intake
      new SpinIntake(RobotContainer.intake, false),
      new MoveTimed(RobotContainer.drivetrain),
      new TurnTimed(RobotContainer.drivetrain),
      new ScanTurret(RobotContainer.turret)
    );
   }
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
