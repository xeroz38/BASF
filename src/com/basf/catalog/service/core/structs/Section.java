package com.basf.catalog.service.core.structs;

import java.util.ArrayList;

public class Section {
	
	public String name;
	public ArrayList<SectionItem> secItems;

	public Section() {
		secItems = new ArrayList<SectionItem>();
	}
}