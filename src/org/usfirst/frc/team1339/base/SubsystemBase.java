package org.usfirst.frc.team1339.base;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The subsystem class is the very core of the entire subsystem framework.
 * Every subsystem is called through {@link run()} and run in Looper.
 * <br>$<br>
 * @author Sam Schwartz
 * @author Nate Howard
 * @author Sam Korman
 */
	
public abstract class SubsystemBase {
	
	private CommandBase nextCommand, defaultCommand;
	
	private boolean commandGroupInit = false;
	
	protected void setDefaultCommand(CommandBase command){
		defaultCommand = command;
		nextCommand = defaultCommand;
	}
	
	public void setNextCommand(CommandBase command){
		nextCommand = command;
	}
	
	public CommandBase getNextCommand(){
		return nextCommand;
	}
	
	public CommandBase getDefaultCommand(){
		return defaultCommand;
	}
	
	public void schedule(CommandBase command){
		if(!getNextCommand().isFinished()){
			getNextCommand().interrupted();
		}
		nextCommand = command;
	}
	
	public void endScheduledCommand(){
		nextCommand = getDefaultCommand();
	}
}
