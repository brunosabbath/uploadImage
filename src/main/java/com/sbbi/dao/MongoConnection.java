package com.sbbi.dao;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.sbbi.model.MenuItem;
import com.sbbi.model.Restaurant;

public class MongoConnection {

	private final int FEATURES_SIZE = 180856;
	private Mongo mongo;
	private MongoClient client;
	private DB db;
	
	public MongoConnection(){
		mongo = new Mongo("localhost", 27017);
		client = new MongoClient( "localhost" , 27017 );
		db = client.getDB("foodTest");
	}
	
	
	//insert restaurant mongo
	public void insertRestaurant(Restaurant restaurant){
		
		DBCollection table = db.getCollection("foodInfo");
		BasicDBObject document = new BasicDBObject();
		document.put("restaurant", restaurant.getName());
		document.put("menuItems", restaurant.getMenuItems());
		table.insert(document);
		
		mongo.close();
	}

	//find restaurant by name
	public Restaurant getRestaurant(String name) {
		
		DBCollection table = db.getCollection("foodInfo");
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("restaurant", name);
		DBCursor cursor = table.find(whereQuery);
		
		Restaurant restaurant = new Restaurant();
		
		//find restaurant
		while(cursor.hasNext()) {
			
		    DBObject next = cursor.next();
		    
		    String restaurantName = (String) next.get("restaurant");

		    //restaurant's menu
		    BasicDBList originalArray = (BasicDBList) next.get("menuItems");
		    
		    List<MenuItem> menuItems = new ArrayList<MenuItem>();
		    
		    //iterate through the menu
		    for(int i = 0; i < originalArray.size(); i++){
		    	MenuItem menuItem = new MenuItem();
		    	
		    	//get menu item
		    	BasicDBObject obj = (BasicDBObject) originalArray.get(i);		    	
		    	menuItem.setName(obj.getString("item"));
		    	
		    	//get image features
		    	BasicDBList listFeatures = (BasicDBList) obj.get("features");
		    	
		    	double[] features = new double[FEATURES_SIZE];
		    	
		    	//loop through features and create array of features
		    	for(int j = 0; j < listFeatures.size(); j++){
		    		features[j] = Double.parseDouble(listFeatures.get(j).toString());
		    	}
		    	
		    	menuItem.setFeatures(features);
		    	menuItems.add(menuItem);
		    }
		    
		    restaurant.setMenuItems(menuItems);
		}
		
		return restaurant;
	}  

	public void insertMenuItem(MenuItem menuItem, String restaurantName) {
		
		DBCollection table = db.getCollection("foodInfo");
		
		BasicDBObject match = new BasicDBObject();
		match.put("restaurant", restaurantName);
		
		BasicDBObject menuInfo = new BasicDBObject();
		menuInfo.put("item", menuItem.getName());
		menuInfo.put("features", menuItem.getFeatures());
		
		BasicDBObject update = new BasicDBObject();
		update.put("$push", new BasicDBObject("menuItems", menuInfo));
		table.update(match, update);
		
		mongo.close();
	}

	public void deleteItemMenu(String restaurantName, String menuItemName) {
		
		DBCollection table = db.getCollection("foodInfo");
		
		BasicDBObject match = new BasicDBObject();
		match.put("restaurant", restaurantName);
		
		BasicDBObject item = new BasicDBObject();
		item.put("item", menuItemName);
		
		BasicDBObject update = new BasicDBObject();
		update.put("$pull", new BasicDBObject("menuItems", item));
		
		table.update(match, update);
		
	}
	
}