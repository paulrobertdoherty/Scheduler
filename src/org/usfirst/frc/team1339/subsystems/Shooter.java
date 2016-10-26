package org.usfirst.frc.team1339.subsystems;

import org.usfirst.frc.team1339.commands.DriveShooter;
import org.usfirst.frc.team1339.utils.Constants;

import edu.wpi.first.wpilibj.CANTalon;

public class Shooter extends SubsystemBase{
	
	private CANTalon shooterMotorOne = new CANTalon(Constants.kShooterMotorOne);
	private CANTalon shooterMotorTwo = new CANTalon(Constants.kShooterMotorTwo);
	
	public Shooter(){
		setDefaultCommand(new DriveShooter());
	}
	
	public void shoot(double speed){
		shooterMotorOne.set(speed);
		shooterMotorTwo.set(speed);
	}
	
}
