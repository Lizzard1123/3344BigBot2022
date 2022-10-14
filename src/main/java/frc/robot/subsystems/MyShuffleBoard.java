// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.math.MathUtil;
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
  private static ShuffleboardTab powerTab  = Shuffleboard.getTab("powerTab");
  private static ShuffleboardTab autonTab  = Shuffleboard.getTab("auton");
  private static ShuffleboardTab camerasTab  = Shuffleboard.getTab("camerasTab");

  //misc
  private NetworkTableEntry maxSpeed;
  private NetworkTableEntry turnSpeed;
  private NetworkTableEntry totalSpeedDisplay;
  private NetworkTableEntry scanWidth;
  private NetworkTableEntry colorPicker;
  private NetworkTableEntry colorDisplay;
  private NetworkTableEntry manualOverride;

  //flywheel
  private NetworkTableEntry flywheelManualSpeed;
  private NetworkTableEntry flywheelAnalog;
  private NetworkTableEntry flywheelTemp;
  private NetworkTableEntry flywheelVelocity;
  private NetworkTableEntry flywheelCurrent;
  private NetworkTableEntry backFlywheelTemp;
  private NetworkTableEntry backFlywheelVelocity;
  private NetworkTableEntry backFlywheelCurrent;


  //color sensor
  private NetworkTableEntry colorDist;
  private NetworkTableEntry colorRed;
  private NetworkTableEntry colorGreen;
  private NetworkTableEntry colorBlue;
  private NetworkTableEntry holdingBlueBall;
  private NetworkTableEntry colorMinDist;
  private NetworkTableEntry holdingBall;
  private NetworkTableEntry shootTime;
  private NetworkTableEntry shootWidth;


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
  //limelight pid
  private NetworkTableEntry turretTolerance;
  private NetworkTableEntry turretError;
  private NetworkTableEntry readyToShoot;
  private NetworkTableEntry backWheelMultipler;
  //setpoints
  private NetworkTableEntry insideCircleSpeed;
  private NetworkTableEntry onCircleSpeed;
  private NetworkTableEntry fullCourtSpeed;
  private NetworkTableEntry dumpBallSpeed;

  //auton vars
  private NetworkTableEntry goForAuton;
  private NetworkTableEntry autonForwardSpeed;
  private NetworkTableEntry autonForwardTime;
  private NetworkTableEntry autonTurnSpeed;
  private NetworkTableEntry autonTurnTime;


  //intake ones
  private NetworkTableEntry intakeVoltage;
  private NetworkTableEntry climberVoltage;
  

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
    initDriverTab();
    //constants
    initConstantsTab();
    //debug tab (full)
    initDebugTab();
    //limelight
    initLimelight();
    //power and commands crashes program through duplicating pdp element
    initPower();
    //auton
    initAuton();
    //init cameras 
    initCameras();
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
    Constants.intakeMaxSpeed = updateConstantOverrides("intakeMaxSpeed", Constants.intakeMaxSpeed);
    Constants.climberMaxSpeed = updateConstantOverrides("climberMaxSpeed", Constants.climberMaxSpeed);
    Constants.defaultGimbal = updateConstantOverrides("defaultGimbal", Constants.defaultGimbal);
    Constants.flywheelAnalog = flywheelAnalog.getBoolean(Constants.flywheelAnalog);
    Constants.scanWidth = scanWidth.getDouble(Constants.scanWidth);
    Constants.minDist = colorMinDist.getDouble(Constants.minDist);
    Constants.shootTime = shootTime.getDouble(Constants.shootTime);
    Constants.shootWidth = shootWidth.getDouble(Constants.shootWidth);
    Constants.manualOverride = manualOverride.getBoolean(Constants.manualOverride);
    Constants.goForAuton = goForAuton.getBoolean(Constants.goForAuton);
    Constants.wheelMultipler = backWheelMultipler.getDouble(Constants.wheelMultipler);
    Constants.insideCircleSpeed = insideCircleSpeed.getDouble(Constants.insideCircleSpeed);
    Constants.onCircleSpeed = onCircleSpeed.getDouble(Constants.onCircleSpeed);
    Constants.fullCourtSpeed = fullCourtSpeed.getDouble(Constants.fullCourtSpeed);
    Constants.dumpBallSpeed = dumpBallSpeed.getDouble(Constants.dumpBallSpeed);
    //updating turret command
    updateTurretCommand();
    //ready to shoot
    Constants.readyToShoot = RobotContainer.turretHandler.withinTolerance() && RobotContainer.limelight.hasSight();
    readyToShoot.setBoolean(Constants.readyToShoot);
    //auton updates
    Constants.autonForwardSpeed = MathUtil.clamp(autonForwardSpeed.getDouble(Constants.autonForwardSpeed), -Constants.maxSpeed, Constants.maxSpeed);
    Constants.autonForwardTime = MathUtil.clamp(autonForwardTime.getDouble(Constants.autonForwardTime), 0, 15);
    Constants.autonTurnSpeed = MathUtil.clamp(autonTurnSpeed.getDouble(Constants.autonTurnSpeed), -Constants.maxSpeed, Constants.maxSpeed);
    Constants.autonTurnTime = MathUtil.clamp(autonTurnTime.getDouble(Constants.autonTurnTime), 0, 15);
    //color picker
    handleColor();
    //indexer
    holdingBall.setBoolean(Constants.holdingBall);
    holdingBlueBall.setBoolean(Constants.holdingBlueBall);
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

  public double getTurretTolerance(){
    return turretTolerance.getDouble(4.0);
  }

  public boolean getVisionStatus(){
    return limelightCamController.getBoolean(false);
  }

  //sets up and logs the entries into the constantOverrides 
  public void setUpConstantOverrides(String name, double defaultSetVal){
    ShuffleboardContainer container = constTab.getLayout(name, BuiltInLayouts.kList)
    .withSize(1, 3)
    .withPosition(constantOverrides.size(), 0);
    NetworkTableEntry actualVal = container.add("A_" + name, defaultSetVal)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0, "max", 100, "Block increment", 1))
    .getEntry(); 
    NetworkTableEntry defaultVal = container.add("D_" + name, defaultSetVal)
    .withWidget(BuiltInWidgets.kNumberBar)
    .withProperties(Map.of("min", defaultSetVal, "max", defaultSetVal))
    .getEntry(); 
    NetworkTableEntry compare = container.add("S_" + name, true).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
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
    .withSize(2, 3)
    .withPosition(4, 0);
    Flywheel fly = RobotContainer.flywheel;
    NetworkTableEntry FV = flyTurContainer.add("FlywheelVoltage", fly.getVoltage()).withWidget(BuiltInWidgets.kVoltageView).withProperties(Map.of("min", -12, "max", 12)).getEntry();
    NetworkTableEntry BFV = flyTurContainer.add("BackFlywheelVoltage", fly.getBackVoltage()).withWidget(BuiltInWidgets.kVoltageView).withProperties(Map.of("min", -12, "max", 12)).getEntry();
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
    debugVoltages.put("BackFlywheelVoltage", BFV);
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
  //back flywheel
  public boolean updateBackFlyWheelCurrent(double num){
    return backFlywheelCurrent.setDouble(num);
  }
  public boolean updateBackFlyWheelTemp(double num){
    return backFlywheelTemp.setDouble(num);
  }
  public boolean updateBackFlyWheelVelocity(double num){
    return backFlywheelVelocity.setDouble(num);
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

  public double getMultipler(){
    return backWheelMultipler.getDouble(Constants.wheelMultipler);
  }

  public void updateTurretCommand(){
    RobotContainer.turretHandler.setTolerance(turretTolerance.getDouble(4.0));
    turretError.setDouble(RobotContainer.turretHandler.getError());
  }

  //intake voltages
  public void updateIntakeVoltages(double intake){
    intakeVoltage.setDouble(intake);
  }

  public void updateClimberVoltage(double voltage){
    climberVoltage.setDouble(voltage);
  }

  //return the shuffleboard value of constants
  public double updateConstantOverrides(String name, double defaultValue){
    if(!constantOverrides.containsKey(name)) return defaultValue;
    return MathUtil.clamp(constantOverrides.get(name).actaulVal.getDouble(defaultValue), 0, 100);
  }

  //handler for color choosing
  public void handleColor(){
    Constants.isBlue = colorPicker.getBoolean(false);
    colorDisplay.setBoolean(Constants.isBlue);
  }

  public void setNotes(){
    ShuffleboardContainer noteContainer = driveTab.getLayout("Notes", BuiltInLayouts.kList)
    .withSize(2, 4)
    .withPosition(7, 0);
    noteContainer.add("0", "Y climber up");
    noteContainer.add("1", "A climber down");
    noteContainer.add("2", "X gimbal close");
    noteContainer.add("3", "B gimbal open");
    noteContainer.add("4", "good luck");
    noteContainer.add("5", "youve got this");

  }

  private void initDriverTab(){
    maxSpeed = driveTab
    .add("Max Speed", Constants.maxSpeed)
    .withPosition(0, 0)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0, "max", 100, "Block increment", 1))
    .getEntry();

    turnSpeed = driveTab
    .add("Turn Speed", Constants.turnSpeed)
    .withPosition(0, 1)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0, "max", 100,  "Block increment", 1))
    .getEntry(); 

    //driver camera
    driveTab.add(RobotContainer.limelight.frontCamera)
    .withPosition(2, 0)
    .withSize(5, 4);

    //driveTab.add("Piston Status", piston.checkStatus()).withPosition(3,0).withSize(5,4);
    driveTab.add("Piston Status", RobotContainer.piston.checkStatus())
    .withPosition(3, 0)
    .withSize(2, 2);


    //Driver tab set notes
    setNotes();

    //auton chooser
    //driveTab.add(RobotContainer.autoChooser)
    //.withWidget(BuiltInWidgets.kComboBoxChooser)
    //.withPosition(0, 3)
    //.withSize(2, 1);

    manualOverride = driveTab.add("manualOverride", Constants.manualOverride)
    .withPosition(1, 2)
    .withSize(1, 1)
    .withWidget(BuiltInWidgets.kToggleButton)
    .getEntry();
  }

  private void initConstantsTab(){
    //Constants tab presets -> on the shuffleboard
    //numbers
    setUpConstantOverrides("turtle", Constants.turtle);
    setUpConstantOverrides("rabbit", Constants.rabbit);
    setUpConstantOverrides("normal", Constants.normal);
    setUpConstantOverrides("flywheelMaxSpeed", Constants.flywheelMaxSpeed);
    setUpConstantOverrides("turretMaxSpeed", Constants.turretMaxSpeed);
    setUpConstantOverrides("uptakeMaxSpeed", Constants.uptakeMaxSpeed);
    setUpConstantOverrides("intakeMaxSpeed", Constants.intakeMaxSpeed);
    setUpConstantOverrides("climberMaxSpeed", Constants.climberMaxSpeed); //defaultGimbal
    setUpConstantOverrides("defaultGimbal", Constants.defaultGimbal);
    //misc
    scanWidth = constTab.add("scanWidth", Constants.scanWidth)
    .withPosition(0, 3)
    .withSize(1, 1)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0, "max", 320, "Block increment", 1))
    .getEntry();

    holdingBlueBall = constTab.add("holdingBlueBall", Constants.holdingBlueBall)
    .withPosition(6, 3)
    .withSize(1, 1)
    .withWidget(BuiltInWidgets.kBooleanBox)
    .withProperties(Map.of("Color when false", "red", "Color when true", "blue"))
    .getEntry();

    colorMinDist = constTab.add("colorMinDist", Constants.minDist)
    .withPosition(8, 3)
    .withSize(1, 1)
    .getEntry();

    holdingBall = constTab.add("holdingBall", Constants.holdingBall)
    .withPosition(5, 3)
    .withSize(1, 1)
    .withWidget(BuiltInWidgets.kBooleanBox)
    .getEntry();

    shootTime = constTab.add("shootTime", Constants.shootTime)
    .withPosition(4, 3)
    .withSize(1, 1)
    .getEntry(); //getEjectSetGoal

    shootWidth = constTab.add("shootWidth", Constants.shootWidth)
    .withPosition(3, 3)
    .withSize(1, 1)
    .getEntry(); //getEjectSetGoal

    
    //color picker
    colorPicker = constTab.add("Color", Constants.isBlue)
    .withPosition(1, 3)
    .withSize(1, 1)
    .withWidget(BuiltInWidgets.kToggleButton)
    .getEntry();

    colorDisplay = constTab.add("CurrentColor", Constants.isBlue)
    .withPosition(2, 3)
    .withSize(1, 1)
    .withWidget(BuiltInWidgets.kBooleanBox)
    .withProperties(Map.of("Color when false", "red", "Color when true", "blue"))
    .getEntry();
  }

  private void initDebugTab(){
    //flywheel
    ShuffleboardContainer flyWheelContainer = debugTab.getLayout("flywheelLayout", BuiltInLayouts.kList)
    .withSize(2, 4)
    .withPosition(7,0);
    flywheelAnalog = flyWheelContainer.add("flywheelAnalog", Constants.flywheelAnalog)
    .withWidget(BuiltInWidgets.kToggleButton)
    .getEntry();
    flywheelTemp = flyWheelContainer.add("flywheelTemp", 0)
    .getEntry();
    flywheelVelocity = flyWheelContainer.add("flywheelVelocity", 0)
    .getEntry();
    flywheelCurrent = flyWheelContainer.add ("flywheelCurrent", 0)
    .getEntry();
    backFlywheelTemp = flyWheelContainer.add("backFlywheelTemp", 0)
    .getEntry();
    backFlywheelVelocity = flyWheelContainer.add("backFlywheelVelocity", 0)
    .getEntry();
    backFlywheelCurrent = flyWheelContainer.add ("backFlywheelCurrent", 0)
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
    
    //intake and arm
    intakeVoltage = debugTab.add("intakeVoltage", 0)
    .withWidget(BuiltInWidgets.kVoltageView)
    .withProperties(Map.of("min", -12, "max", 12))
    .withPosition(0, 3)
    .withSize(2, 1)
    .getEntry();

    climberVoltage = debugTab.add("climberVoltage", 0)
    .withWidget(BuiltInWidgets.kVoltageView)
    .withProperties(Map.of("min", -12, "max", 12))
    .withPosition(2, 3)
    .withSize(2, 1)
    .getEntry();

    //misc
    //debugTab
    //.add("GyroDisplay", RobotContainer.gyro)
    //.withPosition(4, 2)
    //.withWidget(BuiltInWidgets.kGyro);

    //Debug tab presets
    initAllVoltages();
  }

  private void initLimelight(){
    //limelight init
    limelightTab.add("LL", RobotContainer.limelight.limelightFeed)
    .withPosition(0, 0)
    .withSize(4, 4)
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
    limelightCamController = limelightTab.add("ll_CamController", true)
    .withPosition(7, 2)
    .withSize(1, 1)
    .withWidget(BuiltInWidgets.kToggleButton)
    .getEntry();

    //PID
    limelightTab.add("turretVisionPID", RobotContainer.turretHandler.pid)
    .withPosition(5, 0)
    .withSize(2, 2)
    .withWidget(BuiltInWidgets.kPIDController);

    turretTolerance = limelightTab.add("turretTolerance", 4)
    .withPosition(4, 0)
    .withSize(1, 1)
    .getEntry();

    turretError = limelightTab.add("turretError", 0)
    .withPosition(4, 1)
    .withSize(1, 1)
    .getEntry();
    
    backWheelMultipler = limelightTab.add("backWheelMultipler", Constants.wheelMultipler)
    .withPosition(4, 3)
    .withSize(1, 1)
    .getEntry();

    insideCircleSpeed = limelightTab.add("insideCircleSpeed", Constants.insideCircleSpeed)
    .withPosition(5, 3)
    .withSize(1, 1)
    .getEntry();
    onCircleSpeed = limelightTab.add("onCircleSpeed", Constants.onCircleSpeed)
    .withPosition(6, 3)
    .withSize(1, 1)
    .getEntry();
    fullCourtSpeed = limelightTab.add("fullCourtSpeed", Constants.fullCourtSpeed)
    .withPosition(5, 2)
    .withSize(1, 1)
    .getEntry();
    dumpBallSpeed = limelightTab.add("dumpBallSpeed", Constants.dumpBallSpeed)
    .withPosition(6, 2)
    .withSize(1, 1)
    .getEntry();

    readyToShoot = limelightTab.add("readyToShoot", false)
    .withPosition(7, 3)
    .withSize(1, 1)
    .getEntry();
  }

  private void initPower(){
    /*
    powerTab.add("Power distribute", RobotContainer.pdp)
    .withWidget(BuiltInWidgets.kPowerDistribution)
    .withPosition(0, 0)
    .withSize(3, 4);
    */
    powerTab.add("Climber", RobotContainer.climber)
    .withPosition(4, 0)
    .withSize(2, 1);
    powerTab.add("Drivetrain", RobotContainer.drivetrain)
    .withPosition(4, 1)
    .withSize(2, 1);
    powerTab.add("Flywheel", RobotContainer.flywheel)
    .withPosition(4, 2)
    .withSize(2, 1);
    powerTab.add("Intake", RobotContainer.intake)
    .withPosition(4, 3)
    .withSize(2, 1);
    powerTab.add("LimelightClass", RobotContainer.limelight)
    .withPosition(6, 0)
    .withSize(2, 1);
    powerTab.add("Turret", RobotContainer.turret)
    .withPosition(6, 1)
    .withSize(2, 1);
    powerTab.add("Uptake", RobotContainer.uptake)
    .withPosition(6, 2)
    .withSize(2, 1);
  }

  private void initAuton(){
    goForAuton = autonTab.add("goForAuton", Constants.goForAuton)
    .withPosition(0, 2)
    .withSize(2, 1)
    .withWidget(BuiltInWidgets.kToggleButton)
    .withProperties(Map.of("Color when false", "red", "Color when true", "blue"))
    .getEntry();

    autonForwardSpeed = autonTab.add("autonForwardSpeed", Constants.autonForwardSpeed)
    .withPosition(0, 0)
    .withSize(1, 1)
    .getEntry();

    autonForwardTime = autonTab.add("autonForwardTime", Constants.autonForwardTime)
    .withPosition(0, 1)
    .withSize(1, 1)
    .getEntry();

    autonTurnSpeed = autonTab.add("autonTurnSpeed", Constants.autonTurnSpeed)
    .withPosition(1, 0)
    .withSize(1, 1)
    .getEntry();

    autonTurnTime = autonTab.add("autonTurnTime", Constants.autonTurnTime)
    .withPosition(1, 1)
    .withSize(1, 1)
    .getEntry();
  }

  private void initCameras(){
     //driver camera
     camerasTab.add(RobotContainer.limelight.frontCamera)
     .withPosition(0, 0)
     .withSize(4, 4);

     camerasTab.add("LL", RobotContainer.limelight.limelightFeed)
    .withPosition(4, 0)
    .withSize(4, 4)
    .withProperties(Map.of("Show Crosshair", true, "Show Controls", true));
  }
}
