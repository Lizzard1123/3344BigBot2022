// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ExampleSubsystem;

import java.io.Console;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class moveClimberGimbal extends CommandBase {
  private Climber climber;
  private Boolean reverse;
  private Boolean custom;
  private double speed;

  public moveClimberGimbal(Climber climber, Boolean reverse, boolean custom, double speed) {
    this.climber = climber;
    this.reverse = reverse;
    this.custom = custom;
    this.speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(custom){
      climber.spinGimbal(speed * (reverse?-1:1)); 
    } else {
      climber.spinGimbal(Constants.defaultGimbal/100); 
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.spinGimbal(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
