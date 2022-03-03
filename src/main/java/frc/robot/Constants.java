// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    //Ports
    public static int FRPort = 1;
    public static int FLPort = 2;
    public static int BRPort = 3;
    public static int BLPort = 4;
    public static int flywheelPort = 5;
    public static int turretPort = 6;
    public static int uptakePort = 7;
    //others
    public static int flightControllerPort = 0;

    //Drive speeds
    public static double maxSpeed = 50;
    public static double turnSpeed = 100;
    public static double turtle = 40;
    public static double rabbit = 100;
    public static double normal = 70;
    public static double driveSet = normal;
    public static double k_deadband = .05; //deadzone for controller inputs

    //Flywheel constants
    public static double flywheelManualSpeed = 0; // default for manual control
    public static double flywheelMaxSpeed = 10;
    public static double turretMaxspeed = 10;

    //misc
    public static boolean flyhweelAnalog = true; // speed of flyhweel manual controller
}
