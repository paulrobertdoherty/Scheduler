package org.usfirst.frc.team1339.utils;


import org.usfirst.frc.team1339.base.CommandBase;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The AngelButton class is a customized button class created for 
 * button manipulation. It extends the WPIlib {@link JoystickButton}, which allows
 * each button made to use the same traits as a {@link JoystickButton}. The main
 * purpose of this class is to recognize triggers that occur from the buttons.
 * There are multiple different methods we have for different types of triggers.
 * {@link HardwareAdapter} is where call these methods continuously with different buttons
 * and different commands. It then schedules commands in {@link Looper} when a certain
 * trigger is enacted.
 * 
 * @author Sam Korman
 * @author Sam Schwartz
 * @author Nate Howard
 * @see JoystickButton
 * @see HardwareAdapter
 * @see Looper
 *
 */

public class AngelButton extends JoystickButton{
	
	private boolean isPressed = false, isHeld = false, isPressedToggle = false, isFirstCommand = false;
	
	public AngelButton(Joystick stick, int port){
		super(stick, port);
	}
	
	/**
	 * This method runs a given command when a certain button is pressed.
	 * @param command
	 */
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
	
	/**
	 * this method runs a given command while a button is being held down
	 * @param command
	 */
	public void whileHeld(CommandBase command){
		if(isHeld){
			if(!this.get()){
				isHeld = false;
				Looper.getInstance().endCommand(command);
			}
		}
		else {
			if(this.get()) {
				isHeld = true;
				Looper.getInstance().newCommand(command);
			}
		}
	}
	
	/**
	 * this method allows for a user to toggle between two different commands,
	 * with a single button
	 * @param firstCommand
	 * @param secondCommand
	 */
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
