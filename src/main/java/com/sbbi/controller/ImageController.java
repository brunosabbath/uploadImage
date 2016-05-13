package com.sbbi.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sbbi.engine.ExtractFeature;


@RestController
@RequestMapping("/image")
public class ImageController {
	
	private final String PATH = "/home/bsilva/Documents/workspace-sts/uploadImage/imgs/";

	
	@RequestMapping(method = RequestMethod.POST)
	public void upload(@RequestParam("file") MultipartFile file){
		
		try {
			
			String imgPath = buildPath(file);
			file.transferTo(new java.io.File(imgPath));
			
			ExtractFeature extractFeature = new ExtractFeature(System.currentTimeMillis());
			double[] featureVector = extractFeature.getFeatureVector(imgPath);
			
			
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private String buildPath(MultipartFile file) {
		return PATH + System.currentTimeMillis() + getExtension(file.getOriginalFilename());
	}


	private String getExtension(String fileName) {
		return fileName.substring(fileName.indexOf('.'),fileName.length());
	}
	
}
