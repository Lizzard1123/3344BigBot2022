// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.I2C;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    //important
    public static boolean manualOverride = false;

    //Ports
    public static int FRPort = 1;
    public static int FLPort = 0;
    public static int BRPort = 3;
    public static int BLPort = 2;
    public static int flywheelPort = 4;
    public static int turretPort = 5;
    public static int uptakePort = 6;
    public static int intakePort = 7;
    public static int climberPort = 8;
    //public static int armPort = 8;
    public static I2C.Port colorPort = I2C.Port.kOnboard;
    public static int leftLimitPort = 0;
    public static int rightLimitPort = 1;
    //others
    public static int flightControllerPort = 0;

    //Drive speeds
    public static double maxSpeed = 50;
    public static double turnSpeed = 100;
    public static double turtle = 40;
    public static double rabbit = 100;
    public static double normal = 70;
    public static double driveSet = normal;
    public static double k_deadband = .1; //deadzone for controller inputs
    public static double scanWidth = 15;
    public static double shootWidth = 20;

    //max speeds
    public static double flywheelMaxSpeed = 100;
    public static double turretMaxSpeed = 20;
    public static double uptakeMaxSpeed = 60;
    public static double intakeMaxSpeed = 25;
    public static double climberMaxSpeed = 50;

    //auton
    public static double autonForwardSpeed = 30;
    public static double autonForwardTime = 3;
    public static double autonTurnSpeed = 25;
    public static double autonTurnTime = 5;
    //misc
    public static boolean flywheelAnalog = false; // speed of flywheel manual controller
    public static boolean isBlue = true; //holds team color false means red
    public static boolean holdingBall = false;
    public static boolean holdingBlueBall = false;
    public static boolean readyToShoot = false;
    public static double minDist = 130;
    public static double shootTime = .75;
    public static double getEjectSetGoal = 500;
    public static double defaultGimbal = 10;
}
