// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Climber extends SubsystemBase {
  CANSparkMax climber = new CANSparkMax(Constants.climberPort, MotorType.kBrushless);

  public Climber() {
    super();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    RobotContainer.shuffleBoardInterface.updateClimberVoltage(getVoltage());
  }
  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    periodic();
  }

  public double getVoltage(){
    return climber.get() * 12;
  }

  public double getCurrent(){
    //return climberGimbal.getOutputCurrent();
    return 0;
  }
  
  public double getTemp(){
    //return climberGimbal.getMotorTemperature();
    return 0;
  }

  public void spin(double speed){
    speed = MathUtil.clamp(speed, -1, 1); // check just in case, max and mins input
    climber.set(speed * (Constants.climberMaxSpeed / 100)); 
  }

  public void spinGimbal(double speed){
    speed = MathUtil.clamp(speed, -1, 1); // check just in case, max and mins input
    //climberGimbal.set(speed); 
  }

  public void stop(){
    spin(0);
  }
}
