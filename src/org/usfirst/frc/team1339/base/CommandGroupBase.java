package org.usfirst.frc.team1339.base;

import java.util.ArrayList;

import org.usfirst.frc.team1339.utils.AngelButton;
import org.usfirst.frc.team1339.utils.Looper;


import edu.wpi.first.wpilibj.Timer;

/**
 * This is the base class for command groups, so all command groups should extend this class.
 * It handles all the parallel and sequential commands, as well as all of the interrupters.
 * {@link Looper} sees this as just a regular command, and this class handles all of the
 * running of the commands it consists of internally.
 * 
 * @author Sam Schwartz
 * @author Nate Howard
 * @author Sam Korman
 * 
 * @see ArrayList
 * @see CommandBase
 * @see Looper
 */

public abstract class CommandGroupBase extends CommandBase{

	private ArrayList<CommandState> commands = new ArrayList<CommandState>();
	private ArrayList<CommandState> runningCommands = new ArrayList<CommandState>();
	private ArrayList<Interrupter> interrupters = new ArrayList<Interrupter>();
	
	private int index = 0;
	private int numCmdsFinished = 0;
	private int lineCounter = 0;
	
	protected void init(){}
	
	/**
	 * This method gets called every time a command group runs.
	 * It schedules commands based on sequentials and parallels
	 * created in the actual command group by adding them to
	 * {@link #runningCommands}. After that the {@link #run()}
	 * method handles the actual running of the commands.
	 * <br/>
	 * It is similar to the {@link Looper#newCommand(CommandBase)}
	 * method in {@link Looper}, except that it automatically schedules the commands.
	 */
	public void execute() {
		if(index < commands.size()){
			CommandState currentCmdState = commands.get(index);
			if(!runningCommands.contains(currentCmdState)){
				ArrayList<SubsystemBase> reqs = currentCmdState.command.getRequirements();
				for(int runCmds = 0; runCmds < runningCommands.size(); runCmds++){
					CommandState cmd = runningCommands.get(runCmds);
					for(int subs = 0; subs < cmd.command.getRequirements().size(); subs++){
						SubsystemBase subsys = cmd.command.getRequirements().get(subs);
						for(int reqNum = 0; reqNum < reqs.size(); reqNum++){
							SubsystemBase req = reqs.get(reqNum);
							if(req.equals(subsys)){
								cmd.command.cancel();
								runningCommands.remove(cmd);
							}
						}
					}
				}
				runningCommands.add(currentCmdState);
				if(currentCmdState.isParallel) index++;
			}
		}
		run();
	}
	
	/**
	 * This method actually runs the commands in a command group.
	 * It takes the commands in {@link #runningCommands} and handles
	 * the initialization, executing, and ending of those commands.
	 * It is modeled after the code in {@link Looper}.
	 * 
	 * @see CommandBase
	 */
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
	
	protected void end() {}
	
	protected void interrupted(){
		for(int x = 0; x < runningCommands.size(); x++){
			runningCommands.get(x).command.cancel();
		}
	}
	
	/**
	 * This method schedules the command you give it to be run sequentially,
	 * so other commands after this one will wait to be run until this command
	 * is finished.
	 * 
	 * @param command The command to be scheduled sequentially
	 */
	protected void addSequential(CommandBase command){
		for(SubsystemBase subsys : command.getRequirements()){
			requires(subsys);
		}
		commands.add(new CommandState(command, false, lineCounter));
		lineCounter++;
	}
	
	/**
	 * This method schedules the command you give it to be run in parallel,
	 * so other commands will start
	 * 
	 * @param command
	 */
	protected void addParallel(CommandBase command){
		for(SubsystemBase subsys : command.getRequirements()){
			requires(subsys);
		}
		commands.add(new CommandState(command, true, lineCounter));
		lineCounter++;
	}
	
	/**
	 * This method is the same as {@link #addInterrupter(int, AngelButton)}, except that
	 * you can specify the value you want the button to be for the command to finish.
	 * 
	 * @param line The line of the command to interrupt
	 * @param button The button that should interrupt the command
	 * @param value The value that the button should be to end the command
	 */
	protected void addInterrupter(int line, AngelButton button, boolean value){
		interrupters.add(new Interrupter(line, button, value));
	}
	
	/**
	 * This method adds an interrupter on the line that you specify. If the button
	 * that you specify matches returns true, the command will end, and the command
	 * group will continue as it is set up.
	 * 
	 * @param line The line of the command to interrupt
	 * @param button The button that should interrupt the command
	 */
	protected void addInterrupter(int line, AngelButton button){
		addInterrupter(line, button, true);
	}
	
	/**
	 * Use this method to check if the command on a given line should be interrupted.
	 * 
	 * @param line The line of the command to check
	 * @return If the command should be interrupted
	 */
	private boolean isInterrupted(int line){
		for(int i = 0; i < interrupters.size(); i++){
			if(interrupters.get(i).line == line) {
				return interrupters.get(i).isInterrupted();
			}
		}
		return false;
	}
	
	/**
	 * This is a class that contains all of the information needed to check if
	 * a command should be interrupted.
	 * 
	 * @author Nate Howard
	 * @author Sam Korman
	 * @author Sam Shwartz
	 * 
	 * @see AngelButton
	 */
	private static class Interrupter{
		/**
		 * The line of the command that should be interrupted.
		 */
		int line;
		
		/**
		 * The button to check to see if the command should be interrupted.
		 */
		AngelButton button;
		
		/**
		 * The value that the button needs to be at to interupt the command.
		 */
		boolean value;
		
		/**
		 * The constructor for {@link Interrupter}.
		 * 
		 * @param line The line of the command to interrupt
		 * @param button The button that should interrupt the command
		 * @param value The value that the button should be to end the command
		 */
		Interrupter(int line, AngelButton button, boolean value){
			this.line = line;
			this.button = button;
			this.value = value;
		}
		
		/**
		 * This method checks if the button value is equal to the given value, so if
		 * the command should be interrupted.
		 * 
		 * @return Whether or not the command should be interrupted.
		 */
		boolean isInterrupted(){
			return button.get() == value;
		}
	}
	
	/**
	 * This is a class that contains the command, as well as the
	 * state (sequential or parallel) and the line of that command.
	 * 
	 * @author Nate Howard
	 * @author Sam Korman
	 * @author Sam Shwartz
	 * 
	 * @see CommandBase
	 */
	private static class CommandState{
		/**
		 * The command that this {@link CommandState} represents.
		 */
		CommandBase command;
		
		/**
		 * The state of the command, so whether or not the command is parallel.
		 */
		boolean isParallel;
		
		/**
		 * The line of the command
		 */
		int line;
		
		/**
		 * The constructor for {@link CommandState}.
		 * 
		 * @param command The command for this {@link CommandState} to represent
		 * @param isParallel Whether or not the command is parallel
		 * @param line The line of this command
		 */
		CommandState(CommandBase command, boolean isParallel, int line){
			this.command = command;
			this.isParallel = isParallel;
			this.line = line;
		}
	}
}
