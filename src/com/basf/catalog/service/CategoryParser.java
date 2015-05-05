package com.basf.catalog.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.basf.catalog.service.core.structs.Category;
import com.basf.catalog.service.core.structs.MainCategory;

public class CategoryParser
{
//	public static String[] extractMainCategoryToSingleLevelCategory(MainCategory mainCat) {
//		ArrayList<String> catList = new ArrayList<String>();
//		for(Category cat : mainCat.category){
//			catList.add(cat.text);
//		}
//		return catList.toArray(new String[0]);		
//	}
	
	public static MainCategory getMainCategoryList(InputStream in) throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setInput(in, "UTF-8");

		boolean parsing = true;
		String name;
		int eventType;

		MainCategory mainCat = new MainCategory();

		while (parsing)
		{
			eventType = parser.next();
			switch (eventType)
			{
			case XmlPullParser.START_TAG:
			{
				name = parser.getName();
				if ("categories".equals(name))
				{
					mainCat.isMultiLevel = parser.getAttributeValue("","multilevel");
				} else

					if ("category".equals(name))
					{
						Category category = new Category(); // category lv 1
						category.file = parser.getAttributeValue("","file");
						category.text = parser.getAttributeValue("","text");

						boolean subParsing = true;
						while (subParsing)
						{
							eventType = parser.next();
							switch (eventType)
							{
								case XmlPullParser.START_TAG:
								{
									name = parser.getName();
									if ("subcategory".equals(name)) 
									{
										if(category.category == null) 
											category.category = new ArrayList<Category>();
	
										Category sub_category = new Category(); //category lv 2
	
										sub_category.file = parser.getAttributeValue("","file");
										sub_category.text = parser.getAttributeValue("","text");
	
										category.category.add(sub_category);
									}
	
									break;
								}
								case XmlPullParser.END_TAG:
								{
									name = parser.getName();
									if ("category".equals(name))
									{
										mainCat.category.add(category);
										subParsing = false;
									}
									break;
								}
							}
						}
					}
				break;
			}

			case XmlPullParser.END_TAG:
			{
				name = parser.getName();
				if ("categories".equals(name))
				{
					parsing = false;
				}
				break;
			}

			case XmlPullParser.END_DOCUMENT:
			{
				parsing = false;
				break;
			}
			}
		}
		return mainCat;
	}
}
