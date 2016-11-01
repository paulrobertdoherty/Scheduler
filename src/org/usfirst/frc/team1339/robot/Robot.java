
package org.usfirst.frc.team1339.robot;


import org.usfirst.frc.team1339.subsystems.Chassis;
import org.usfirst.frc.team1339.subsystems.Intake;
import org.usfirst.frc.team1339.subsystems.Shooter;
import org.usfirst.frc.team1339.utils.HardwareAdapter;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends IterativeRobot {
	public Autonomous a;
	public TeleOp t;
	public Disabled d;
	public static Chassis chassis;
	public static Intake intake;
	public static Shooter shooter;
	public static HardwareAdapter HardwareAdapter;
    public Robot() {
		a = new Autonomous();
    	t = new TeleOp();
		d = new Disabled();
    }

    public void robotInit() {
    		chassis = new Chassis();
    		intake = new Intake();
    		shooter = new Shooter();
    		HardwareAdapter = new HardwareAdapter();
    }

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the if-else structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomous() {
    	while(isAutonomous()){
    		a.autonomousPeriodic();
    	}
    }
    /**
     * 
     */
    @Override
    public void disabledInit(){
    	
    }
    /**
     * 
     */
    @Override
    public void disabledPeriodic(){
    	
    }
    /**
     * 
     */
    @Override
    public void teleopInit(){
    	t.init();
    }
    /**
     * 
     */
    @Override
    public void teleopPeriodic(){
        while (isEnabled()) {
        	t.teleOpPeriodic();
        }
    }
    /**
     * 
     */
    @Override
    public void autonomousInit(){
    	
    }
    /**
     * Runs code of autonomous.
     */
    @Override
    public void autonomousPeriodic(){
    	
    }

    /**
     * Runs during test mode
     */
    public void test() {
    	
    }
    
}
