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

public class Climber extends SubsystemBase {
  VictorSPX climber = new VictorSPX(Constants.climberPort);
  CANSparkMax climberGimbal = new CANSparkMax(11, MotorType.kBrushless);

  public Climber() {
    super();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public double getCurrent(){
    return climberGimbal.getOutputCurrent();
  }
  
  public double getTemp(){
    return climberGimbal.getMotorTemperature();
  }

  public void spin(double speed){
    speed = MathUtil.clamp(speed, -1, 1); // check just in case, max and mins input
    climber.set(ControlMode.PercentOutput, speed * (Constants.climberMaxSpeed / 100)); 
  }

  public void spinGimbal(double speed){
    speed = MathUtil.clamp(speed, -1, 1); // check just in case, max and mins input
    climberGimbal.set(speed); 
  }

  public void stop(){
    spin(0);
  }
}
