package org.usfirst.frc.team1339.base;

import java.util.ArrayList;

import org.usfirst.frc.team1339.utils.Looper;

import edu.wpi.first.wpilibj.Timer;

/**
 * 
 * {@link CommandBase} is the base class for all our commands. All commands should extend this
 * class. It contains all the abstract classes needed in commands, and it also has some
 * background functions necessary for {@link Looper} to run the commands.
 * <p>
 * {@link CommandGroupBase} also extends this class as command groups and commands are
 * seen as the same to {@link Looper}.
 * </p>
 * 
 * @author Sam Schwartz
 * @author Nate Howard
 * @author Sam Korman
 * 
 * @see Looper
 */

public abstract class CommandBase {
	
	/**
	 * This is the variable that contains the timeout (in seconds)
	 */
	private double m_time = -1;
	
	/**
	 * This contains the time (in seconds) when the command was initialized
	 */
	private double startTime;
	
	/**
	 * This contains the delay that should be given between each run of the command
	 */
	private double m_speed = 0;
	
	/**
	 * This contains the last time that the command was run (in seconds)
	 */
	private double runTime = 0;
	
	/**
	 * This contains the name of the command
	 */
	private String m_name = "";
	
	/**
	 * This contains whether or not the command has been initialized yet.
	 */
	private boolean initialized = false;
	
	/**
	 * A list that contains the requirements of this command
	 */
	private ArrayList<SubsystemBase> requirements = new ArrayList<SubsystemBase>();
	
	protected CommandBase(){
		this.m_name = this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.') + 1);
	}
	
	/**
	 * The first method that will run when a command is scheduled.
	 */
	protected abstract void init();
	
	/**
	 * This method will run over and over again until the {@link #isFinished()} method returns true.
	 * It will run at least once every time.
	 */
	public abstract void execute();
	
	/**
	 * This method returns whether or not the command is finished and should not be run again.
	 * 
	 * @return Whether or not the command is finished
	 */
	public abstract boolean isFinished();
	
	/**
	 * This command runs when {@link #isFinished()} returns true, when the command is done.
	 */
	protected abstract void end();
	
	/**
	 * This command runs when another command is scheduled that has a similar required subsystem to this command,
	 * and this one is canceled before {@link #isFinished()} returns true.
	 */
	protected abstract void interrupted();
	
	/**
	 * Call this method to end the command. It will run either the end method or the
	 * interrupted method depending on if the command is finished yet.
	 */
	public void cancel(){
		if(isFinished()) end();
		else interrupted();
	}
	
	/**
	 * This function returns the name of the command.
	 * 
	 * @return The name of the command
	 */
	public String getName(){
		return m_name;
	}
	
	/**
	 * Sets the delay (in seconds) between each time the command runs.
	 * 
	 * @param speed Seconds to delay between each run
	 */
	protected void setRunSpeed(double speed){
		m_speed = speed;
	}
	
	/**
	 * This method tells {@link Looper} which subsystems that this command uses. Always
	 * include this method in a command if it needs the use of a subsystem.
	 * 
	 * @param instance The subsystem that this command requires
	 */
	protected void requires(SubsystemBase instance){
		if(!requirements.contains(instance))
			requirements.add(instance);
	}
	
	/**
	 * This method sets the timeout of the command. Check to see if it has reached the timeout
	 * using {@link #isTimedOut()}. This is used to create a timed command.
	 * 
	 * @param time Time in seconds till {@link #isTimedOut()} returns true.
	 */
	protected void setTimeout(double time){
		m_time = Math.abs(time);
	}
	
	/**
	 * Use this method to check if the command has reached its timeout. This is commonly used
	 * in the isFinished() method of a command for a timed command.
	 * 
	 * @return Whether or not the command has reached its timeout
	 */
	protected boolean isTimedOut(){
		if (m_time < 0) return false;
		if (Timer.getFPGATimestamp() > startTime + m_time){
			return true;
		}
		return false;
	}
	
	/**
	 * Use this method to get a list of all of the subsystems a command requires.
	 * 
	 * @return A list of the requirements of a command
	 */
	public ArrayList<SubsystemBase> getRequirements(){
		return requirements;
	}
	
	/**
	 * This method resets the time since the command has last run. Use it immediately
	 * after you run the command.
	 * 
	 */
	public void resetTime(){
		runTime = Timer.getFPGATimestamp();
	}
	
	/**
	 * This method returns the last time a command was run. Use it to check if the command
	 * is scheduled to run again.
	 * 
	 * @return The last time the command was run
	 */
	public double getLastTime(){
		return runTime;
	}
	
	/**
	 * This returns whether the command has been initialized. Use it to see if you need to
	 * call {@link #setInitialized()}.
	 * 
	 * @return Whether or not the command has been initialized
	 */
	public boolean isInitialized(){
		return initialized;
	}
	
	/**
	 * This method registers the command as initialized and runs the {@link #init()} method
	 * of the command.
	 * 
	 */
	public void setInitialized(){
		startTime = Timer.getFPGATimestamp();
		init();
		initialized = true;
	}
	
	/**
	 * This method returns the delay that should be given between each run of this command.
	 * Use it to determine whether the command should be run again.
	 * 
	 * @return Seconds to be delayed between run times
	 */
	public double getRunSpeed(){
		return m_speed;
	}
}
