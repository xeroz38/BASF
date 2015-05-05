package com.basf.catalog.service.core.structs;

import java.util.ArrayList;

public class MainCategory {
	
	public String isMultiLevel;
	public ArrayList<Category> category;

	public MainCategory() {
		category = new ArrayList<Category>();
	}

}