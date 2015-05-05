package com.basf.catalog.ui.productfinder.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.service.core.structs.Category;
import com.basf.catalog.service.core.structs.MainCategory;

public class ProductFinderListAdapter extends BaseAdapter{

	MainCategory mainCategory;

	public void setContent(MainCategory maincat) {
		mainCategory = maincat;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mainCategory != null)
			return mainCategory.category.size();
		else
			return 0;
	}


	@Override
	public Category getItem(int position) {
		// TODO Auto-generated method stub
		return mainCategory.category.get(position);
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
			vi = View.inflate(parent.getContext(), R.layout.product_finder_list_item, null);
			holder=new ViewHolder();
			holder.title = (TextView) vi.findViewById(R.id.title);
			vi.setTag(holder);      
		}
		else{
			holder = (ViewHolder) vi.getTag();
		}

		holder.title.setText(mainCategory.category.get(position).text);

		return vi;
	}

	public class ViewHolder{
		TextView title;
	}
}
