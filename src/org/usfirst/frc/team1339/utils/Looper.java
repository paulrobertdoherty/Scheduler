package org.usfirst.frc.team1339.utils;

import java.util.ArrayList;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.base.SubsystemBase;
import org.usfirst.frc.team1339.robot.Robot;
import org.usfirst.frc.team1339.subsystems.Chassis;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;	

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
	
	public Looper(){
		
	}
	
	/** returns global instance */
	public static Looper getInstance(){
	    return instance == null ? instance = new Looper() : instance;
	}
	
	/** 
	 * This method adds the command @param instance to the list of running commands.
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
	 * this method if for initializing the default commands at the start of teleOp
	 */
	public void setInitDefaults(){
		commands.clear();
		for (SubsystemBase subsystem : SubsystemBase.getDefaults()){
			if(subsystem.getDefaultCommand() != null){		
				commands.add(subsystem.getDefaultCommand());
				subsystem.getDefaultCommand().addRequires(subsystem);
			}
		}
	}
	/**
	 * This method is run continuously to run the commands.
	 * <p> $ <p> 
	 * @see SubsystemBase
	 */
	public void update(){
		for(CommandBase command : commands){
			if(Timer.getFPGATimestamp() > command.getRunSpeed() + command.getLastTime()){
				if(!command.isInitialized()){
					command.init();
					command.setInitialized();
				}
				command.execute();
				command.resetTime();
				if(command.isFinished()){
					setDefault(command);
				}
			}
		}
    }
	
	private void setDefault(CommandBase command){
		command.end();
		ArrayList<SubsystemBase> requirements = command.getRequirements();
		for(SubsystemBase subsystem : requirements){
			if (subsystem.getDefaultCommand() != null){
				newCommand(subsystem.getDefaultCommand());
			}
		}
		commands.remove(command);
	}
}
