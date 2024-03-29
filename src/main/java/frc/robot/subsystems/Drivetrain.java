// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.math.MathUtil;
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
  //frontLeft, rearLeft, frontRight, rearRight
  //public MecanumDrive base = new MecanumDrive(frontLeftDrive, backLeftDrive, frontRightDrive, backRightDrive);
  
  public Drivetrain() {
    super();
    frontRightDrive.setInverted(true);
    backRightDrive.setInverted(true);
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    displayVoltages();
  }
  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    //drive(50, 50, 0, 0);
  }

  public void updateNormalSpeed(){
    if(RobotContainer.driverController.getPOV() == 0){
      Constants.driveSet = Constants.rabbit;
    } else if(RobotContainer.driverController.getPOV() == 180){
      Constants.driveSet = Constants.turtle;
    } else if(RobotContainer.driverController.getPOV() == 90){
      Constants.driveSet = Constants.normal;
    }
    RobotContainer.shuffleBoardInterface.updateTotalSpeedDisplay(Constants.driveSet * (Constants.maxSpeed/100));
  }

  public void drive(double x, double y, double z, double orientation){
    updateNormalSpeed();
    //just in case, we have a joystick deadzone and make sure its within -1, 1
    x = MathUtil.applyDeadband(MathUtil.clamp(x, -1.0, 1.0), Constants.k_deadband);
    y = MathUtil.applyDeadband(MathUtil.clamp(y, -1.0, 1.0), Constants.k_deadband);
    z = MathUtil.applyDeadband(MathUtil.clamp(z, -1.0, 1.0), Constants.k_deadband);

    //meccanum drive class did not work with VictorSPX controllers 
    //speeds is an object with the raw motor %s for each motor
    WheelSpeeds speeds = MecanumDrive.driveCartesianIK(y, x, z * (Constants.turnSpeed / 100), orientation);
    
    frontRightDrive.set(ControlMode.PercentOutput, speeds.frontRight * (Constants.driveSet / 100) * (Constants.maxSpeed / 100));
    frontLeftDrive.set(ControlMode.PercentOutput, speeds.frontLeft * (Constants.driveSet / 100) * (Constants.maxSpeed / 100));
    backLeftDrive.set(ControlMode.PercentOutput, speeds.rearLeft * (Constants.driveSet / 100) * (Constants.maxSpeed / 100));
    backRightDrive.set(ControlMode.PercentOutput, speeds.rearRight * (Constants.driveSet / 100) * (Constants.maxSpeed / 100));
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

  //displays motor voltages in the debug tab in shuffleboard
  public void displayVoltages(){
    HarrisonShuffleBoard sb = RobotContainer.shuffleBoardInterface;
    boolean sucess =  
      sb.updateVoltage("FLVoltage", getFLVoltage()) &&
      sb.updateVoltage("FRVoltage", getFRVoltage()) &&
      sb.updateVoltage("BLVoltage", getBLVoltage()) &&
      sb.updateVoltage("BRVoltage", getBRVoltage());

    if(!sucess){
      System.out.print("Failed to output voltages for debug\n");
    }
  }

  public void spinLeft(double speed){
    speed = MathUtil.clamp(speed, -1.0, 1.0);
    frontLeftDrive.set(ControlMode.PercentOutput, speed);
    backLeftDrive.set(ControlMode.PercentOutput, speed);
  }

  public void spinRight(double speed){
    speed = MathUtil.clamp(speed, -1.0, 1.0);
    frontRightDrive.set(ControlMode.PercentOutput, speed);
    backRightDrive.set(ControlMode.PercentOutput, speed);
  }

  public void stopAll(){
    frontRightDrive.set(ControlMode.PercentOutput, 0);
    frontLeftDrive.set(ControlMode.PercentOutput, 0);
    backLeftDrive.set(ControlMode.PercentOutput, 0);
    backRightDrive.set(ControlMode.PercentOutput, 0);
  }

}
