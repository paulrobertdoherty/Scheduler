package org.usfirst.frc.team1339.robot;

import org.usfirst.frc.team1339.commands.TankDrive;
import org.usfirst.frc.team1339.subsystems.SubsystemBase;
import org.usfirst.frc.team1339.utils.SynchronousPID;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class HardwareAdapter {

	//Encoders
	public static Encoder kRightDriveEncoder = new Encoder(
			Constants.kRightDriveAEncoder , Constants.kRightDriveBEncoder);
	public static Encoder kLeftDriveEncoder = new Encoder(
			Constants.kLeftDriveAEncoder , Constants.kLeftDriveBEncoder);
	public static ADXRS450_Gyro kSpartanGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
	//Spike
	//Joysticks
	private Joystick xboxStick = new Joystick(Constants.xboxPort);
	//Razer Joystick Buttons
	private JoystickButton AButton = new JoystickButton(xboxStick , 1);
	private JoystickButton BButton = new JoystickButton(xboxStick , 2);
	private JoystickButton XButton = new JoystickButton(xboxStick , 3);
	private JoystickButton YButton = new JoystickButton(xboxStick , 4);
	private JoystickButton leftBumper = new JoystickButton(xboxStick , 5);
	private JoystickButton rightBumper = new JoystickButton(xboxStick , 6);
	private JoystickButton leftStickButton = new JoystickButton(xboxStick , 9);
	private JoystickButton rightStickButton = new JoystickButton(xboxStick , 10);
	//PID Loops
	public static SynchronousPID LeftDriveEncoderPID = new SynchronousPID(
			Constants.kDriveKp , Constants.kDriveKi , Constants.kDriveKd);
	public static SynchronousPID ShortLeftDriveEncoderPID = new SynchronousPID(
			Constants.kShortDriveKp , Constants.kShortDriveKi , Constants.kShortDriveKd);
	public static SynchronousPID RightDriveEncoderPID = new SynchronousPID(
			Constants.kDriveKp , Constants.kDriveKi , Constants.kDriveKd);
	public static SynchronousPID ShortRightDriveEncoderPID = new SynchronousPID(
			Constants.kShortDriveKp , Constants.kShortDriveKi , Constants.kShortDriveKd);
	public static SynchronousPID TurnGyroPID = new SynchronousPID(
			Constants.kTurnKp , Constants.kTurnKi , Constants.kTurnKd);
	public static SynchronousPID ArmPID = new SynchronousPID(
			Constants.kArmKp , Constants.kArmKi , Constants.kArmKd);
	public static SynchronousPID ShooterPID = new SynchronousPID(
			Constants.kShooterKp , Constants.kShooterKi , Constants.kShooterKd);
	public static SynchronousPID AccelPID = new SynchronousPID(
			Constants.kAccelKp, Constants.kAccelKi, Constants.kAccelKd);
	public static SynchronousPID JerkPID = new SynchronousPID(
			Constants.kJerkKp, Constants.kJerkKi, Constants.kJerkKd);
	public static SynchronousPID GyroPID = new SynchronousPID(
			Constants.kGyroKp , Constants.kGyroKi , Constants.kGyroKd);
	public static SynchronousPID MotionProfile = new SynchronousPID(
			Constants.k_mp_Kp , Constants.k_mp_Ki , Constants.k_mp_Kd);

	public HardwareAdapter(){
	}
	
	public void checkTriggers(SubsystemBase subsystem){
		subsystem.whenPressed(AButton, new TankDrive());
	}
	
	public Joystick getXboxStick(){
		return xboxStick;
	}
	public boolean getAButton(){
		return AButton.get();
	}
	public boolean getBButton(){
		return BButton.get();
	}
	public boolean getXButton(){
		return XButton.get();
	}
	public boolean getYButton(){
		return YButton.get();
	}
	public boolean getLeftBumper(){
		return leftBumper.get();
	}
	public boolean getRightBumper(){
		return rightBumper.get();
	}
	public boolean getLeftStickButton(){
		return leftStickButton.get();
	}
	public boolean getRightStickButton(){
		return rightStickButton.get();
	}
	public static double getLeftDriveEnc(){
		return (kLeftDriveEncoder.get());
	}
	public static double getRightDriveEnc(){
		return (kRightDriveEncoder.get() * -1);
	}
	
}
