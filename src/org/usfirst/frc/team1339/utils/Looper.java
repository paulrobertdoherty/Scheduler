package org.usfirst.frc.team1339.utils;

import java.util.ArrayList;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.base.SubsystemBase;

import edu.wpi.first.wpilibj.Timer;	

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
	private ArrayList<SubsystemBase> subsystems;
	/** Time variable utilizing the FPGA timer.*/
	private double time; 
	private double m_delay;
	
	private ArrayList<CommandBase> commands;
	/**
	 * This method creates the ArrayList subsystems and sets the
	 * variable time to the current FPGA time.
	 * <br>$<br>
	 * @param speed = the speed of the loop.
	 * @see ArrayList
	 */
	public Looper(double delay){
		subsystems = new ArrayList<SubsystemBase>();
		commands = new ArrayList<CommandBase>();
		time = Timer.getFPGATimestamp();
		m_delay = delay;
	}
	/**
	 * This method registers various subsystems that we create into
	 * the ArrayList subsystems.
	 * <br>$<br>
	 * @param instance = subsystem variable
	 * @see SubsystemBase
	 * @see ArrayList
	 */
	public void register(SubsystemBase instance){
		subsystems.add(instance);
	}
	
	/* adds a command only when it is supposed to be scheduled e.g  whenPressed()*/
	public void newCommand(CommandBase instance){
		commands.add(instance);
	}
	/**
	 * This method updates subsystems.
	 * <p> $ <p> 
	 * @see SubsystemBase
	 */
	public void update(){
    	if(Timer.getFPGATimestamp() > time + m_delay){ //Updates every m_delay
    		
    		//Gets commands to run
    		for(int x = 0; x < subsystems.size(); x++){ 
    			commands.add(x, subsystems.get(x).getDefaultCommand());
    		}
    		
    		//Runs commands
    		for(int commandNum = 0; commandNum < commands.size(); commandNum++){ 
    			//Creates and files commands - EDIT I MADE - WHOLE LOOP
    			if(commands.get(commandNum) != null){
    				
    				//Create an object for the command
    				CommandBase command = commands.get(commandNum);
    				
    				//Initialize the command if it's not already
    				if(!command.isInitialized()){
						command.init();
						command.setInitialized();
    				}
    				
    				//Execute the command
    				commands.get(commandNum).execute();
    				
    				//Check if the command is finished
    				if(command.isFinished()){
    					command.end();
    				}
    			}
    		}
    		
    		commands.clear();
			
            time = Timer.getFPGATimestamp();
            
    		for(int x = 0; x < subsystems.size(); x++){
    			if(subsystems.get(x).getNextCommand().isFinished()){
    				subsystems.get(x).endScheduledCommand();
    			}
    		}
    	}
    }
	
	public void resetSubsystems(){
		for(int x = 0; x < subsystems.size(); x++){
			subsystems.get(x).endScheduledCommand();
		}
	}
	
	public void createNewLoop(Looper oldLoop, Looper newLoop, SubsystemBase subsystem){
		oldLoop.removeRegisteredSubsystem(subsystem);
		newLoop.register(subsystem);
	}
	
	public void removeRegisteredSubsystem(SubsystemBase subsystem){
		subsystems.remove(subsystem);
	}
}
