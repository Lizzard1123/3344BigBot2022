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
    ButtonA = 0,
    ButtonB = 0,
    ButtonC = 0;


  //axis
  /*
    if preconfigured commands dont work
        return getRawAxis(LEFTJOY_Y);
        set axis channels in defines above
  */
  public double getAxisX(){
    return this.getX();
  }
  public double getAxisY(){
    return this.getY() * -1; //flight controller Y axis inverted bc yannno flying
  }
  public double getThrottle(){
    return this.getZ();
  }
  public double getAxisRZ(){
    return this.getTwist();
  }

  //buttons 
  public boolean getButtonA() {
    return getRawButton(ButtonA);
  }
  public boolean getButtonAPressed() {
    return getRawButtonPressed(ButtonA);
  }

}
