package org.usfirst.frc.team1339.robot;

import org.usfirst.frc.team1339.utils.Looper;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is the class for teleoperated robot control. In this
 * instance, we use Arcade Drive, consisting of two joysticks,
 * one being assigned to throttle and the other being assigned
 * to turn.
 * <br><br>
 * $
 * @author Sam Schwartz
 * @author Nate Howard
 * @author Sam Korman
 * <br><br>
 * @see Autonomous
 * @see Disabled
 * @see Robot
 */
public class TeleOp {
	
	TeleOp(){
		
	}
	/** This method runs TeleOp at the speed of 20 milliseconds.*/
    public void teleOpPeriodic(){
    	Looper.getInstance().update();
    	Robot.HardwareAdapter.checkTriggers();
		SmartDashboard.putNumber("left drive encoder", Robot.HardwareAdapter.getLeftDriveEnc());
		SmartDashboard.putNumber("right encoder", Robot.HardwareAdapter.getRightDriveEnc());
		SmartDashboard.putNumber("Gyro",  Robot.HardwareAdapter.kSpartanGyro.getAngle());
    }
    /** This method is called before TeleOp has run.*/
	public void init(){	
		Looper.getInstance().setInitDefaults();
		Robot.HardwareAdapter.kLeftDriveEncoder.reset();
		Robot.HardwareAdapter.kRightDriveEncoder.reset();
	}
	/** This method is called after TeleOp has run.*/

}
