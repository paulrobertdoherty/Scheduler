package org.usfirst.frc.team1339.commands;

public abstract class CommandBase {
	
	private boolean initialized = false;
	
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
}
