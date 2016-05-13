package com.sbbi.model;

public class MenuItem {

	private final int FEATURES_SIZE = 180856;
	
	private String name;
	private double[] features;
	
	public MenuItem(){
		features = new double[FEATURES_SIZE];
	}

	public String getName() {
		return name;
	}

	public MenuItem setName(String name) {
		this.name = name;
		return this;
	}

	public double[] getFeatures() {
		return features;
	}

	public void setFeatures(double[] features) {
		this.features = features;
	}
	
}
