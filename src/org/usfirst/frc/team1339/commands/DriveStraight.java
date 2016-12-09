package org.usfirst.frc.team1339.commands;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.robot.Robot;

public class DriveStraight extends CommandBase{

	public DriveStraight(double m_speed, double timeout){
		requires(Robot.chassis);
		setRunSpeed(m_speed);
		setTimeout(timeout);
		setName();
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Robot.chassis.setMotorValues(0.5, 0.5);
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
