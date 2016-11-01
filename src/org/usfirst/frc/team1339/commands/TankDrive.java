package org.usfirst.frc.team1339.commands;

import org.usfirst.frc.team1339.robot.Robot;
import org.usfirst.frc.team1339.utils.Constants;

import edu.wpi.first.wpilibj.Joystick;

public class TankDrive extends CommandBase{
	
	double left, right;
	Joystick stick;
	
	public TankDrive(){
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
		return Robot.HardwareAdapter.getBButton();
	}
	public void end(){
	}
	
	public void interrupted(){
		
	}
}
