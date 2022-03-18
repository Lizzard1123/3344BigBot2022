// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
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
    //manual override doesnt need indexer 
    if(!Constants.manualOverride){
      //check if ball is in front of color sensor
      Constants.holdingBall = (uptake.getDist() >= Constants.minDist);
      if(Constants.holdingBall)
        uptake.stop();
      Constants.holdingBlueBall = (uptake.getBlue() >= uptake.getRed());
     //check color of ball to determine its fate
      if(Constants.holdingBall)
        //sort color
        if((Constants.isBlue && Constants.holdingBlueBall )|| //is blue ball on blue team
          (!Constants.isBlue && !Constants.holdingBlueBall)){ //is red ball on red team
          new Shoot(uptake, true).schedule();;
        } else { //wrong color
          new Shoot(uptake, false).schedule();;
        }
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
