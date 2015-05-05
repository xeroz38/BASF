package com.basf.catalog.service;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.basf.catalog.service.core.structs.MainSection;
import com.basf.catalog.service.core.structs.Section;
import com.basf.catalog.service.core.structs.SectionItem;

public class ServiceParser {
	
	public static MainSection getMainSectionList(InputStream in) throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setInput(in, "UTF-8");

		boolean parsing = true;
		String name;
		int eventType;

		MainSection mainSection = new MainSection(); // TODO adasd

		while (parsing) {
			eventType = parser.next();
			switch (eventType) {
				
				case XmlPullParser.START_TAG: {
					name = parser.getName();
					
					if ("items".equals(name)) {
						// nothing to do
					} else if ("section".equals(name)) {
						
						Section section = new Section();
						section.name = parser.getAttributeValue("", "name");

						boolean sectionParsing = true;
						while (sectionParsing) {
							
							eventType = parser.next();
							switch (eventType) {
								case XmlPullParser.START_TAG: {
									String sectionName = parser.getName();
									
									if ("item".equals(sectionName)) {
										SectionItem i = new SectionItem(); // TODO init

										i.file = parser.getAttributeValue("", "file");

										parser.nextTag();
										i.name = parser.nextText();

										parser.nextTag();
										i.polymer = parser.nextText();

										parser.nextTag();
										i.keypoint = parser.nextText();

										parser.nextTag();
										i.keypoint2 = parser.nextText();

										section.secItems.add(i);
									}
								}
								case XmlPullParser.END_TAG: {
									name = parser.getName();
									if ("section".equals(name)) {
										sectionParsing = false;
									}
									break;
								}

								case XmlPullParser.END_DOCUMENT: {
									sectionParsing = false;
									break;
								}
							}
						} // end while
						mainSection.sections.add(section);
					}

					break;
				}

				case XmlPullParser.END_TAG: {
					name = parser.getName();
					if ("items".equals(name)) {
						parsing = false;
					}
					break;
				}

				case XmlPullParser.END_DOCUMENT: {
					parsing = false;
					break;
				}
			}
		}
		return mainSection;
	}

}
