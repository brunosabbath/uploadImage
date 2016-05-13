package com.sbbi.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

	private String name;
	private List<MenuItem> menuItems;
	
	public Restaurant(){
		menuItems = new ArrayList<MenuItem>();
	}

	public String getName() {
		return name;
	}

	public Restaurant setName(String name) {
		this.name = name;
		return this;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}
	
	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public Restaurant addMenuItems(MenuItem menuItem){
		menuItems.add(menuItem);
		return this;
	}
	
}
