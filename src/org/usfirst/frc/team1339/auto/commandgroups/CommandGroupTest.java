package org.usfirst.frc.team1339.auto.commandgroups;

import org.usfirst.frc.team1339.base.CommandGroupBase;
import org.usfirst.frc.team1339.base.SubsystemBase;
import org.usfirst.frc.team1339.commands.ArcadeDrive;
import org.usfirst.frc.team1339.commands.TankDrive;
import org.usfirst.frc.team1339.robot.Robot;

/**
 * A test commandgroup.
 * @author Sam Schwartz
 * @author Nate Howard
 * @author Sam Korman
 * @see CommandGroupBase
 * @see Robot
 */

public class CommandGroupTest extends CommandGroupBase{

	public CommandGroupTest(){
		addSequential(new TankDrive(), Robot.chassis);
		addSequential(new ArcadeDrive(), Robot.chassis);
		addInterrupter(1);
		addSequential(new TankDrive(), Robot.chassis);
	}
	/**
	 * This function returns {@code true} when the creator
	 * wants a sequential command at a given index to be
	 * interrupted. 
	 * 
	 * @param index = index at which the sequential command is
	 */ 
	protected boolean isInterrupted(int index) {
		if (index == 1){
			return Robot.HardwareAdapter.getXButton();
		}
		return false;
	}
}
