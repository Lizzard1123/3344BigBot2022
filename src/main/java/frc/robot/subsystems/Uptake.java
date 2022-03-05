// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Uptake extends SubsystemBase {
  public final VictorSPX uptake = new VictorSPX(Constants.uptakePort); 
  public final ColorSensorV3 colorSensor = new ColorSensorV3(Constants.colorPort);

  public Uptake() {
    super();
  }

  public void spin(double speed){
    speed /= 100;
    speed = MathUtil.clamp(speed, -1, 1); // check just in case, max and mins input
    uptake.set(ControlMode.PercentOutput, speed * (Constants.uptakeMaxSpeed / 100)); 
  }

  public void stop(){
    uptake.set(ControlMode.PercentOutput, 0);
  }

  public Color getColor(){
    return colorSensor.getColor();
  }

  public double getRed(){
    return colorSensor.getRed();
  }

  public double getGreen(){
    return colorSensor.getGreen();
  }

  public double getBlue(){
    return colorSensor.getBlue();
  }

  public double getDist(){
    return colorSensor.getProximity(); //0-2047 with large being close
  }

  //displays the Color Sensor info to the shuffleboard
  public void displayAll(){
    if(!RobotContainer.shuffleBoardInterface.updateColorDist(getDist()))
      System.out.println("Error setting Color Sensor display Dist\n");
    if(!RobotContainer.shuffleBoardInterface.updateColorRed(getRed()))
      System.out.println("Error setting Color Sensor display red\n");
    if(!RobotContainer.shuffleBoardInterface.updateColorGreen(getGreen()))
      System.out.println("Error setting Color Sensor display green\n");
    if(!RobotContainer.shuffleBoardInterface.updateColorBlue(getBlue()))
      System.out.println("Error setting Color Sensor display blue\n");
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
}
