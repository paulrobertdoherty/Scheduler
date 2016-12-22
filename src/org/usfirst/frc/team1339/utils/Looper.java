package org.usfirst.frc.team1339.utils;

import java.util.ArrayList;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.base.SubsystemBase;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;	

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
	
	private static ArrayList<CommandBase> commands = new ArrayList<CommandBase>();
	
	public Looper(){
		
	}
	
	public static Looper getInstance(){
	    return instance == null ? instance = new Looper() : instance;
	}
	
	public void newCommand(CommandBase instance){
		boolean isCommandAlready = false;
		for(CommandBase command : commands){
			if(command.getName().equals(instance.getName())) isCommandAlready = true;
		}
		if(isCommandAlready)System.out.println(instance.getName() + " is already a command");
		if(!isCommandAlready){
			System.out.println("new " + instance.getName());
			ArrayList<SubsystemBase> requirements = instance.getRequirements();
			for(SubsystemBase subsystem : requirements){
				if (subsystem != null){
					if(subsystem.getCurrentCommand() != null){
						System.out.println(subsystem.getCurrentCommand().getName() + "current command");
						commands.remove(subsystem.getCurrentCommand());
						subsystem.getCurrentCommand().cancel();
					}
					subsystem.setCurrentCommand(instance);
				}
			}
			commands.add(instance);
		}
	}
	
	public void setInitDefaults(){
		commands.clear();
		System.out.println(SubsystemBase.getDefaults().size() + "size");
		for (SubsystemBase subsystem : SubsystemBase.getDefaults()){
			if(subsystem.getDefaultCommand() != null){		
				commands.add(subsystem.getDefaultCommand());
				subsystem.getDefaultCommand().addRequires(subsystem);
				subsystem.setCurrentCommand(subsystem.getDefaultCommand());
			}
		}
	}
	/**
	 * This method updates subsystems.
	 * <p> $ <p> 
	 * @see SubsystemBase
	 */
	public void update(){
		SmartDashboard.putNumber("Number of commands", commands.size());
		for(CommandBase command : commands){
			SmartDashboard.putString("commands", commands.toString());
			if(Timer.getFPGATimestamp() > command.getRunSpeed() + command.getLastTime()){
				if(!command.isInitialized()){
					command.init();
					command.setInitialized();
				}
				command.execute();
				command.resetTime();
				if(command.isFinished()){
					System.out.println(command.getName() + " finished");
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
				System.out.println("Set Default Running");
				newCommand(subsystem.getDefaultCommand());
			}
		}
		commands.remove(command);
	}
}
