package org.usfirst.frc.team1339.base;

import java.util.ArrayList;

import org.usfirst.frc.team1339.utils.Looper;
import org.usfirst.frc.team1339.utils.Triggers;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * @author Sam Schwartz
 * @author Nate Howard
 * @author Sam Korman
 * <br>
 * @see ArrayList
 */

public abstract class CommandGroupBase extends CommandBase{

	private ArrayList<CommandState> commands = new ArrayList<CommandState>();
	private ArrayList<CommandState> runningCommands = new ArrayList<CommandState>();
	private ArrayList<Integer> interupters = new ArrayList<Integer>();
	private boolean isFinished = false;
	
	private int index = -1;
	private int numCmdsFinished = 0;
	
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
		CommandState currentCmdState = null;
		if(index == -1){
			index++;
			currentCmdState = commands.get(index);
			runningCommands.add(currentCmdState);
			if(currentCmdState.isParallel) index++;
		}
		if(index < commands.size()){
			currentCmdState = commands.get(index);
			if(!runningCommands.contains(currentCmdState)){
				runningCommands.add(currentCmdState);
				if(currentCmdState.isParallel) index++;
			}
		}
		run();
	}
	
	private void run(){
		for(int x = 0; x < runningCommands.size(); x++){
			CommandState cmd = runningCommands.get(x);
			CommandBase command = cmd.command;
			if(Timer.getFPGATimestamp() > command.getRunSpeed() + command.getLastTime()){
				if(!command.isInitialized()){
					command.init();
					command.setInitialized();
				}
				command.execute();
				command.resetTime();
				System.out.println(command.isFinished());
				if(command.isFinished()){
					command.end();
					if(!cmd.isParallel)
						index++;
					runningCommands.remove(cmd);
					numCmdsFinished++;
				}
			}
		}
	}

	public boolean isFinished() {
		SmartDashboard.putNumber("Size", commands.size());
		SmartDashboard.putNumber("Index", index);
		return numCmdsFinished >= commands.size();
	}
	
	public void end() {

	}
	
	public void interrupted(){
		
	}
	
	protected void addSequential(CommandBase command){
		for(SubsystemBase subsys : command.getRequirements()){
			requires(subsys);
		}
		commands.add(new CommandState(command, false));
	}
	
	protected void addParallel(CommandBase command){
		for(SubsystemBase subsys : command.getRequirements()){
			requires(subsys);
		}
		commands.add(new CommandState(command, true));
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
	
	private static class CommandState{
		CommandBase command;
		boolean isParallel;
		
		CommandState(CommandBase command, boolean isParallel){
			this.command = command;
			this.isParallel = isParallel;
		}
	}
}
