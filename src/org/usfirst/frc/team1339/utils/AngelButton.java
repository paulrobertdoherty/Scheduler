package org.usfirst.frc.team1339.utils;

import org.usfirst.frc.team1339.base.CommandBase;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class AngelButton extends JoystickButton{
	
	private boolean isPressed = false, isHeld = false, isPressedToggle = false, isFirstCommand = false;
	
	public AngelButton(Joystick stick, int port){
		super(stick, port);
	}
	
	public void whenPressed(CommandBase command){
		if(!isPressed){
			if (this.get()){
				isPressed = true;
				Looper.getInstance().newCommand(command);
			}
		}
		if(isPressed && !this.get()){
			isPressed = false;
		}
	}
	
	public void whileHeld(CommandBase command){
		if(isHeld){
			if(!this.get()){
				isHeld = false;
				Looper.getInstance().endCommand(command);
				System.out.println("end");
			}
		}
		else {
			if(this.get()) {
				isHeld = true;
				System.out.println("schedule");
				Looper.getInstance().newCommand(command);
			}
		}
	}
	
	public void toggle(CommandBase firstCommand, CommandBase secondCommand){
		if(!isPressedToggle){
			if (this.get()){
				isPressedToggle = true;
				if(isFirstCommand){
					Looper.getInstance().endCommand(firstCommand);
					Looper.getInstance().newCommand(secondCommand);
					isFirstCommand = false;
				}
				else{
					Looper.getInstance().endCommand(secondCommand);
					Looper.getInstance().newCommand(firstCommand);
					isFirstCommand = true;
				}
			}
		}
		if(isPressedToggle && !this.get()){
			isPressedToggle = false;
		}
	}

}
