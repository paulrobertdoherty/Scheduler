package org.usfirst.frc.team1339.robot;

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


	Autonomous(){
		
	}
	
	public void autonomousPeriodic(){
		Looper.getInstance().update();
    }

}