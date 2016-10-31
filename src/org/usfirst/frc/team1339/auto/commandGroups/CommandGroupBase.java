package org.usfirst.frc.team1339.auto.commandGroups;

import java.util.ArrayList;

import org.usfirst.frc.team1339.commands.CommandBase;
import org.usfirst.frc.team1339.subsystems.SubsystemBase;

public abstract class CommandGroupBase extends CommandBase{

	private ArrayList<CommandBase> commands;
	private ArrayList<SubsystemBase> subsystems;
	private ArrayList<Boolean> parallels;
	private ArrayList<Integer> interupters;
	private boolean isFinished = false;
	
	private int index = 0;
	
	public void init(){
		
	}
	
	public void execute() {
		// TODO Auto-generated method stub
		/*
		if (commands.size() == subsystems.size() && 
				commands.size() == parallels.size() &&
				interupters.size() <= commands.size()){
			for(int i = 0; i <= index; i++){
				if(parallels.get(i)) index++;
				if (!commands.get(i).isFinished() && !isInterruptIndex(i)){
					if(!commands.get(i).isInitialized()){
						commands.get(i).init();
						commands.get(i).setInitialized();
					}
					else{
						commands.get(i).execute();
					}
				}
				else{
					commands.get(i).end();
					if (!parallels.get(i)) index++;
				}
			}
		}
		*/
		
		
		
		
		
		if (commands.size() == subsystems.size() && 
				commands.size() == parallels.size() &&
				interupters.size() <= commands.size()){
			if(index == 0){
				subsystems.get(index).schedule(commands.get(index));
				index++;
			}
			else if(parallels.get(index - 1).equals(true)){
				if(index < subsystems.size()){
					subsystems.get(index).schedule(commands.get(index));
					index++;
				}
				else {
					isFinished = true;
				}
			}
			else if(isInterruptIndex(index - 1)){
				if(index < subsystems.size()){
					subsystems.get(index).schedule(commands.get(index));
					index++;
				}
				else {
					isFinished = true;
				}
			}
			else if(commands.get(index - 1).isFinished()){
				if(index < subsystems.size()){
					subsystems.get(index).schedule(commands.get(index));
					index++;
				}
				else {
					isFinished = true;
				}
			}
		}
		
	}

	public boolean isFinished() {
		// TODO Auto-generated method stub
		return isFinished;
	}
	
	public void end() {
		// TODO Auto-generated method stub
	}
	
	public void interrupted(){
		
	}
	
	protected void addSequential(CommandBase command, SubsystemBase subsystem){
		commands.add(command);
		subsystems.add(subsystem);
		parallels.add(false);
	}
	
	protected void addParallel(CommandBase command, SubsystemBase subsystem){
		commands.add(command);
		subsystems.add(subsystem);
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
