package org.usfirst.frc.team1339.utils;

import java.util.ArrayList;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.base.SubsystemBase;
import org.usfirst.frc.team1339.subsystems.Chassis;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;	

/**
 * The Looper class calls and creates subsystems and runs them at certain rates.
 * @author Sam Schwartz
 * @author Nate Howard
 * @author Sam Korman
 * @see CommandBase
 * @see SubsystemBase
 */

public class Looper {
	/** Creating an array list of subsystems.*/
	
	private static Looper instance;
	
	private static ArrayList<CommandBase> commands;
	/**
	 * This method creates the ArrayList subsystems and sets the
	 * variable time to the current FPGA time.
	 * <br>$<br>
	 * @param speed = the speed of the loop.
	 * @see ArrayList
	 */
	public Looper(){}
	/**
	 * This method registers various subsystems that we create into
	 * the ArrayList subsystems.
	 * <br>$<br>
	 * @param instance = subsystem variable
	 * @see SubsystemBase
	 * @see ArrayList
	 */
	public static Looper getInstance() {
	    return instance == null ? instance = new Looper() : instance;
	  }
	
	
	/* adds a command only when it is supposed to be scheduled e.g  whenPressed()*/
	public void newCommand(CommandBase instance){
		ArrayList<SubsystemBase> requirements = instance.getRequirements();
		for(SubsystemBase subsystem : requirements){
			if (subsystem == null) System.out.println("Subsystem is null");
			if(subsystem.getCurrentCommand() != null){
				commands.remove(subsystem.getCurrentCommand());
				subsystem.getCurrentCommand().cancel();
			}
			subsystem.setCurrentCommand(instance);
		}
		commands.add(instance);
	}
	
	public void setInitDefaults(){
		for (SubsystemBase subsystem : SubsystemBase.getDefaults()){
			if(subsystem.getDefaultCommand() != null){
				commands.add(subsystem.getDefaultCommand());
			}
		}
	}
	/**
	 * This method updates subsystems.
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
