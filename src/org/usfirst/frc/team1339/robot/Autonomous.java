package org.usfirst.frc.team1339.robot;

import org.usfirst.frc.team1339.base.SubsystemBase;
import org.usfirst.frc.team1339.subsystems.Chassis;
import org.usfirst.frc.team1339.subsystems.Intake;
import org.usfirst.frc.team1339.utils.Looper;

/**
 * This is the class for autonomous robot control.
 * <p>
 * *insert autonomous routines here*
 * <p>
 * $
 * <br>
 * @author Sam Schwartz
 * @author Nate Howard
 * @author Sam Korman
 * @see Robot
 *
 */
public class Autonomous {

	Looper loop;
	SubsystemBase chassis;
	SubsystemBase intake;

	Autonomous(){
		intake = new Intake();
		chassis = new Chassis();
		
		loop = new Looper(0.01);
		loop.register(chassis);
		loop.register(intake);
	}
	
	public void autonomousPeriodic(){
    	loop.update();
    }

}