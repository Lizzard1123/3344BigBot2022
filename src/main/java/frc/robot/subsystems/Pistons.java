package frc.robot.subsystems;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

//import java.util.HashMap;
//import java.util.Map;

//import edu.wpi.first.math.MathUtil;
//import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
//import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
//import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
//import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
//import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
//import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
//import frc.robot.RobotContainer;

public class Pistons extends SubsystemBase {
    public final static DoubleSolenoid leftPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.leftPistonPort1, Constants.leftPistonPort2);
    public final static DoubleSolenoid rightPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.rightPistonPort1, Constants.rightPistonPort2);

    public Pistons(){
        super();
        leftPiston.set(kReverse); // set init position of solonoids
        rightPiston.set(kReverse);
    }

    public void togglePiston(){
        leftPiston.toggle();
        rightPiston.toggle();
    }

}
