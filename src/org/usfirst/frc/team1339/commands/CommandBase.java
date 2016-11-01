package org.usfirst.frc.team1339.commands;

import org.usfirst.frc.team1339.robot.Robot;
import org.usfirst.frc.team1339.subsystems.Chassis;
import org.usfirst.frc.team1339.subsystems.Intake;
import org.usfirst.frc.team1339.subsystems.Shooter;

import edu.wpi.first.wpilibj.Timer;

public abstract class CommandBase {
	
	//Subsystems
	public static Chassis chassis = Robot.chassis;
	public static Intake intake = Robot.intake;
	public static Shooter shooter = Robot.shooter;
	
	
	private double m_time = -1;
	private double startTime;
	
	private boolean initialized = false;
	private boolean isCommandGroup = false;
	
	public abstract void init();
	
	public abstract void execute();
	
	public abstract boolean isFinished();
	
	public abstract void end();
	
	public abstract void interrupted();
	
	public boolean isInitialized(){
		return initialized;
	}
	
	public void setInitialized(){
		initialized = true;
	}
	
	protected void setTimeout(double time){
		m_time = Math.abs(time);
		startTime = Timer.getFPGATimestamp();
	}
	
	protected boolean isTimedOut(){
		if (m_time < 0) return false;
		if (Timer.getFPGATimestamp() > startTime + m_time){
			return true;
		}
		return false;
	}
	
	public boolean isCommandGroup(){
		return false;
	}
}
