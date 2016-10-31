package org.usfirst.frc.team1339.robot;

import org.usfirst.frc.team1339.subsystems.*;
import org.usfirst.frc.team1339.subsystems.SubsystemBase;
import org.usfirst.frc.team1339.utils.HardwareAdapter;
import org.usfirst.frc.team1339.utils.Looper;

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
	
	Looper loop;
	SubsystemBase chassis;
	SubsystemBase intake;
	SubsystemBase shooter;
	/**
	 * Teleoperated constructor that adds the chassis subsystem
	 * to Looper.
	 * <br><br>
	 * $
	 * @see Looper
	 */
	TeleOp(){
		intake = new Intake();
		chassis = new Chassis();
		shooter = new Shooter();
		
		loop = new Looper(0.02);
		loop.register(chassis);
		loop.register(intake);
		loop.register(shooter);
	}
	/** This method runs TeleOp at the speed of 20 milliseconds.*/
    public void teleOpPeriodic(){
    	loop.update();
    	Robot.HardwareAdapter.checkTriggers(chassis);
    }
    /** This method is called before TeleOp has run.*/
	public void init(){	
		loop.resetSubsystems();
		HardwareAdapter.kLeftDriveEncoder.reset();
		HardwareAdapter.kRightDriveEncoder.reset();
	}
	/**
	 * This method runs Arcade Drive.
	 * @see Robot
	 */
	/** This method is called after TeleOp has run.*/
	public void stop(){

	}
}
