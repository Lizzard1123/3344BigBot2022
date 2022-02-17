// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.drive.MecanumDrive.WheelSpeeds;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class myShuffleBoard extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private static ShuffleboardTab driveTab  = Shuffleboard.getTab("Drive");
  private static ShuffleboardTab constTab  = Shuffleboard.getTab("Constants");
  private static ShuffleboardTab debugTab  = Shuffleboard.getTab( "Debug");

  //misc
  private NetworkTableEntry maxSpeed;
  private NetworkTableEntry turnSpeed;

  public Map<String, tableTrio> constantOverrides;
  //struct (ish) for overrides 
  public class tableTrio{
    public NetworkTableEntry actaulVal;
    public NetworkTableEntry defaultVal;
    public NetworkTableEntry check;
    public tableTrio(NetworkTableEntry aV, NetworkTableEntry dV, NetworkTableEntry c){
      actaulVal = aV;
      defaultVal = dV;
      check = c;
    }
  }

  public myShuffleBoard() {
    //Driver Tab presets
      maxSpeed = driveTab
        .add("Max Speed", Constants.maxSpeed)
        .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 100))
        .getEntry();

      turnSpeed = driveTab
        .add("Turn Speed", Constants.turnSpeed)
        .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 100))
        .getEntry(); 

      //Constants tab presets -> on the shuffleboard
      setUpConstantOverrides("maxSpeed", Constants.maxSpeed);
      setUpConstantOverrides("turtle", Constants.turtle);
      setUpConstantOverrides("rabbit", Constants.rabbit);
      setUpConstantOverrides("normal", Constants.normal);
      setUpConstantOverrides("flywheelMaxSpeed", Constants.flywheelMaxSpeed);
      setUpConstantOverrides("turretMaxspeed", Constants.turretMaxspeed);

      //Debug tab presets

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateAllOverrides(); // check code constants to ones set for tests
    //update the constants with items from suffleboard
    Constants.maxSpeed = maxSpeed.getDouble(Constants.maxSpeed);
    Constants.turnSpeed = turnSpeed.getDouble(Constants.turnSpeed);
    Constants.maxSpeed = updateConstantOverrides("maxSpeed", Constants.maxSpeed);
    Constants.turtle = updateConstantOverrides("turtle", Constants.turtle);
    Constants.rabbit = updateConstantOverrides("rabbit", Constants.rabbit);
    Constants.normal = updateConstantOverrides("normal", Constants.normal);
    Constants.flywheelMaxSpeed = updateConstantOverrides("flywheelMaxSpeed", Constants.flywheelMaxSpeed);
    Constants.turretMaxspeed = updateConstantOverrides("turretMaxspeed", Constants.turretMaxspeed);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  //custom functions

  //sets up and logs the entries into the constantOverrides 
  public void setUpConstantOverrides(String name, double defaultSetVal){
    NetworkTableEntry actualVal = constTab.add(name + "_actual", defaultSetVal).getEntry(); 
    NetworkTableEntry defaultVal = constTab.add(name + "_default", defaultSetVal).getEntry(); 
    NetworkTableEntry compare = constTab.add(name + "_same", true).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    constantOverrides.put(name, new tableTrio(actualVal, defaultVal, compare));
  }

  //for each tabletrio in the overrides update the bool indicator showing that they are different in code
  public void updateAllOverrides(){
    for (Map.Entry<String,tableTrio> entry : constantOverrides.entrySet()){
      if(entry.getValue().actaulVal != entry.getValue().defaultVal){
        entry.getValue().check.setBoolean(false);
      } else {
        entry.getValue().check.setBoolean(true);
      }
    }
  }

  //return the shuffleboard value of constants
  public double updateConstantOverrides(String name, double defaultValue){
    return constantOverrides.get(name).actaulVal.getDouble(defaultValue);
  }

  //show the shuffleboard the meccanum speeds
  public static void updateMeccanum(WheelSpeeds speeds){
    driveTab.add("Meccanum Visualization", speeds)
            .withWidget(BuiltInWidgets.kMecanumDrive);
  }


}
