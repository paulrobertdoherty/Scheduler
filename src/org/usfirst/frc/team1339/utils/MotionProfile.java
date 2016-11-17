package org.usfirst.frc.team1339.utils;

public class MotionProfile {
	
	public double Kp, Ki, Kd, Ka, Kv, goal, cruiseVel, maxAcc, cruiseVelScaleFactor;
	public Segment initialSegment = new Segment(0, 0, 0);
	public Segment currentSegment;
	public Segment nextSegment;
	
	public MotionProfile(){
	}
	
	public MotionProfile(double Kp, double Ki, double Kd, double Ka,
			double Kv){
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		this.Ka = Ka;
		this.Kv = Kv;
	}

	public static class Segment{
		public double pos, vel, acc;
		
		public Segment(double pos, double vel, double acc){
			this.pos = pos;
			this.vel = vel;
			this.acc = acc;
		}
	}
	
	public void configureNewProfile(double distance){
		this.goal = distance;
		this.cruiseVel = getCruiseVel(this.goal);
		this.maxAcc = Constants.maxAcceleration;
		this.currentSegment = initialSegment;
		this.cruiseVelScaleFactor = Constants.motionProfileSlowScaleFactor;
	}
	
	public void configureNewProfile(double Kp, double Ki, double Kd, double Ka,
			double Kv, double distance){
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		this.Ka = Ka;
		this.Kv = Kv;
		this.goal = distance;
		this.cruiseVel = getCruiseVel(this.goal);
		this.maxAcc = Constants.maxAcceleration;
		this.currentSegment = initialSegment;
		this.cruiseVelScaleFactor = Constants.motionProfileSlowScaleFactor;
	}
	
	public void setProfileConstants(double Kp, double Ki, double Kd, double Ka,
			double Kv){
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		this.Ka = Ka;
		this.Kv = Kv;
	}
	
	private double getCruiseVel(double distance){
		double halfDist = distance / 2;
		double maxVelOverHalfDistance = Math.sqrt(2 * halfDist * maxAcc);
		return Math.min(maxVelOverHalfDistance * cruiseVelScaleFactor, Constants.maxCruiseSpeed);
	}
}
