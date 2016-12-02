package org.usfirst.frc.team1339.utils;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.base.CommandGroupBase;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Triggers {
	
	private boolean commandGroupInit;
	private CommandGroupBase commandGroup;
	
	protected void whenPressed(JoystickButton button, CommandBase command){
		if (button.get()){
			if(command.isCommandGroup()){
				commandGroupInit = true;
				commandGroup = (CommandGroupBase) command;
			}
			else schedule(command);
		}
		if(commandGroupInit){
			commandGroup.execute();
		}
	}
}
