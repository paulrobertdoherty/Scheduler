package org.usfirst.frc.team1339.auto.commandGroups;

import org.usfirst.frc.team1339.commands.ArcadeDrive;
import org.usfirst.frc.team1339.commands.DriveShooter;
import org.usfirst.frc.team1339.commands.TankDrive;
import org.usfirst.frc.team1339.robot.Robot;
import org.usfirst.frc.team1339.subsystems.SubsystemBase;

public class CommandGroupTest extends CommandGroupBase{

	public CommandGroupTest(SubsystemBase chassis, SubsystemBase shooter){
		addSequential(new TankDrive(), chassis);
		addParallel(new DriveShooter(), shooter);
		addSequential(new ArcadeDrive(), chassis);
		addInterrupter(1);
	}

	@Override
	protected boolean isInterrupted(int index) {
		if (index == 1){
			return Robot.HardwareAdapter.getLeftStickButton();
		}
		return false;
	}
}
