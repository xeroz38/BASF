package com.basf.catalog.ui.productfinder.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.service.core.structs.MainSection;
import com.basf.catalog.service.core.structs.Section;

public class ExpandableProductFinderAdapter extends BaseExpandableListAdapter {

	MainSection mainSection;
	
	public void setContent(MainSection mainSec) {
		mainSection = mainSec;
		notifyDataSetChanged();
	}

	@Override
	public Section getChild(int groupPosition, int childPosition) {
		return mainSection.sections.get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		if(mainSection.sections.get(groupPosition) != null)
			return mainSection.sections.get(groupPosition).secItems.size();
		else
			return 0;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		View vi = convertView;
		final ViewHolder holder;

		if (convertView == null) {
			vi = View.inflate(parent.getContext(), R.layout.expandable_child_product_finder, null);
			holder = new ViewHolder();
			holder.title = (TextView) vi.findViewById(R.id.childtext);
			holder.name = (TextView) vi.findViewById(R.id.desctext_name);
			holder.keypoint = (TextView) vi.findViewById(R.id.desctext_keypoint);
			holder.keypoint2 = (TextView) vi.findViewById(R.id.desctext_keypoint2);
			vi.setTag(holder);      
		}
		else{
			holder = (ViewHolder) vi.getTag();
		}

		Section item_section = getChild(groupPosition, childPosition);
		holder.title.setText(item_section.secItems.get(childPosition).polymer);
		holder.name.setText(item_section.secItems.get(childPosition).name);
//		holder.keypoint.setText(item_section.secItems.get(childPosition).keypoint);
		holder.keypoint2.setText(item_section.secItems.get(childPosition).keypoint2);
		
		return vi;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if(mainSection.sections.get(groupPosition).secItems != null)
			return mainSection.sections.get(groupPosition).secItems.size();
		else
			return 0;
	}

	@Override
	public Section getGroup(int groupPosition) {
		return mainSection.sections.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		if(mainSection != null)
			return mainSection.sections.size();
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
			vi = View.inflate(parent.getContext(), R.layout.expandable_parent_product_finder, null);
			holder = new ViewHolder();
			holder.title = (TextView) vi.findViewById(R.id.parenttext);
			vi.setTag(holder);      
		}
		else{
			holder = (ViewHolder) vi.getTag();
		}
		
		Section group = getGroup(groupPosition);
		holder.title.setText(group.name);

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
		TextView name;
		TextView keypoint;
		TextView keypoint2;
	}
}