package com.basf.catalog.ui.quiz;

import java.io.InputStream;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.component.BaseActivity;
import com.basf.catalog.service.CategoryAssetsXml;
import com.basf.catalog.service.QuizCategoryParser;
import com.basf.catalog.service.core.structs.BundleInformation;
import com.basf.catalog.service.core.structs.MainQuiz;
import com.basf.catalog.ui.quiz.adapter.QuizAdapter;

public class QuizActivity extends BaseActivity {

	TextView txt;
	ListView lv;

	InputStream inputstream;

	MainQuiz mainQuiz;
	QuizAdapter qa;


	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.quiz;
	}
	
	@Override
	protected int getCurrentMenu() {
		// TODO Auto-generated method stub
		return 4;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.quiz);

		txt = (TextView) findViewById(R.id.title);
		txt.setText("Quiz");

		qa = new QuizAdapter();

		lv = (ListView) findViewById(R.id.list);
		lv.setAdapter(qa);
		lv.setOnItemClickListener(new ListItemClickListener());

		try {
			inputstream = getAssets().open(CategoryAssetsXml.Assets.QUIZ);
			mainQuiz = QuizCategoryParser.getMainQuizCategoryList(inputstream);
			qa.setContent(mainQuiz);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class ListItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> av, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(QuizActivity.this, QuizQuestionActivity.class);
			intent.putExtra(BundleInformation.QuizFile, mainQuiz.quizcategory.get(position).file);
			intent.putExtra(BundleInformation.QuizName, mainQuiz.quizcategory.get(position).name);
			startActivity(intent);
		}
	}
}