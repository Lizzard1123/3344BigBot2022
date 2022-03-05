// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Flywheel extends SubsystemBase {
  //TODO IMPORTANT set flywheel to brushless on the REV HARDWARE CLIENT 
  public final CANSparkMax flywheel = new CANSparkMax(Constants.flywheelPort, MotorType.kBrushless);
  public final RelativeEncoder encoder = flywheel.getEncoder();


  public Flywheel() {
    super();
    //TODO same config process here too
    flywheel.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    displayAll();
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
    //flywheel.setVoltage(speed * 12); //backup if set doest work
  }

  //used by driver to control speed
  public void manualControl(double speed){
    if(Constants.flywheelAnalog) spin(speed);
  }

  //used by auton and PID to control flywheel speed
  public void automatedControl(double speed){
    if(!Constants.flywheelAnalog) spin(speed);
  }

  //returns voltage supplied to motor from motorcontroller
  public double getVoltage(){
    return flywheel.get() * 12;
  }
  
  public double getCurrent(){
    return flywheel.getOutputCurrent();
  }
  
  public double getTemp(){
    return flywheel.getMotorTemperature();
  }
  
  public double getVelocity(){
    return encoder.getVelocity();
  }
  //displays the flywheel info to the shuffleboard
  public void displayAll(){
    if(!RobotContainer.shuffleBoardInterface.updateVoltage("FlywheelVoltage", getVoltage()))
      System.out.println("Error setting flywheel display voltage\n");
    if(!RobotContainer.shuffleBoardInterface.updateFlyWheelTemp(getTemp()))
      System.out.println("Error setting flywheel display temp\n");
    if(!RobotContainer.shuffleBoardInterface.updateFlyWheelCurrent(getCurrent()))
      System.out.println("Error setting flywheel display Current\n");
    if(!RobotContainer.shuffleBoardInterface.updateFlyWheelVelocity(getVelocity()))
      System.out.println("Error setting flywheel display Velocity\n");
  }
}
