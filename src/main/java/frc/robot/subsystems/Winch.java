// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
//import frc.robot.RobotContainer;

public class Winch extends SubsystemBase {
  public final CANSparkMax winch = new CANSparkMax(Constants.winchPort, MotorType.kBrushless);

  public Winch() {
    super();
    winch.setIdleMode(IdleMode.kBrake);
    winch.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //RobotContainer.shuffleBoardInterface.updatewinchVoltages(getwinchVoltage());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    periodic();
  }

  public void winchSpin(double speed){
    speed = MathUtil.clamp(speed, -1, 1); // check just in case, max and mins input
    winch.set(speed * (Constants.winchMaxSpeed / 100)); 
  }

  public double getwinchVoltage(){
    return winch.get() * 12;
  }

  public void winchStop(){
    winchSpin(0);
  }
}
