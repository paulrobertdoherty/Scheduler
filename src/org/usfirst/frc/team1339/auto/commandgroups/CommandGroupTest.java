package org.usfirst.frc.team1339.auto.commandgroups;

import org.usfirst.frc.team1339.base.CommandGroupBase;
import org.usfirst.frc.team1339.commands.*;
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
		addParallel(new DriveShooterTimeout(0.5, 5));
		addSequential(new DriveStraight(-.5, 2));

		addInterrupter(0, Robot.HardwareAdapter.getYButton());
	}
}
