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
		addSequential(new DriveStraight(.5, 1));
		addSequential(new DriveStraight(-.5, 1));
	}

	@Override
	protected boolean isInterrupted(int index) {
		// TODO Auto-generated method stub
		return false;
	}
}
