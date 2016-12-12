package org.usfirst.frc.team1339.utils;

import org.usfirst.frc.team1339.base.CommandBase;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Triggers {
	
	private boolean commandGroupInit = false;
	private boolean isPressed = false;
	
	protected void whenPressed(JoystickButton button, CommandBase command){
		
		if(!isPressed){
			if (button.get()){
				isPressed = true;
				//System.out.println("button is pressed");
				if(command.isCommandGroup()){
					commandGroupInit = true;
				}
				else Looper.getInstance().newCommand(command);
			}
			if(commandGroupInit){
				command.execute();
			}
		}
		if(isPressed && !button.get()){
			//System.out.println("button is relesaed");
			isPressed = false;
		}
	}
	
	public static void schedule(CommandBase command){
		Looper.getInstance().newCommand(command);
	}
}
