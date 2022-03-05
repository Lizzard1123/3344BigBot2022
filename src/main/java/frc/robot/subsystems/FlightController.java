// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;

public class FlightController extends Joystick {

  public FlightController(int port) {
    super(port);
  }
  
  //button Ports
  public static int 
    AxisX = 0,
    AxisY = 1,
    AxisZ = 2,
    AxisZR = 3,
    AxisSlider = 4,
    ButtonL1 = 2,
    ButtonL2 = 10,
    ButtonL3 = 4,
    ButtonR1 = 1,
    ButtonR2 = 9,
    ButtonR3 = 3,
    Button5 = 5,
    Button6 = 6,
    Button7 = 7,
    Button8 = 8,
    ButtonSE = 11,
    ButtonST = 12;


  //axis
  public double getAxisX(){
    return this.getRawAxis(AxisX);
  }
  public double getAxisY(){
    return this.getRawAxis(AxisY) * -1; //flight controller Y axis inverted bc yannno flying
  }
  public double getThrottle(){
    return this.getRawAxis(AxisZ) * -1;
  }
  public double getAxisRZ(){
    return this.getRawAxis(AxisZR);
  }
  //two buttons on throttle? 
  //TODO double check that these are the right buttons
  public double getAxisSlider(){
    return this.getRawAxis(AxisSlider);
  }

  //buttons 
  public boolean getTrigger() {
    return getRawButton(ButtonR1);
  }
  public boolean getTriggerPressed() {
    return getRawButtonPressed(ButtonR1);
  }

  //just general ones

  //returns button state of port
  public boolean getButton(int port) {
    return getRawButton(port);
  }
  //returns button new press from port
  public boolean getButtonPressed(int port) {
    return getRawButtonPressed(port);
  }

  public boolean getDpadRelativeUp(){
    return this.getPOV() == 0 || this.getPOV() == 45 || this.getPOV() == 315;
  }

  public boolean getDpadRelativeDown(){
    return this.getPOV() == 180 || this.getPOV() == 135 || this.getPOV() == 225;
  }

}
