// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  public final VictorSPX frontRightDrive = new VictorSPX(1); 
  public final VictorSPX frontLeftDrive = new VictorSPX(2); 
  public final VictorSPX backLeftDrive = new VictorSPX(3); 
  public final VictorSPX backRightDrive = new VictorSPX(4); 

  
  public Drivetrain() {
    super();
    frontRightDrive.setInverted(true);
    backRightDrive.setInverted(true);
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void drive(double x, double y, double z, double orientation){
    //just in case
    x = MathUtil.clamp(x, -1.0, 1.0);
    y = MathUtil.clamp(y, -1.0, 1.0);
    z = MathUtil.clamp(z, -1.0, 1.0);

    var speeds = MecanumDrive.driveCartesianIK(y, x, z, orientation);
    frontRightDrive.set(ControlMode.PercentOutput, speeds.frontRight * (Constants.driveLimit / 100));
    frontLeftDrive.set(ControlMode.PercentOutput, speeds.frontLeft * (Constants.driveLimit / 100));
    backLeftDrive.set(ControlMode.PercentOutput, speeds.rearLeft * (Constants.driveLimit / 100));
    backRightDrive.set(ControlMode.PercentOutput, speeds.rearRight * (Constants.driveLimit / 100));
  }

}
