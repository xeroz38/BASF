package com.basf.catalog.service;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.basf.catalog.service.core.structs.MainQuiz;
import com.basf.catalog.service.core.structs.Quiz;

public class QuizCategoryParser
{
	public static MainQuiz getMainQuizCategoryList(InputStream in) throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setInput(in, "UTF-8");

		boolean parsing = true;
		String name;
		int eventType;

		MainQuiz mainQuiz = new MainQuiz();

		while (parsing)
		{
			eventType = parser.next();
			switch (eventType)
			{
				case XmlPullParser.START_TAG:
				{
					name = parser.getName();
					if ("category".equals(name))
					{
						Quiz quiz = new Quiz();
						quiz.file = parser.getAttributeValue("","file");
						quiz.name = parser.nextText();
						
						mainQuiz.quizcategory.add(quiz);
					}
					break;
				}
	
				case XmlPullParser.END_TAG:
				{
					name = parser.getName();
					if ("category".equals(name))
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
		return mainQuiz;
	}
}
