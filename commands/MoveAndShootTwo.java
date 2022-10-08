// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.RobotContainer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class MoveAndShootTwo extends SequentialCommandGroup {
  /** Creates a new MoveAndShoot. */
  public MoveAndShootTwo() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SpinIntakeAuton(RobotContainer.intake, true, Constants.intakeMaxSpeed),
      new MoveTimed(RobotContainer.drivetrain, false),
      new setFlywheelSpeed(RobotContainer.flywheel, Constants.onCircleSpeed/100),
      new TurnTimed(RobotContainer.drivetrain),
      new SpinIntakeAuton(RobotContainer.intake, true, 0),
      new WaitCommand(2),
      new spinUptakeTimed(RobotContainer.uptake),
      new setFlywheelSpeed(RobotContainer.flywheel, 0)
    );
  }
}
