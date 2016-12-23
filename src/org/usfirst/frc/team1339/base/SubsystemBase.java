package org.usfirst.frc.team1339.base;

import java.util.ArrayList;

import org.usfirst.frc.team1339.utils.Looper;

/**
 * The subsystem class is the very core of the entire subsystem framework.
 * Every subsystem is called through {@link run()} and run in Looper.
 * <br>$<br>
 * @author Sam Schwartz
 * @author Nate Howard
 * @author Sam Korman
 */
	
public abstract class SubsystemBase {
	
	private CommandBase currentCommand, defaultCommand;
	
	private static ArrayList<SubsystemBase> defaults = new ArrayList<SubsystemBase>();
	
	public abstract void initDefaultCommand();
	
	protected SubsystemBase(){
		Looper.getInstance().register(this);
	}
	
	protected void setDefaultCommand(CommandBase command){
		defaultCommand = command;
		currentCommand = defaultCommand;
		defaults.add(this);
	}
	
	public void setCurrentCommand(CommandBase command){
		currentCommand = command;
	}
	
	public static ArrayList<SubsystemBase> getDefaults(){
		return defaults;
	}
	
	public CommandBase getCurrentCommand(){
		return currentCommand;
	}
	
	public CommandBase getDefaultCommand(){
		if(defaultCommand == null)
			initDefaultCommand();
		return defaultCommand;
	}
	
	public void endScheduledCommand(){
		currentCommand = getDefaultCommand();
	}
}
