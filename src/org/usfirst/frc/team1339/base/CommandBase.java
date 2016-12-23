package org.usfirst.frc.team1339.base;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;

/**
 * 
 * @author Sam Schwartz
 * @author Nate Howard
 * @author Sam Korman
 * @see Timer
 *
 */

public abstract class CommandBase {
	
	private double m_time = -1;
	private double startTime;
	private double m_speed = 0;
	private double runTime = 0;
	private String m_name = "";
	
	private boolean initialized = false;
	
	private ArrayList<SubsystemBase> requirements = new ArrayList<SubsystemBase>();
	
	public abstract void init();
	
	public abstract void execute();
	
	public abstract boolean isFinished();
	
	public abstract void end();
	
	public abstract void interrupted();
	
	public void cancel(){
		if(isFinished()) end();
		else interrupted();
	}
	
	protected void setName(){
		this.m_name = this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.') + 1);
	}
	
	public String getName(){
		return m_name;
	}
	
	protected void setRunSpeed(double speed){
		m_speed = speed;
	}
	
	protected void requires(SubsystemBase instance){
		if(!requirements.contains(instance))
			requirements.add(instance);
	}
	
	protected void setTimeout(double time){
		m_time = Math.abs(time);
	}
	
	protected boolean isTimedOut(){
		if (m_time < 0) return false;
		if (Timer.getFPGATimestamp() > startTime + m_time){
			return true;
		}
		return false;
	}
	
	public ArrayList<SubsystemBase> getRequirements(){
		return requirements;
	}
	
	public void resetTime(){
		runTime = Timer.getFPGATimestamp();
	}
	
	public double getLastTime(){
		return runTime;
	}
	
	public boolean isInitialized(){
		return initialized;
	}
	
	public void setInitialized(){
		startTime = Timer.getFPGATimestamp();
		initialized = true;
	}
	/**
	 * Test
	 * @return
	 */
	public double getRunSpeed(){
		return m_speed;
	}
}
