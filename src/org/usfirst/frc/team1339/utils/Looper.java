package org.usfirst.frc.team1339.utils;


import java.util.ArrayList;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.base.SubsystemBase;

import edu.wpi.first.wpilibj.Timer;	

/**
 * The Looper class loops through a list of commands that are supposed to be 
 * scheduled and runs them at certain rates, depending on what runTime is set 
 * within the command.
 * @author Sam Schwartz
 * @author Nate Howard
 * @author Sam Korman
 * @see CommandBase
 * @see SubsystemBase
 */

public class Looper {
	
	//Global Instance of Looper Class
	private static Looper instance;
	
	/** creating an arrayList of commands that are supposed to be run */
	private static ArrayList<CommandBase> commands = new ArrayList<CommandBase>();
	/** creating an ArrayList of subsystems for robot initialization purposes {@link #setInitDefaults()}*/
	private static ArrayList<SubsystemBase> subsystems = new ArrayList<SubsystemBase>();
	
	public Looper(){
		
	}
	
	/** @return global instance of looper */
	public static Looper getInstance(){
	    return instance == null ? instance = new Looper() : instance;
	}
	
	/**
	 * registers a subsystem to a list of subsystems. Currently
	 * only used to set the default commands initially. This method
	 * is called every time a subsystem is instantiated
	 * @see SubsystemBase
	 * @param subsystem
	 */
	public void register(SubsystemBase subsystem){
		subsystems.add(subsystem);
	}
	
	/** 
	 * This method adds the command {@code instance} to the list of running commands.
	 * It checks if the command is running already, and if not, then it removes
	 * the current command from the subsystem, ends it, and then is added to the list
	 * of commands.
	 * @param instance
	 */
	public void newCommand(CommandBase instance){
		boolean isCommandAlready = false;
		
		//checking if command already exists
		for(CommandBase command : commands){
			if(command.getName().equals(instance.getName())) isCommandAlready = true;
		}
		
		//only runs if it doesn't exist already
		if(!isCommandAlready){
			ArrayList<SubsystemBase> requirements = instance.getRequirements();
			for(SubsystemBase subsystem : requirements){  //loops through list of requirements
				if (subsystem != null){
					if(subsystem.getCurrentCommand() != null){
						commands.remove(subsystem.getCurrentCommand()); //removes current command if it exists
						subsystem.getCurrentCommand().cancel(); //cancels current command if it exists
					}
					subsystem.setCurrentCommand(instance); //sets instance to current command
				}
			}
			commands.add(instance); //adds instance to commands list
		}
	}
	
	/**
	 * this method is for initializing the default commands at the start of teleOp
	 */
	public void setInitDefaults(){
		commands.clear(); //clears running list of commands
		for (SubsystemBase subsystem : subsystems){
			//looping through the subsystems that have a default command
			if(subsystem.getDefaultCommand() != null){		
				commands.add(subsystem.getDefaultCommand());
				subsystem.setCurrentCommand(subsystem.getDefaultCommand());
			}
		}
	}
	/**
	 * This method is run continuously to execute the commands.
	 * First, it loops through the list of commands, but it only
	 * hits a certain command if enough time has passed for it to run. {@code CommandBase.getRunSpeed()}
	 * Then, if it is not initialized, it runs the {@code init()} method within the 
	 * command. Then, it executes the command and resets the time. If the command
	 * is finished, then it adds the default command for that subsystem to the 
	 * running list of commands {@link #setDefault(CommandBase)}
	 * <p> $ <p> 
	 * @see SubsystemBase
	 */
	public void update(){
		for(CommandBase command : commands){ //looping through command list
			if(Timer.getFPGATimestamp() > command.getRunSpeed() + command.getLastTime()){ //checking time
				if(!command.isInitialized()){
					command.setInitialized(); //initialize command if not initialized already
				}
				command.execute(); 
				command.resetTime();
				if(command.isFinished()){
					setDefault(command);
				}
			}
		}
    }
	
	/**
	 * This method is run when a command is finished, and it adds
	 * the default command from the finished commands requirements 
	 * ({@code command.getRequirements()}) to the list of commands
	 * @param command
	 */
	private void setDefault(CommandBase command){
		command.cancel(); //runs end method on command
		ArrayList<SubsystemBase> requirements = command.getRequirements();
		for(SubsystemBase subsystem : requirements){ //loops through requirements
			if (subsystem.getDefaultCommand() != null){
				newCommand(subsystem.getDefaultCommand()); 
			}
		}
		commands.remove(command);
	}
	
	/**
	 * This method is for ending a certain command that is currently running.
	 * This should mainly be used for User/Button Interface. Note that 
	 * because it checks the name of the command, the instance that is input
	 * does not have to be the same instance as the one currently running.
	 * @see AngelButton
	 * @param command
	 */
	public void endCommand(CommandBase command){
		String name = command.getName(); //name of command
		for(int i = 0; i < commands.size(); i++){ //loop through list of commands
			if(name.equals(commands.get(i).getName())){ 
				setDefault(commands.get(i)); //end command if in command list
			}
		}
	}
}
