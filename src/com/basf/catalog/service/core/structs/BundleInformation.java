package com.basf.catalog.service.core.structs;

import android.net.Uri;

public interface BundleInformation {
	
	public String CurrentMenu	= "current_menu";
	public String File			= "file";
	public String Name			= "name";
	
	public String ListFile		= "list_file";
	public String ListItem		= "list_item";
	
	public String XmlUrl		= "xml_url";
	public String GroupText		= "group_text";
	public String ChildText		= "child_text";
	
	public String SectionName	= "section_name";
	public String DataFile		= "data_file";
	public String DataPosition	= "data_position";
	public String DataName		= "data_name";
	public String DataPolymer	= "data_polymer";
	public String DataKeypoint	= "data_keypoint";
	public String DataKeypoint2 = "data_keypoint2";
	
	public String QuizName		= "quiz_name";
	public String QuizFile		= "quiz_file";
	
	public interface BroadcastInformation{
		
		public String IntentName= "intent_name";
		public String IndexMenu = "index_menu";
	}
	
	public interface FavoriteColumns
	{
		public final Uri CONTENT_URI = Uri.parse("content://" + "com.basf.catalog").buildUpon().appendPath("favorite").build();
		
		public final String[] QUERY_SHORT = {
				
            FavoriteColumns.NAME,
            FavoriteColumns.POLYMER,
            FavoriteColumns.XML_PATH
		};

		public String ID		= "id";
		public String NAME		= "name";
		public String POLYMER	= "polymer";
		public String XML_PATH	= "xml_path";
	}
}
