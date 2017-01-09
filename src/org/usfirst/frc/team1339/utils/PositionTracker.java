package org.usfirst.frc.team1339.utils;

public class PositionTracker {
	
	private double xPos = 0;
	private double yPos = 0;
	private double lastDistance = 0;


	public PositionTracker(){
	}
	
	public void setPosistions(double newAngle, double input_distance){
		double angle = newAngle;
		double distance = input_distance - lastDistance;
		lastDistance = distance;
		
		xPos += Math.sin(Math.toRadians(angle)) * distance;
		yPos += Math.cos(Math.toRadians(angle)) * distance;
	}
	
	public double getXPos(){
		return xPos;
	}
	
	public double getYPos(){
		return yPos;
	}
}
