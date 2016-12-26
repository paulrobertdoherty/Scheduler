package org.usfirst.frc.team1339.base;

import org.usfirst.frc.team1339.utils.Looper;

/**
 * This is the base class of all subsystems, and all subsystems should extend this class.
 * 
 * @author Sam Schwartz
 * @author Nate Howard
 * @author Sam Korman
 */
	
public abstract class SubsystemBase {
	
	/**
	 * A variable that contains the instance of the command that we are currently running.
	 */
	private CommandBase currentCommand;
	
	/**
	 * A variable that contains the default command of this subsystem.
	 */
	private CommandBase defaultCommand;
	
	/**
	 * The method in which you should call the method {@link #setDefaultCommand(CommandBase)}.
	 */
	public abstract void initDefaultCommand();
	
	/**
	 * The constructor for {@link SubsystemBase}. This automatically registers this subsystem to
	 * {@link Looper} when any subsystem is constructed.
	 */
	protected SubsystemBase(){
		Looper.getInstance().register(this);
	}
	
	/**
	 * This method is used in the subsystem to set the default command of that subsystem.
	 * 
	 * @param command The command to be the default command
	 */
	protected void setDefaultCommand(CommandBase command){
		defaultCommand = command;
		currentCommand = defaultCommand;
	}
	
	/**
	 * This method is used to set the current command of the subsystem. Use this
	 * when changing commands.
	 * 
	 * @param command The new command that requires this subsystem.
	 */
	public void setCurrentCommand(CommandBase command){
		currentCommand = command;
	}
	
	/**
	 * This method returns the current command that is requiring this subsystem.
	 * 
	 * @return The current command
	 */
	public CommandBase getCurrentCommand(){
		return currentCommand;
	}
	
	/**
	 * This method returns the default command, which is set by {@link #setDefaultCommand(CommandBase)}.
	 * 
	 * @return The default command
	 */
	public CommandBase getDefaultCommand(){
		if(defaultCommand == null)
			initDefaultCommand();
		return defaultCommand;
	}
}
