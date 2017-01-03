package org.usfirst.frc.team1339.commands;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.robot.Robot;

public class MotionProfileTest extends CommandBase {
	
	private double m_goal;
	
	public MotionProfileTest(double goal){
		requires(Robot.chassis);
		m_goal = goal;
	}

	protected void init() {
		// TODO Auto-generated method stub
		Robot.HardwareAdapter.ChassisMP.configureNewProfile(m_goal);
	}

	public void execute() {
		// TODO Auto-generated method stub
		//System.out.println("Running");
		Robot.chassis.motionProfile();
	}

	public boolean isFinished() {
		// TODO Auto-generated method stub
		return Robot.HardwareAdapter.ChassisMP.isFinishedTrajectory();
	}

	protected void end() {
		// TODO Auto-generated method stub
		
	}

	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
