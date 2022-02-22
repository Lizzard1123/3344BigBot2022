// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Flywheel extends SubsystemBase {
  public final CANSparkMax flywheel = new CANSparkMax(Constants.flywheelPort, MotorType.kBrushless);

  public Flywheel() {
    super();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    displayVoltage();
    displayInput();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    periodic();
  }

  public void spin(double speed){
    speed /= 100;
    speed = MathUtil.clamp(speed, -1, 1); // check just in case, max and mins input
    flywheel.set(speed * (Constants.flywheelMaxSpeed / 100));
  }

  //used by driver to control speed
  public void manualControl(double speed){
    if(Constants.flyhweelAnalog) spin(speed);
  }

  //used by auton and PID to control flywheel speed
  public void automatedControl(double speed){
    if(!Constants.flyhweelAnalog) spin(speed);
  }

  //returns voltage supplied to motor from motorcontroller
  public double getVoltage(){
    return flywheel.get() * 12;
  }
  //displays the flywheel voltage to the shuffleboard
  public void displayVoltage(){
    if(!RobotContainer.shuffleBoardInterface.updateVoltage("FlywheelVoltage", getVoltage()))
      System.out.println("Error setting flywheel display voltage\n");
  }
  //displays the flywheel voltage to the shuffleboard
  public void displayInput(){
    if(!RobotContainer.shuffleBoardInterface.updateInput(RobotContainer.flightController.getThrottle()))
      System.out.println("Error setting flywheel display Input\n");
  }
}
