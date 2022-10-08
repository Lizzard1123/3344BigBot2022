// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class LimelightClass extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private final NetworkTable limelightNetworkTable = NetworkTableInstance.getDefault().getTable("limelight");
  public HttpCamera limelightFeed;
  public UsbCamera frontCamera;

  public LimelightClass() {
    limelightFeed = new HttpCamera("limelight", "http://10.33.44.11:5800//stream.mjpg");
    frontCamera = CameraServer.startAutomaticCapture();
    //frontCamera.setResolution(160, 120);
    //frontCamera.setFPS(30);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    RobotContainer.shuffleBoardInterface.updateAllLimelight(hasSight(), getXOffset(), getYOffset(), getArea(), getWidth(), getHeight());
    if(RobotContainer.shuffleBoardInterface.getVisionStatus()){
      enableVisionCam();
    } else {
      enableDriverCam();
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    periodic();
  }

  public double getLimelightVal(String name){
    return limelightNetworkTable.getEntry(name).getDouble(0.0);
  }

  public boolean hasSight(){
    return (int)getLimelightVal("tv") == (int)1;
  }

  public double getXOffset(){
    return getLimelightVal("tx"); //-29.8 to 29.8 max
  }
  
  public double getYOffset(){
    return getLimelightVal("ty"); //-24.85 to 24.85 max
  }
  
  public double getArea(){
    return getLimelightVal("ta"); //0 to 100 %
  }

  public double getWidth(){
    return getLimelightVal("tlong"); //pixels of longest side 
  }

  public double getHeight(){
    return getLimelightVal("tshort"); //pixels of longest side 
  }

  //valid keys - https://docs.limelightvision.io/en/latest/networktables_api.html
  public void enableDriverCam(){
    limelightNetworkTable.getEntry("camMode").setDouble(1.0);
    limelightNetworkTable.getEntry("ledMode").setDouble(1.0);
  }

  public void enableVisionCam(){
    limelightNetworkTable.getEntry("camMode").setDouble(0.0);
    limelightNetworkTable.getEntry("ledMode").setDouble(3.0);
  }
}