package com.basf.catalog.service.core.structs;

import java.util.ArrayList;

public class QuizQuestion {
	
	public String question;
	public ArrayList<QuizQuestionItem> quizQuestionItem;

	public QuizQuestion() {
		quizQuestionItem = new ArrayList<QuizQuestionItem>();
	}
}