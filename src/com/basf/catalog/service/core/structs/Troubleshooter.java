package com.basf.catalog.service.core.structs;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Troubleshooter implements Serializable {
	
	public ArrayList<Troubleshooter> troubleshooter;
	
	public String file;
	public String name;
	
	@Override
	public boolean equals(Object o){
		Troubleshooter ts = (Troubleshooter) o;
		
		return ts.file.equals(file);
	}
}
