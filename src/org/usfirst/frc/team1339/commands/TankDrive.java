package org.usfirst.frc.team1339.commands;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.robot.Robot;
import org.usfirst.frc.team1339.utils.Constants;

import edu.wpi.first.wpilibj.Joystick;

public class TankDrive extends CommandBase{
	
	double left, right;
	Joystick stick;
	
	public TankDrive(){
		requires(Robot.chassis);
		setRunSpeed(0.05);
		setName();
	}
	
	public void init(){
		
	}
	
	public void execute(){
		stick = Robot.HardwareAdapter.getXboxStick();
		left = stick.getRawAxis(Constants.xboxLeftYAxis);
		right = stick.getRawAxis(Constants.xboxRightYAxis);
		
		Robot.chassis.tankDrive(left, right);
	}
	
	public boolean isFinished(){
		return false;
	}
	
	public void end(){
	}
	
	public void interrupted(){
		
	}
}
