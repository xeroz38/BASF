package com.basf.catalog.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import com.basf.catalog.service.core.structs.MainTroubleshooter;
import com.basf.catalog.service.core.structs.Troubleshooter;

public class TroubleshooterParser
{
	public static MainTroubleshooter getMainTroubleshooterList(InputStream in) throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setInput(in, "UTF-8");

		boolean parsing = true;
		String name;
		int eventType;

		MainTroubleshooter mainTroubleshooter = new MainTroubleshooter();

		while (parsing)
		{
			eventType = parser.next();
			switch (eventType)
			{
				case XmlPullParser.START_TAG:
				{
					name = parser.getName();
					if ("item".equals(name))
					{
						Troubleshooter item = new Troubleshooter(); // category lv 1
						item.name = parser.getAttributeValue("","name");
						item.file = parser.getAttributeValue("","file");

						boolean subParsing = true;
						while (subParsing)
						{
							eventType = parser.next();
							switch (eventType)
							{
								case XmlPullParser.START_TAG:
								{
									name = parser.getName();
									if ("sub_item".equals(name)) 
									{
										if(item.troubleshooter == null) 
											item.troubleshooter = new ArrayList<Troubleshooter>();

										Troubleshooter sub_item = new Troubleshooter(); //category lv 2

										sub_item.file = parser.getAttributeValue("","file");
										sub_item.name = parser.getAttributeValue("","name");

										Log.e(item.file, item.name);
										
										item.troubleshooter.add(sub_item);
									}

									break;
								}
								case XmlPullParser.END_TAG:
								{
									name = parser.getName();
									if ("item".equals(name))
									{
										mainTroubleshooter.troubleshooter.add(item);
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
					if ("troubleshooter".equals(name))
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
		return mainTroubleshooter;
	}
}
