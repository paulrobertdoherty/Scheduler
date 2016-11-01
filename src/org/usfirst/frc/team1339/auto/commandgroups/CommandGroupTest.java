package org.usfirst.frc.team1339.auto.commandgroups;

import org.usfirst.frc.team1339.commands.ArcadeDrive;
import org.usfirst.frc.team1339.commands.DriveShooter;
import org.usfirst.frc.team1339.commands.TankDrive;
import org.usfirst.frc.team1339.robot.Robot;
import org.usfirst.frc.team1339.subsystems.SubsystemBase;

public class CommandGroupTest extends CommandGroupBase{

	public CommandGroupTest(){
		addSequential(new TankDrive(), Robot.chassis);
		addSequential(new ArcadeDrive(), Robot.chassis);
		addInterrupter(1);
		addSequential(new TankDrive(), Robot.chassis);
	}

	@Override
	protected boolean isInterrupted(int index) {
		if (index == 1){
			return Robot.HardwareAdapter.getXButton();
		}
		return false;
	}
}
