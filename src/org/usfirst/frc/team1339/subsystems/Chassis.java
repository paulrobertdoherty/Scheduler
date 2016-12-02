package org.usfirst.frc.team1339.subsystems;

import org.usfirst.frc.team1339.base.SubsystemBase;
import org.usfirst.frc.team1339.commands.ArcadeDrive;
import org.usfirst.frc.team1339.robot.Robot;
import org.usfirst.frc.team1339.utils.Constants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Chassis extends SubsystemBase{
	

	public static CANTalon leftMotorOne = new CANTalon(Constants.kLeftMotorOne);
	public static CANTalon leftMotorTwo = new CANTalon(Constants.kLeftMotorTwo);
	public static CANTalon rightMotorOne = new CANTalon(Constants.kRightMotorOne);
	public static CANTalon rightMotorTwo = new CANTalon(Constants.kRightMotorTwo);
	
	double leftLastSpeed, rightLastSpeed;
	double rightSpeed, leftSpeed;
	double rate = 0.085;
	
	public Chassis(){
		setDefaultCommand(new ArcadeDrive());
	}
	
	
	public void driveWithJoystick(double throttle, double turn){
       	double right = throttle;
    	double left = throttle;
    	double turningThrottleScale;
    	
    	if (java.lang.Math.abs(throttle) > 0.1) {
            turningThrottleScale = java.lang.Math.abs(throttle);
        }
        else{
        	turningThrottleScale = 0.75;
        }
    	turningThrottleScale *= 1.5;
    	
    	right += turn * turningThrottleScale;  
        left -= turn * turningThrottleScale;
        
        if(Math.abs(right) <= 0.05)
        	right = 0;
        if(Math.abs(left) <= 0.05)
        	left = 0;
        
        if(left >= leftLastSpeed + rate)
        	leftSpeed = leftLastSpeed + rate;
        else if (left <= leftLastSpeed - rate)
        	leftSpeed = leftLastSpeed - rate;
        else
        	leftSpeed = left;
        
        if(right >= rightLastSpeed + rate)
        	rightSpeed = rightLastSpeed + rate;
        else if (right <= rightLastSpeed - rate)
        	rightSpeed = rightLastSpeed - rate;
        else
        	rightSpeed = right;
        
        
        setMotorValues(leftSpeed, rightSpeed);
        
        leftLastSpeed = leftSpeed;
        rightLastSpeed = rightSpeed;
        //Arcade Drive with Throttle
    }
    
    public void setMotorValues(double left, double right){
    	leftMotorOne.set(left);
    	leftMotorTwo.set(-left);
    	rightMotorOne.set(right);
    	rightMotorTwo.set(-right);
    	//
    }
    public void tankDrive(double left, double right){
    	setMotorValues(left, right);
    }
    
    public void PIDDriveEncoder(){
    	//double enc = (HardwareAdapter.getRightDriveEnc() + HardwareAdapter.getLeftDriveEnc())/2;
    	double rightSpeed = Robot.HardwareAdapter.RightDriveEncoderPID.calculate(Robot.HardwareAdapter.getRightDriveEnc());
    	double leftSpeed = Robot.HardwareAdapter.LeftDriveEncoderPID.calculate(Robot.HardwareAdapter.getLeftDriveEnc());
    	double gyroOutput = Robot.HardwareAdapter.GyroPID.calculate(Robot.HardwareAdapter.kSpartanGyro.getAngle());
    	rightSpeed -= gyroOutput;
    	leftSpeed += gyroOutput;
    	rightSpeed *= 0.5;
    	leftSpeed *= 0.5;
    	if(leftSpeed > 0.5){
    		leftSpeed = 0.5;
    	}
    	if(rightSpeed > 0.5){
    		rightSpeed = 0.5;
    	}
    	if(leftSpeed < -0.5){
    		leftSpeed = -0.5;
    	}
    	if(rightSpeed < -0.5){
    		rightSpeed = -0.5;
    	}
    	
    	SmartDashboard.putNumber("Right PID Output", rightSpeed);
    	SmartDashboard.putNumber("Left PID Output", -leftSpeed);
    	setMotorValues(-leftSpeed, rightSpeed);
    }
    
    public void motionProfile(){
    	double speed = Robot.HardwareAdapter.ChassisMP.calculate();
    	System.out.println(speed);
    	SmartDashboard.putNumber("MP output", speed);
    	//setMotorValues(speed, speed);
    }
	
}
