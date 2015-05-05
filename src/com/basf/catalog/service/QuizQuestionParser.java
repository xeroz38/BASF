package com.basf.catalog.service;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import com.basf.catalog.service.core.structs.MainQuizQuestion;
import com.basf.catalog.service.core.structs.QuizQuestion;
import com.basf.catalog.service.core.structs.QuizQuestionItem;

public class QuizQuestionParser
{
	public static MainQuizQuestion getMainQuizQuestionList(InputStream in) throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setInput(in, "UTF-8");

		boolean parsing = true;
		String name;
		int eventType;

		MainQuizQuestion mainQQ = new MainQuizQuestion();
		QuizQuestion quizquestion = null;

		while (parsing)
		{
			eventType = parser.next();
			switch (eventType)
			{
				case XmlPullParser.START_TAG:
				{
					name = parser.getName();
					if ("text".equals(name))
					{
						quizquestion= new QuizQuestion();
						quizquestion.question = parser.nextText();
					} else 
						
						if ("answers".equals(name))
						{
							boolean subParsing = true;
							while (subParsing)
							{
								eventType = parser.next();
								switch (eventType)
								{
									case XmlPullParser.START_TAG:
									{
										name = parser.getName();
										if ("answer".equals(name)) 
										{
											QuizQuestionItem quizquestionitem = new QuizQuestionItem(); 
		
											quizquestionitem.correct = parser.getAttributeValue("","correct");
											quizquestionitem.name = parser.nextText();
											
											Log.e(quizquestionitem.name, quizquestionitem.correct);

											quizquestion.quizQuestionItem.add(quizquestionitem);
										}
		
										break;
									}
									case XmlPullParser.END_TAG:
									{
										name = parser.getName();
										if ("answers".equals(name))
										{
											mainQQ.quizQuestion.add(quizquestion);
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
					if ("questions".equals(name))
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
		return mainQQ;
	}
}
