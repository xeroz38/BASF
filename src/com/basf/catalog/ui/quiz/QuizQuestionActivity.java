package com.basf.catalog.ui.quiz;

import java.io.InputStream;
import java.util.Collections;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.component.BaseActivity;
import com.basf.catalog.service.CategoryAssetsXml;
import com.basf.catalog.service.QuizQuestionParser;
import com.basf.catalog.service.core.structs.BundleInformation;
import com.basf.catalog.service.core.structs.MainQuizQuestion;
import com.basf.catalog.service.core.structs.QuizQuestionItem;

public class QuizQuestionActivity extends BaseActivity {
	
	int qlist = 0;

	TextView txt;
	ImageView iv;
	ListView lv;
	Builder ad;
	
	InputStream inputstream;
	
	MainQuizQuestion mainQQ;
	BaseAdapter quizAdapter;
	
	String quizname;
	String quizfile;


	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.quiz_question;
	}
	
	@Override
	protected int getCurrentMenu() {
		// TODO Auto-generated method stub
		return 4;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.quiz_question);
		
		Bundle bundle = getIntent().getExtras();
        if(bundle != null){
        	quizname = bundle.getString(BundleInformation.QuizName);
        	quizfile = bundle.getString(BundleInformation.QuizFile);
        }
    	ad = new AlertDialog.Builder(this);
    	ad.setCancelable(false);
		
		txt = (TextView) findViewById(R.id.title);
		txt.setText("Quiz" + " - " + quizname);
		
		quizAdapter = new QuizQuestionAdapter();
		lv = (ListView) findViewById(R.id.list);
		lv.setAdapter(quizAdapter);
		lv.setOnItemClickListener(new ListItemClickListener());
		
		try {
			inputstream = getAssets().open(CategoryAssetsXml.Assets.XML_ROOT_QUIZ + quizfile);
			mainQQ = QuizQuestionParser.getMainQuizQuestionList(inputstream);
			Collections.shuffle(mainQQ.quizQuestion);	//random list
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		txt = (TextView) findViewById(R.id.question);
		txt.setText(mainQQ.quizQuestion.get(qlist).question);
		
		iv = (ImageView) findViewById(R.id.rightarrow);
		iv.setOnClickListener(new NextClickListener());
		iv = (ImageView) findViewById(R.id.leftarrow);
		iv.setOnClickListener(new PreviousClickListener());
	}
	
	private class PreviousClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (qlist > 0){
				qlist--;
				txt.setText(mainQQ.quizQuestion.get(qlist).question);
				quizAdapter.notifyDataSetChanged();
			} else if (qlist == 0){
				qlist = mainQQ.quizQuestion.size() - 1;
				txt.setText(mainQQ.quizQuestion.get(qlist).question);
				quizAdapter.notifyDataSetChanged();
			}
		}
	}

	private class NextClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(qlist < mainQQ.quizQuestion.size() - 1){
				qlist++;
				txt.setText(mainQQ.quizQuestion.get(qlist).question);
				quizAdapter.notifyDataSetChanged();
			} else if (qlist == mainQQ.quizQuestion.size() - 1){
				qlist = 0;
				txt.setText(mainQQ.quizQuestion.get(qlist).question);
				quizAdapter.notifyDataSetChanged();
			}
		}
	}

	private class QuizQuestionAdapter extends BaseAdapter {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View vi = convertView;
			final ViewHolder holder;

			if (convertView == null) {
				vi = View.inflate(parent.getContext(), R.layout.quiz_question_item, null);
				holder=new ViewHolder();
				holder.title = (TextView) vi.findViewById(R.id.title);
				vi.setTag(holder);      
			}
			else{
				holder = (ViewHolder) vi.getTag();
			}

			vi.setBackgroundColor(Color.TRANSPARENT);
			holder.title.setText(mainQQ.quizQuestion.get(qlist).quizQuestionItem.get(position).name);

			return vi;
		}
		
		public class ViewHolder{
			TextView title;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public QuizQuestionItem getItem(int position) {
			// TODO Auto-generated method stub
			return mainQQ.quizQuestion.get(qlist).quizQuestionItem.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(mainQQ != null)
				return mainQQ.quizQuestion.get(qlist).quizQuestionItem.size();
			else
				return 0;
		}
	}

	private class ListItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> av, final View view, int position, long id) {
			// TODO Auto-generated method stub
			String correct = mainQQ.quizQuestion.get(qlist).quizQuestionItem.get(position).correct;

			if(correct.equals("true")){
				view.setBackgroundColor(Color.GREEN);
			    ad.setTitle("Your answer is correct!");
			    ad.setPositiveButton("Next Question", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(qlist < mainQQ.quizQuestion.size() - 1){
							qlist++;
							txt.setText(mainQQ.quizQuestion.get(qlist).question);
							quizAdapter.notifyDataSetChanged();
						} else if (qlist == mainQQ.quizQuestion.size() - 1){
							qlist = 0;
							txt.setText(mainQQ.quizQuestion.get(qlist).question);
							quizAdapter.notifyDataSetChanged();
						}
					}
				});
			    ad.show();
			} else {
				view.setBackgroundColor(Color.RED);
			    ad.setTitle("Please try again.");
			    ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						view.setBackgroundColor(Color.TRANSPARENT);
					}
				});
			    ad.show();
			}
		}
	}

}