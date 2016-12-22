package org.usfirst.frc.team1339.commands;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.robot.Robot;

public class DriveStraight extends CommandBase{
	
	private double m_speed;

	public DriveStraight(double speed, double timeout){
		requires(Robot.chassis);
		m_speed = speed;
		setRunSpeed(0.05);
		setName();
		setTimeout(timeout);
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Robot.chassis.setMotorValues(m_speed, m_speed);
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return isTimedOut(); 
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		Robot.chassis.setMotorValues(0, 0);
	}

	@Override
	public void interrupted() {
		// TODO Auto-generated method stub
		Robot.chassis.setMotorValues(0, 0);
	}

}
