package org.usfirst.frc.team1339.commands;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.robot.Robot;
import org.usfirst.frc.team1339.utils.HardwareAdapter;

public class MotionProfileTest extends CommandBase {
	
	private double m_goal;
	
	public MotionProfileTest(double goal){
		m_goal = goal;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		Robot.HardwareAdapter.ChassisMP.configureNewProfile(m_goal);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("Running");
		Robot.chassis.motionProfile();
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return Robot.HardwareAdapter.ChassisMP.isFinishedTrajectory();
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
