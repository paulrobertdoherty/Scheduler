package org.usfirst.frc.team1339.robot;

import org.usfirst.frc.team1339.auto.commandgroups.CommandGroupTest;
import org.usfirst.frc.team1339.base.CommandBase;
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
	
	private CommandBase autoCommand = null;

	Autonomous(){
		
	}
	
	public void init(){
		autoCommand = new CommandGroupTest();
		Looper.getInstance().setInitDefaults();
		if(autoCommand != null) Looper.getInstance().newCommand(autoCommand);
		Robot.HardwareAdapter.kLeftDriveEncoder.reset();
		Robot.HardwareAdapter.kRightDriveEncoder.reset();
	}
	
	public void autonomousPeriodic(){
		Looper.getInstance().update();
    }

}