package org.usfirst.frc.team1339.utils;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.base.CommandGroupBase;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Triggers {
	
	private boolean commandGroupInit = false;
	
	protected void whenPressed(JoystickButton button, CommandBase command){
		if (button.get()){
			if(command.isCommandGroup()){
				commandGroupInit = true;
			}
			else Looper.getInstance().newCommand(command);
		}
		if(commandGroupInit){
			command.execute();
		}
	}
	
	public static void schedule(CommandBase command){
		Looper.getInstance().newCommand(command);
	}
}
