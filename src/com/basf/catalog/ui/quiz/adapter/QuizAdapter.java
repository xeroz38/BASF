package com.basf.catalog.ui.quiz.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.service.core.structs.MainQuiz;
import com.basf.catalog.service.core.structs.Quiz;

public class QuizAdapter extends BaseAdapter{

	MainQuiz mainQuiz;

	public void setContent(MainQuiz mainquiz) {
		mainQuiz = mainquiz;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mainQuiz != null)
			return mainQuiz.quizcategory.size();
		else
			return 0;
	}


	@Override
	public Quiz getItem(int position) {
		// TODO Auto-generated method stub
		return mainQuiz.quizcategory.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		final ViewHolder holder;

		if (convertView == null) {
			vi = View.inflate(parent.getContext(), R.layout.quiz_item, null);
			holder=new ViewHolder();
			holder.title = (TextView) vi.findViewById(R.id.title);
			vi.setTag(holder);      
		}
		else{
			holder = (ViewHolder) vi.getTag();
		}

		holder.title.setText(mainQuiz.quizcategory.get(position).name);

		return vi;
	}

	public class ViewHolder{
		TextView title;
	}
}
