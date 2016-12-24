package org.usfirst.frc.team1339.base;

import java.util.ArrayList;

import org.usfirst.frc.team1339.utils.AngelButton;

import edu.wpi.first.wpilibj.Timer;

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
	private ArrayList<Interrupter> interrupters = new ArrayList<Interrupter>();
	
	private int index = -1;
	private int numCmdsFinished = 0;
	private int lineCounter = 0;
	
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
					command.setInitialized();
				}
				command.execute();
				command.resetTime();
				if(command.isFinished() || isInterrupted(cmd.line)){
					command.cancel();
					if(!cmd.isParallel)
						index++;
					runningCommands.remove(cmd);
					numCmdsFinished++;
				}
			}
		}
	}

	public boolean isFinished() {
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
		commands.add(new CommandState(command, false, lineCounter));
		lineCounter++;
	}
	
	protected void addParallel(CommandBase command){
		for(SubsystemBase subsys : command.getRequirements()){
			requires(subsys);
		}
		commands.add(new CommandState(command, true, lineCounter));
		lineCounter++;
	}
	
	protected void addInterrupter(int line, AngelButton button, boolean value){
		interrupters.add(new Interrupter(line, button, value));
	}
	
	protected void addInterrupter(int line, AngelButton button){
		addInterrupter(line, button, true);
	}
	
	private boolean isInterrupted(int line){
		for(int i = 0; i < interrupters.size(); i++){
			if(interrupters.get(i).line == line) {
				return interrupters.get(i).isInterrupted();
			}
		}
		return false;
	}
	
	private static class Interrupter{
		int line;
		AngelButton button;
		boolean value;
		
		Interrupter(int line, AngelButton button, boolean value){
			this.line = line;
			this.button = button;
			this.value = value;
		}
		
		public boolean isInterrupted(){
			return button.get() == value;
		}
	}
	
	private static class CommandState{
		CommandBase command;
		boolean isParallel;
		int line;
		
		CommandState(CommandBase command, boolean isParallel, int line){
			this.command = command;
			this.isParallel = isParallel;
			this.line = line;
		}
	}
}
