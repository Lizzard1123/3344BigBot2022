// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive.WheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Drivetrain extends SubsystemBase {
  public final VictorSPX frontRightDrive = new VictorSPX(Constants.FRPort); 
  public final VictorSPX frontLeftDrive = new VictorSPX(Constants.FLPort); 
  public final VictorSPX backLeftDrive = new VictorSPX(Constants.BLPort); 
  public final VictorSPX backRightDrive = new VictorSPX(Constants.BRPort); 
  public WheelSpeeds speeds;
  
  public Drivetrain() {
    super();
    frontLeftDrive.setInverted(true);
    backLeftDrive.setInverted(true);
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    drive(50, 50, 0, 0);
  }

  public void drive(double x, double y, double z, double orientation){
    //just in case, we have a joystick deadzone and make sure its within -1, 1
    x = MathUtil.applyDeadband(MathUtil.clamp(x, -1.0, 1.0), Constants.k_deadband);
    y = MathUtil.applyDeadband(MathUtil.clamp(y, -1.0, 1.0), Constants.k_deadband);
    z = MathUtil.applyDeadband(MathUtil.clamp(z, -1.0, 1.0), Constants.k_deadband);

    //meccanum drive class did not work with VictorSPX controllers 
    //speeds is an object with the raw motor %s for each motor
    speeds = MecanumDrive.driveCartesianIK(y, x, z * (Constants.turnSpeed / 100), orientation);
    
    //if(Constants.displayMeccanums) RobotContainer.shuffleBoardInterface.updateMeccanum(speeds);

    if(!RobotBase.isReal())
      return;
    
    frontRightDrive.set(ControlMode.PercentOutput, speeds.frontRight * (Constants.driveSet / 100));
    frontLeftDrive.set(ControlMode.PercentOutput, speeds.frontLeft * (Constants.driveSet / 100));
    backLeftDrive.set(ControlMode.PercentOutput, speeds.rearLeft * (Constants.driveSet / 100));
    backRightDrive.set(ControlMode.PercentOutput, speeds.rearRight * (Constants.driveSet / 100));
  }
  //return voltages from the motors
  public double getFLVoltage(){
    return frontLeftDrive.getMotorOutputVoltage();
  }
  public double getFRVoltage(){
    return frontRightDrive.getMotorOutputVoltage();
  }
  public double getBLVoltage(){
    return backLeftDrive.getMotorOutputVoltage();
  }
  public double getBRVoltage(){
    return backRightDrive.getMotorOutputVoltage();
  }

}
