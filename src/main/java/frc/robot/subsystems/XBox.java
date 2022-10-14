package frc.robot.subsystems;

/**
 * Instantiates button numbers to names, so we can read this more easily.
 * It says XBox, but should work for any controller compatible with windows.
 * I THINK the getButton() methods just grab whether or not the button is pressed
 */
import edu.wpi.first.wpilibj.Joystick;

public class XBox extends Joystick{
    
    public XBox(int port) {
        super(port);
    }
    public static final int
  //buttons
            A_BUTTON = 1,
            B_BUTTON = 2,
            X_BUTTON = 3,
            Y_BUTTON = 4,
            LB_BUTTON = 5,
            RB_BUTTON = 6,
            BACK_BUTTON = 7,
            START_BUTTON = 8,
            LS_BUTTON = 9,
            RS_BUTTON = 10,
            upButton = 11, 
            rightButton = 12,
            downButton = 13,
            
            //axes
            LEFTJOY_X = 0,
            LEFTJOY_Y = 1,
            RIGHTJOY_Y = 5,
            RIGHTJOY_X = 4,
            LEFT_TRIGGER = 2,
            RIGHT_TRIGGER = 3;
    
    public boolean getButtonA() {
     return getRawButton(A_BUTTON);
}
    
    public boolean getButtonB() {
        return getRawButton(B_BUTTON);
    }
    
    public boolean getButtonX() {
        return getRawButton(X_BUTTON);
    }
    
    public boolean getButtonY() {
        return getRawButton(Y_BUTTON);
    }
    
    public boolean getButtonRB() {
        return getRawButton(RB_BUTTON);
    }
    
    public boolean getButtonLB() {
        return getRawButton(LB_BUTTON);
    }
    
    public boolean getButtonRS() {
        return getRawButton(RS_BUTTON);
    }
    
    public boolean getButtonLS() {
        return getRawButton(LS_BUTTON);
    }
    
    public boolean getButtonStart() {
        return getRawButton(START_BUTTON);
    }
    
    public boolean getButtonBack() {
        return getRawButton(BACK_BUTTON);
    }
    
    public double getLeftJoyY() {
        return getRawAxis(LEFTJOY_Y);
    }
    
    public double getLeftJoyX() {
        return getRawAxis(LEFTJOY_X);
    }
    
    public double getRightJoyY() {
        return getRawAxis(RIGHTJOY_Y);
    }
    
    public double getRightJoyX(){
        return getRawAxis(RIGHTJOY_X);
    }
    
    public double getRightTrigger() {
        return getRawAxis(RIGHT_TRIGGER);
    }
    
    public double getLeftTrigger() {
    	return getRawAxis(LEFT_TRIGGER);
    }
}