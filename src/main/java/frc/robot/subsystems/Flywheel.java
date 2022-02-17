// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Flywheel extends SubsystemBase {
  public final VictorSPX flywheel = new VictorSPX(Constants.flywheelPort);

  public Flywheel() {
    super();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    displayVoltage();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    periodic();
  }

  //returns voltage supplied to motor from motorcontroller
  public double getVoltage(){
    return flywheel.getMotorOutputVoltage();
  }
  //displays the flywheel voltage to the shuffleboard
  public void displayVoltage(){
    if(!RobotContainer.shuffleBoardInterface.updateVoltage("FlywheelVoltage", getVoltage()))
      System.out.println("Error setting flywheel display voltage\n");
  }
}
