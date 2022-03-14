// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Uptake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class Index extends CommandBase {
  private Uptake uptake;

  public Index(Uptake uptake) {
    super();
    this.uptake = uptake;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(uptake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() { //this is kinda a mess
    //check if ball is in front of color sensor
    if(uptake.getDist() >= Constants.minDist)
      Constants.holdingBall = true;
    //check color of ball to determine its fate
    if(Constants.holdingBall)
      //sort color
      if(Constants.isBlue && uptake.getBlue() >= Constants.minBlue || //is blue ball on blue team
        !Constants.isBlue && uptake.getRed() >= Constants.minRed){ //is red ball on red team
        new Shoot(uptake, true);
      } else { //wrong color
        new Shoot(uptake, false);
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
