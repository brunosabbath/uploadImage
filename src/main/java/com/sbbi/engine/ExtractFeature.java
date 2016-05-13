package com.sbbi.engine;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

public class ExtractFeature {

	private final int FEATURES_SIZE = 180856; 
	private final String PATH = "/home/bsilva/Desktop/sbbi/foodEngine/";
	private final String ENGINE_PATH = PATH + "engine.py";
	private long currentTimeMillis;
	
	public ExtractFeature(long currentTimeMillis) {
		this.currentTimeMillis = currentTimeMillis;
	}

	public double[] getFeatureVector(String photoPath) {

		double features[] = new double[FEATURES_SIZE];
		
		try {
			
			BufferedReader br = null;
			
			String command = "python " + ENGINE_PATH + " " + photoPath + " " + currentTimeMillis;
			System.out.println(command);
			
			//execute process
			Process exec = Runtime.getRuntime().exec(command);
			exec.waitFor();
			
			String sCurrentLine;

			//read features from file
			br = new BufferedReader(new FileReader(currentTimeMillis + ".txt"));
			
			int i = 0;
			
			while ((sCurrentLine = br.readLine()) != null) {
				features[i] = Double.parseDouble(sCurrentLine);
				i++;
			}
			
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return features;
	}
}