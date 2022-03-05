// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Turret extends SubsystemBase {
  public final VictorSPX turret = new VictorSPX(Constants.turretPort); 

  public Turret() {
    super();
  }

  public void spin(double speed){
    speed /= 100;
    speed = MathUtil.clamp(speed, -1, 1); // check just in case, max and mins input
    turret.set(ControlMode.PercentOutput, speed * (Constants.turretMaxSpeed / 100)); 
  }

  public void stop(){
    turret.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run\
    updateVoltage();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    periodic();
  }

  public double getVoltage(){
    return turret.getMotorOutputVoltage();
  }

  private void updateVoltage(){
    if(!RobotContainer.shuffleBoardInterface.updateVoltage("TurretVoltage", getVoltage()))
      System.out.println("Error setting turret display voltage\n");
  }
}
