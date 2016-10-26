package org.usfirst.frc.team1339.subsystems;

import org.usfirst.frc.team1339.commands.DriveIntake;
import org.usfirst.frc.team1339.robot.Constants;

import edu.wpi.first.wpilibj.CANTalon;

public class Intake extends SubsystemBase{

	public static CANTalon axleMotor = new CANTalon(Constants.kAxleMotor);
	
	public Intake(){
		setDefaultCommand(new DriveIntake());
	}
	
	public void intake(double speed) {
		axleMotor.set(speed);
    }
}
