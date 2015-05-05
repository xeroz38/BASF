package com.basf.catalog.ui.productfinder.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.service.core.structs.Category;
import com.basf.catalog.service.core.structs.MainCategory;

public class ProductFinderAdapter extends BaseExpandableListAdapter {

	MainCategory mainCategory;
	
	public void setContent(MainCategory maincat) {
		mainCategory = maincat;
		notifyDataSetChanged();
	}

	@Override
	public Category getChild(int groupPosition, int childPosition) {
		if(mainCategory.category.get(groupPosition) != null)
			return mainCategory.category.get(groupPosition).category.get(childPosition);
		else
			return mainCategory.category.get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		if(mainCategory.category.get(groupPosition) != null)
			return mainCategory.category.get(groupPosition).category.size();
		else
			return 0;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		View vi = convertView;
		final ViewHolder holder;

		if (convertView == null) {
			vi = View.inflate(parent.getContext(), R.layout.product_finder_child, null);
			holder = new ViewHolder();
			holder.title = (TextView) vi.findViewById(R.id.childtext);
			vi.setTag(holder);      
		}
		else{
			holder = (ViewHolder) vi.getTag();
		}

		Category sub_category = getChild(groupPosition, childPosition);
		holder.title.setText(sub_category.text);
		
		return vi;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if(mainCategory.category.get(groupPosition).category != null)
			return mainCategory.category.get(groupPosition).category.size();
		else
			return 0;
	}

	@Override
	public Category getGroup(int groupPosition) {
		return mainCategory.category.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		if(mainCategory != null)
			return mainCategory.category.size();
		else
			return 0;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		View vi = convertView;
		final ViewHolder holder;

		if (convertView == null) {
			vi = View.inflate(parent.getContext(), R.layout.product_finder_parent, null);
			holder = new ViewHolder();
			holder.title = (TextView) vi.findViewById(R.id.parenttext);
			vi.setTag(holder);      
		}
		else{
			holder = (ViewHolder) vi.getTag();
		}
		
		Category group = getGroup(groupPosition);
		holder.title.setText(group.text);

		return vi;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	public class ViewHolder{
		TextView title;
	}
}