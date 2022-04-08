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
    public static boolean manualOverride = true;

    //Ports
    public static int FRPort = 1;
    public static int FLPort = 0;
    public static int BRPort = 3;
    public static int BLPort = 2;
    public static int flywheelPort = 4;
    public static int backFlywheelPort = 13;
    public static int turretPort = 5;
    public static int uptakePort = 6;
    public static int intakePort = 7;
    public static int climberPort = 12;
    public static I2C.Port colorPort = I2C.Port.kOnboard;
    public static int leftLimitPort = 0;
    public static int rightLimitPort = 1;
    //others
    public static int flightControllerPort = 0;

    //Drive speeds
    public static double maxSpeed = 100;
    public static double turnSpeed = 100;
    public static double turtle = 100;
    public static double rabbit = 100;
    public static double normal = 100;
    public static double driveSet = normal;
    public static double k_deadband = .1; //deadzone for controller inputs
    public static double scanWidth = 15;
    public static double shootWidth = 20;

    //max speeds
    public static double flywheelMaxSpeed = 100;
    //flywheel setpoints
    public static double insideCircleSpeed = 45;
    public static double onCircleSpeed = 39;
    public static double fullCourtSpeed = 80;
    public static double dumpBallSpeed = 30;

    public static double turretMaxSpeed = 35;
    public static double uptakeMaxSpeed = 60;
    public static double intakeMaxSpeed = 40;
    public static double climberMaxSpeed = 100;

    //auton
    public static boolean goForAuton = false; 
    public static double autonForwardSpeed = 40;
    public static double autonForwardTime = 1.9;
    public static double autonTurnSpeed = 40;
    public static double autonTurnTime = 2;
    //misc
    public static boolean flywheelAnalog = false; // speed of flywheel manual controller
    public static boolean isBlue = true; //holds team color false means red
    public static boolean holdingBall = false;
    public static boolean holdingBlueBall = false;
    public static boolean readyToShoot = false;
    public static double minDist = 130;
    public static double shootTime = 1;
    public static double defaultGimbal = 10;
    public static double wheelMultipler = -2;//1.52;
}
