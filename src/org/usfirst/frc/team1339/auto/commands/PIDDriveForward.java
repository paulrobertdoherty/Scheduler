package org.usfirst.frc.team1339.auto.commands;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.robot.Robot;
import org.usfirst.frc.team1339.utils.Constants;
import org.usfirst.frc.team1339.utils.HardwareAdapter;

/**
 *
 */
public class PIDDriveForward extends CommandBase {

	private double m_clicks;
	private boolean initialized = false;
	
	
    public PIDDriveForward(double timeout, double clicks) {
    	m_clicks = clicks;
    	
    	setTimeout(timeout);
    }

	public void init() {
    	Robot.HardwareAdapter.GyroPID.setPID(Constants.kAutoGyroKp, Constants.kAutoGyroKi, Constants.kAutoGyroKd);
    	Robot.HardwareAdapter.RightDriveEncoderPID.setSetpoint(m_clicks+Robot.HardwareAdapter.getRightDriveEnc());
    	Robot.HardwareAdapter.LeftDriveEncoderPID.setSetpoint(m_clicks+Robot.HardwareAdapter.getLeftDriveEnc());
    	Robot.HardwareAdapter.GyroPID.setSetpoint(HardwareAdapter.kSpartanGyro.getAngle());
    	initialized = true;
	}
    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	Robot.chassis.PIDDriveEncoder();
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
    	if(!initialized) return false;
        return Robot.HardwareAdapter.RightDriveEncoderPID.onTarget(10)
        		|| Robot.HardwareAdapter.LeftDriveEncoderPID.onTarget(10)  || isTimedOut();
    }

    // Called once after isFinished returns true
    public void end() {
    	Robot.chassis.setMotorValues(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    public void interrupted() {
    	Robot.chassis.setMotorValues(0, 0);
    }

}
