package org.usfirst.frc.team1339.utils;

public class EncoderConversion {
	
	public EncoderConversion(){
	}
	
	public double encoderConversion(int clicks){
		double rotation = clicks/256;
		rotation *= 4.5;
		return rotation;
	}
	
	public double getDistanceInches(int clicks){
		double rotation = clicks/256;
		rotation *= 4.5;
		double distance = rotation * 6 * Math.PI;
		return distance;
	}
	
	public double getDistanceFeet(int clicks){
		double feet = getDistanceInches(clicks)/12;
		return feet;
	}
	
	public static int getClicksInches(double distance){
		double rotation = distance /(6*Math.PI);
		rotation /= (16/38);
		double clicks = rotation * 256;
		//return (int) Math.round(clicks);
		return (int)clicks;
	}
}
