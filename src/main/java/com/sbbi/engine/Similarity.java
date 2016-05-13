package com.sbbi.engine;

public class Similarity {

	private final int FEATURES_SIZE = 180856;
	
	public double calculate(double[] q, double[] p){

		double result = 0;
		
		for(int i = 0; i < FEATURES_SIZE; i++){
			result = Math.pow((q[i]-p[i]), 2) + result;
		}
		
		return Math.sqrt(result);
	}
	
}