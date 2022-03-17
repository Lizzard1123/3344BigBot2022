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
import frc.robot.RobotContainer;

public class Intake extends SubsystemBase {
  public final CANSparkMax intake = new CANSparkMax(Constants.intakePort, MotorType.kBrushless);
  //public final VictorSPX arm = new VictorSPX(Constants.armPort);

  public Intake() {
    super();
    intake.setIdleMode(IdleMode.kCoast);
    intake.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    RobotContainer.shuffleBoardInterface.updateIntakeVoltages(getIntakeVoltage(), getArmVoltage());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    periodic();
  }

  public void intakeSpin(double speed){
    speed = MathUtil.clamp(speed, -1, 1); // check just in case, max and mins input
    intake.set(speed * (Constants.intakeMaxSpeed / 100)); 
  }

  public void armSpin(double speed){
    speed = MathUtil.clamp(speed, -1, 1); // check just in case, max and mins input
    //arm.set(ControlMode.PercentOutput, speed * (Constants.armMaxSpeed / 100)); 
  }

  public void armVoltage(double volt){
    volt = MathUtil.clamp(volt, -12, 12); // check just in case, max and mins input
    //arm.set(ControlMode.PercentOutput, volt / 12); 
  }

  public double getIntakeVoltage(){
    return intake.get() * 12;
  }

  public double getArmVoltage(){
    //return arm.getMotorOutputVoltage();
    return 0.0;
  }

  public void intakeStop(){
    intakeSpin(0);
  }

  public void armStop(){
    armSpin(0);
  }
}
