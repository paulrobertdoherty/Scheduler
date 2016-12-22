package org.usfirst.frc.team1339.base;

import java.util.ArrayList;

import org.usfirst.frc.team1339.utils.Looper;
import org.usfirst.frc.team1339.utils.Triggers;

/**
 * 
 * @author Sam Schwartz
 * @author Nate Howard
 * @author Sam Korman
 * <br>
 * @see ArrayList
 */

public abstract class CommandGroupBase extends CommandBase{

	private ArrayList<CommandBase> commands = new ArrayList<CommandBase>();
	private ArrayList<Boolean> parallels = new ArrayList<Boolean>();
	private ArrayList<Integer> interupters = new ArrayList<Integer>();
	private boolean isFinished = false;
	
	private int index = 0;
	
	public void init(){
		
	}
	/**
	 * This method gets called every time a command group runs.
	 * It schedules commands based on sequentials and parallels
	 * created in the actual command group. It decides if the
	 * command before it is either {@code finished}, {@code interrupted}
	 * or a {@code parallel}.
	 * <br>
	 * If any of those are {@code true}, then it schedules the next
	 * command.
	 * @see CommandBase
	 */
	public void execute() {
		if (commands.size() == parallels.size() &&
				interupters.size() <= commands.size()){
			if(index == 0){
				Looper.getInstance().newCommand(commands.get(index));
				index++;
			}
			else if(parallels.get(index - 1).equals(true)){
				if(index < commands.size()){
					Looper.getInstance().newCommand(commands.get(index));
					index++;
				}
				else {
					isFinished = true;
				}
			}
			else if(isInterruptIndex(index - 1)){
				if(index < commands.size()){
					Looper.getInstance().newCommand(commands.get(index));
					index++;
				}
				else {
					isFinished = true;
				}
			}
			else if(commands.get(index - 1).isFinished()){
				if(index < commands.size()){
					Looper.getInstance().newCommand(commands.get(index));
					index++;
				}
				else {
					isFinished = true;
				}
			}
		}		
	}

	public boolean isFinished() {
		return isFinished;
	}
	
	public void end() {

	}
	
	public void interrupted(){
		
	}
	
	protected void addSequential(CommandBase command){
		commands.add(command);
		parallels.add(false);
	}
	
	protected void addParallel(CommandBase command){
		commands.add(command);
		parallels.add(true);
	}
	protected void addInterrupter(int index){
		interupters.add(index);
	}
	
	protected abstract boolean isInterrupted(int index);
	
	private boolean isInterruptIndex(int index){
		for(int i = 0; i < interupters.size(); i++){
			if(interupters.get(i).equals(index)) {
				if(isInterrupted(interupters.get(i))) return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isCommandGroup(){
		return true;
	}
}
