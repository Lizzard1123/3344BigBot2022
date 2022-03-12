// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class MyShuffleBoard extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private static ShuffleboardTab driveTab  = Shuffleboard.getTab("Drive");
  private static ShuffleboardTab constTab  = Shuffleboard.getTab("Constants");
  private static ShuffleboardTab debugTab  = Shuffleboard.getTab("Debug");
  private static ShuffleboardTab limelightTab  = Shuffleboard.getTab("Limelight");

  //misc
  private NetworkTableEntry maxSpeed;
  private NetworkTableEntry turnSpeed;
  private NetworkTableEntry totalSpeedDisplay;

  //flywheel
  private NetworkTableEntry flywheelManualSpeed;
  private NetworkTableEntry flywheelAnalog;
  private NetworkTableEntry flywheelTemp;
  private NetworkTableEntry flywheelVelocity;
  private NetworkTableEntry flywheelCurrent;

  //color sensor
  private NetworkTableEntry colorDist;
  private NetworkTableEntry colorRed;
  private NetworkTableEntry colorGreen;
  private NetworkTableEntry colorBlue;

  //IO
  private NetworkTableEntry leftLimitDisplay;
  private NetworkTableEntry rightLimitDisplay;

  //Limelight
  private NetworkTableEntry limelightValid;
  private NetworkTableEntry limelightX;
  private NetworkTableEntry limelightY;
  private NetworkTableEntry limelightArea;
  private NetworkTableEntry limelightWidth;
  private NetworkTableEntry limelightHeight;
  private NetworkTableEntry limelightCamController;

  //struct (ish) for overrides 
  public class TableTrio{
    public final NetworkTableEntry actaulVal;
    public final NetworkTableEntry defaultVal;
    public final NetworkTableEntry check;
    public TableTrio(NetworkTableEntry aV, NetworkTableEntry dV, NetworkTableEntry c){
      actaulVal = aV;
      defaultVal = dV;
      check = c;
    }
  }
  //keeps all names to trios of the constant lists
  public final Map<String, TableTrio> constantOverrides = new HashMap<>();
  //keeps all names and graphs to the voltages in debug
  public final Map<String, NetworkTableEntry> debugVoltages = new HashMap<>();


  public MyShuffleBoard() {
    //Driver Tab presets
      maxSpeed = driveTab
        .add("Max Speed", Constants.maxSpeed)
        .withPosition(0, 0)
        .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 100))
        .getEntry();

      turnSpeed = driveTab
        .add("Turn Speed", Constants.turnSpeed)
        .withPosition(0, 1)
        .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 100))
        .getEntry(); 

      //driver camera
      driveTab.add(RobotContainer.limelight.frontCamera)
      .withPosition(2, 0)
      .withSize(5, 4);

      //Constants tab presets -> on the shuffleboard
      //numbers
      setUpConstantOverrides("turtle", Constants.turtle);
      setUpConstantOverrides("rabbit", Constants.rabbit);
      setUpConstantOverrides("normal", Constants.normal);
      setUpConstantOverrides("flywheelMaxSpeed", Constants.flywheelMaxSpeed);
      setUpConstantOverrides("turretMaxSpeed", Constants.turretMaxSpeed);
      setUpConstantOverrides("uptakeMaxSpeed", Constants.uptakeMaxSpeed);
      //flywheel
      ShuffleboardContainer flyWheelContainer = debugTab.getLayout("flywheelLayout", BuiltInLayouts.kList)
      .withSize(2, 4)
      .withPosition(7,0);
      flywheelManualSpeed = flyWheelContainer.add("flywheelManualSpeed", Constants.flywheelManualSpeed)
      .getEntry();
      flywheelAnalog = flyWheelContainer.add("flywheelAnalog", Constants.flywheelAnalog)
      .withWidget(BuiltInWidgets.kToggleButton)
      .getEntry();
      flywheelTemp = flyWheelContainer.add("flywheelTemp", 0)
      .getEntry();
      flywheelVelocity = flyWheelContainer.add("flywheelVelocity", 0)
      .getEntry();
      flywheelCurrent = flyWheelContainer.add("flywheelCurrent", 0)
      .getEntry();
      //Color Sensor
      ShuffleboardContainer colorContainer = debugTab.getLayout("Color Sensor", BuiltInLayouts.kList)
      .withSize(1, 2)
      .withPosition(6,0);
      colorDist = colorContainer.add("Dist", 0)
      .getEntry();
      colorRed = colorContainer.add("Red", 0)
      .getEntry();
      colorGreen = colorContainer.add("Green", 0)
      .getEntry();
      colorBlue = colorContainer.add("Blue", 0)
      .getEntry();
      //IO
      
      leftLimitDisplay = debugTab.add("LeftLimitDisplay", false)
      .withPosition(6, 2)
      .withSize(1, 1)
      .withWidget(BuiltInWidgets.kBooleanBox)
      .getEntry();

      rightLimitDisplay = debugTab.add("RightLimitDisplay", false)
      .withPosition(6, 3)
      .withSize(1, 1)
      .withWidget(BuiltInWidgets.kBooleanBox)
      .getEntry();
      
      //misc
      debugTab
      .add("GyroDisplay", RobotContainer.gyro)
      .withPosition(4, 2)
      .withWidget(BuiltInWidgets.kGyro);

      //Debug tab presets
      initAllVoltages();
      //Driver tab set notes
      setNotes();
      //limelight init
      limelightTab.add("LL", RobotContainer.limelight.limelightFeed)
      .withPosition(0, 0)
      .withSize(7, 4)
      .withProperties(Map.of("Show Crosshair", true, "Show Controls", true));
      limelightValid = limelightTab.add("limelightValid", false)
      .withPosition(8, 0)
      .withSize(1, 1)
      .withWidget(BuiltInWidgets.kBooleanBox)
      .getEntry();
      limelightX = limelightTab.add("limelightX", 0)
      .withPosition(8, 1)
      .withSize(1, 1)
      .getEntry();
      limelightY = limelightTab.add("limelightY", 0)
      .withPosition(8, 2)
      .withSize(1, 1)
      .getEntry();
      limelightArea = limelightTab.add("limelightArea", 0)
      .withPosition(8, 3)
      .withSize(1, 1)
      .getEntry();
      limelightWidth = limelightTab.add("limelightWidth", 0)
      .withPosition(7, 0)
      .withSize(1, 1)
      .getEntry();
      limelightHeight = limelightTab.add("limelightHeight", 0)
      .withPosition(7, 1)
      .withSize(1, 1)
      .getEntry();
      limelightCamController = limelightTab.add("limelightCamController", false)
      .withPosition(7, 2)
      .withSize(1, 1)
      .withWidget(BuiltInWidgets.kToggleButton)
      .getEntry();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateAllOverrides(); // check code constants to ones set for tests
    //update the constants with items from suffleboard
    Constants.maxSpeed = maxSpeed.getDouble(Constants.maxSpeed);
    Constants.turnSpeed = turnSpeed.getDouble(Constants.turnSpeed);
    Constants.turtle = updateConstantOverrides("turtle", Constants.turtle);
    Constants.rabbit = updateConstantOverrides("rabbit", Constants.rabbit);
    Constants.normal = updateConstantOverrides("normal", Constants.normal);
    Constants.flywheelMaxSpeed = updateConstantOverrides("flywheelMaxSpeed", Constants.flywheelMaxSpeed);
    Constants.turretMaxSpeed = updateConstantOverrides("turretMaxSpeed", Constants.turretMaxSpeed);
    Constants.uptakeMaxSpeed = updateConstantOverrides("uptakeMaxSpeed", Constants.uptakeMaxSpeed);
    Constants.flywheelAnalog = flywheelAnalog.getBoolean(Constants.flywheelAnalog);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    periodic();
  }

  public boolean updateAllLimelight(
    boolean valid,
    double xOffset,
    double yOffset,
    double area,
    double width,
    double height
  ){
    return  limelightValid.setBoolean(valid) &&
            limelightX.setDouble(xOffset) &&
            limelightY.setDouble(yOffset) &&
            limelightArea.setDouble(area) &&
            limelightWidth.setDouble(width) &&
            limelightHeight.setDouble(height);
  }

  public boolean getVisionStatus(){
    return limelightCamController.getBoolean(false);
  }

  //sets up and logs the entries into the constantOverrides 
  public void setUpConstantOverrides(String name, double defaultSetVal){
    ShuffleboardContainer container = constTab.getLayout(name, BuiltInLayouts.kList)
    .withSize(1, 2)
    .withPosition(constantOverrides.size(), 0);
    NetworkTableEntry actualVal = container.add(name + "_actual", defaultSetVal)
    .getEntry(); 
    NetworkTableEntry defaultVal = container.add(name + "_default", defaultSetVal)
    .getEntry(); 
    NetworkTableEntry compare = container.add(name + "_same", true).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    TableTrio contents =  new TableTrio(actualVal, defaultVal, compare);
    constantOverrides.put(name, contents);
  }

  //for each tabletrio in the overrides update the bool indicator showing that they are different in code
  public void updateAllOverrides(){
    for (Map.Entry<String,TableTrio> entry : constantOverrides.entrySet()){
      if(entry.getValue().actaulVal.getDouble(1.0) != entry.getValue().defaultVal.getDouble(1.0)){
        entry.getValue().check.setBoolean(false);
        continue;
      }
      entry.getValue().check.setBoolean(true);
    }
  }

  //adds all voltage graphs into debug tab
  public void initAllVoltages(){
    Drivetrain drive = RobotContainer.drivetrain;
    ShuffleboardContainer driveContainer = debugTab.getLayout("DriveTrain", BuiltInLayouts.kList)
    .withSize(4, 3)
    .withPosition(0, 0);
    NetworkTableEntry FLV = driveContainer.add("FLVoltage", drive.getFLVoltage()).withWidget(BuiltInWidgets.kVoltageView).withProperties(Map.of("min", -12, "max", 12)).getEntry();
    NetworkTableEntry FRV = driveContainer.add("FRVoltage", drive.getFRVoltage()).withWidget(BuiltInWidgets.kVoltageView).withProperties(Map.of("min", -12, "max", 12)).getEntry();
    NetworkTableEntry BLV = driveContainer.add("BLVoltage", drive.getBLVoltage()).withWidget(BuiltInWidgets.kVoltageView).withProperties(Map.of("min", -12, "max", 12)).getEntry();
    NetworkTableEntry BRV = driveContainer.add("BRVoltage", drive.getBRVoltage()).withWidget(BuiltInWidgets.kVoltageView).withProperties(Map.of("min", -12, "max", 12)).getEntry();
    ShuffleboardContainer flyTurContainer = debugTab.getLayout("Flywheel&Turret", BuiltInLayouts.kList)
    .withSize(2, 2)
    .withPosition(4, 0);
    Flywheel fly = RobotContainer.flywheel;
    NetworkTableEntry FV = flyTurContainer.add("FlywheelVoltage", fly.getVoltage()).withWidget(BuiltInWidgets.kVoltageView).withProperties(Map.of("min", -12, "max", 12)).getEntry();
    Turret turret = RobotContainer.turret;
    NetworkTableEntry TV = flyTurContainer.add("TurretVoltage", turret.getVoltage()).withWidget(BuiltInWidgets.kVoltageView).withProperties(Map.of("min", -12, "max", 12)).getEntry();
    Uptake uptake = RobotContainer.uptake;
    NetworkTableEntry UV = flyTurContainer.add("UptakeVoltage", uptake.getVoltage()).withWidget(BuiltInWidgets.kVoltageView).withProperties(Map.of("min", -12, "max", 12)).getEntry();
    //add all of them to the map
    debugVoltages.put("FLVoltage", FLV);
    debugVoltages.put("FRVoltage", FRV);
    debugVoltages.put("BLVoltage", BLV);
    debugVoltages.put("BRVoltage", BRV);
    debugVoltages.put("FlywheelVoltage", FV);
    debugVoltages.put("TurretVoltage", TV);
    debugVoltages.put("UptakeVoltage", UV);
    //misc
    totalSpeedDisplay = driveTab.add("totalSpeedDisplay", 0)
    .withPosition(0, 2)
    .withSize(1, 1)
    .getEntry();
  }

  public boolean updateVoltage(String name, double val){
    if(!debugVoltages.containsKey(name)) return false;
    debugVoltages.get(name).setDouble(val);
    return true;
  }

  public boolean updateLimits(boolean leftVal, boolean rightVal){
    //System.out.println(" " + (leftLimitDisplay.setBoolean(leftVal)?"1":"0") + "leftReturn\n");
    //System.out.println(" " + (rightLimitDisplay.setBoolean(rightVal)?"1":"0") + "leftReturn\n");
    leftLimitDisplay.setBoolean(leftVal);
    rightLimitDisplay.setBoolean(rightVal);
    return true;
  }

  public boolean updateFlyWheelInput(double num){
    return flywheelManualSpeed.setDouble(num);
  }
  public boolean updateFlyWheelCurrent(double num){
    return flywheelCurrent.setDouble(num);
  }
  public boolean updateFlyWheelTemp(double num){
    return flywheelTemp.setDouble(num);
  }
  public boolean updateFlyWheelVelocity(double num){
    return flywheelVelocity.setDouble(num);
  }

  public boolean updateColorDist(double num){
    return colorDist.setDouble(num);
  }
  public boolean updateColorRed(double num){
    return colorRed.setDouble(num);
  }
  public boolean updateColorGreen(double num){
    return colorGreen.setDouble(num);
  }
  public boolean updateColorBlue(double num){
    return colorBlue.setDouble(num);
  }

  public boolean updateTotalSpeedDisplay(double num){
    return totalSpeedDisplay.setDouble(num);
  }

  //return the shuffleboard value of constants
  public double updateConstantOverrides(String name, double defaultValue){
    if(!constantOverrides.containsKey(name)) return defaultValue;
    return constantOverrides.get(name).actaulVal.getDouble(defaultValue);
  }

  public void setNotes(){
    ShuffleboardContainer noteContainer = driveTab.getLayout("Notes", BuiltInLayouts.kList)
    .withSize(2, 4)
    .withPosition(7, 0);
    noteContainer.add("0", "FlywheelAnalog & L1 & then Throttle Axis -> FLywheel");
    noteContainer.add("1", "throttle slider -> turret left and");
    noteContainer.add("2", "Button R1 -> uptake up");
    noteContainer.add("3", "Button R2 -> uptake down");
    noteContainer.add("4", "POV up rabbit");
    noteContainer.add("5", "POV down turtle");

  }

  //show the shuffleboard the meccanum speeds
  /*
  //cannot display mecanum obj onto screen bc Victorspx doesnt classify as a motorcontroller default
  public void updateMeccanum(WheelSpeeds speeds){
    MecanumDrive base = new MecanumDrive(speeds);
    driveTab.add("Meccanum Visualization", speeds)
            .withWidget(BuiltInWidgets.kMecanumDrive);
  
  }
*/

}
