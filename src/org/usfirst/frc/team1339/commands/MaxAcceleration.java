package org.usfirst.frc.team1339.commands;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class MaxAcceleration extends CommandBase{

	private double initTime;
	
	public MaxAcceleration(){
		requires(Robot.chassis);
		setName();
		setRunSpeed(0.05);
		setTimeout(2);
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		initTime = Timer.getFPGATimestamp();
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		if(Timer.getFPGATimestamp() < initTime + 1){
			Robot.chassis.setMotorValues(1, 1);
		} else{
			Robot.chassis.setMotorValues(0, 0);
		}
		Robot.chassis.calculate();
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return isTimedOut();
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		Robot.chassis.getAvgAcc();
		Robot.chassis.setMotorValues(0, 0);
	}

	@Override
	public void interrupted() {
		// TODO Auto-generated method stub
		Robot.chassis.getAvgAcc();
		Robot.chassis.setMotorValues(0, 0);
	}

}
