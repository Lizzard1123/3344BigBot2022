// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Flywheel;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class SpinFlywheel extends CommandBase {
  private Flywheel flywheel;
  private PIDController pid;
  private double setInterpolatorPVal = 0.0;
  private boolean update = true;
  private boolean isDone = false;

  public SpinFlywheel(Flywheel flywheel, boolean update, PIDController pid) {
    super();
    this.flywheel = flywheel;
    this.update = update;
    this.pid = pid;
    pid.reset();
    addRequirements(flywheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pid.calculate(0);
    pid.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(update){
      pid.setSetpoint(interpolateVelocity(RobotContainer.limelight.getWidth()));
    }
    flywheel.spin(pid.calculate(flywheel.getVelocity()));
    if(withinTolerance()){
      //pid.reset();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.flywheelPID.reset();
    flywheel.spin(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }

  private double interpolateVelocity(double width){
    return Math.pow(320/width, 2) * setInterpolatorPVal;
  }

  public double getInterpolatedVelocity(){
    return interpolateVelocity(RobotContainer.limelight.getWidth());
  }

  public double getError(){
    return pid.getPositionError();
  }

  public void setGoal(double speed){
    pid.setSetpoint(speed);
  }

  public void isDone(boolean done){
    this.isDone = done;
  }

  public boolean withinTolerance(){
    return pid.atSetpoint();
  }

  public void setTolerance(double tol){
    pid.setTolerance(tol);
  }

  public void setInterpolatorVal(double val){
    setInterpolatorPVal = val;
  }
}
