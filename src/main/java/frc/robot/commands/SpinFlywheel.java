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
  public PIDController pid;
  private double setInterpolatorPVal = 0.0;

  public SpinFlywheel(Flywheel flywheel) {
    super();
    this.flywheel = flywheel;
    pid = new PIDController(0,0,0);
    addRequirements(flywheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Constants.flywheelAnalog){ //manual
      flywheel.spin(RobotContainer.flightController.getThrottle());
    } else { //pid
      flywheel.spin(pid.calculate(flywheel.getVelocity(), interpolateVelocity(RobotContainer.limelight.getWidth())));
      if(withinTolerance()){
        pid.reset();
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

  private double interpolateVelocity(double width){
    return Math.pow(width, 2) * setInterpolatorPVal;
  }

  public double getInterpolatedVelocity(){
    return interpolateVelocity(RobotContainer.limelight.getWidth());
  }

  public double getError(){
    return pid.getPositionError();
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
