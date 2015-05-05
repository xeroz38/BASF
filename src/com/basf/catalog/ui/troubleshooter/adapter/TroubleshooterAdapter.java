package com.basf.catalog.ui.troubleshooter.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.service.core.structs.MainTroubleshooter;
import com.basf.catalog.service.core.structs.Troubleshooter;

public class TroubleshooterAdapter extends BaseExpandableListAdapter {

	MainTroubleshooter mainTroubleshooter;

	public void setContent(MainTroubleshooter mainCat) {
		mainTroubleshooter = mainCat;
		notifyDataSetChanged();
	}


	@Override
	public Troubleshooter getChild(int groupPosition, int childPosition) {
		return mainTroubleshooter.troubleshooter.get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		View vi = convertView;
		final ViewHolder holder;

		if (convertView == null) {
			vi = View.inflate(parent.getContext(), R.layout.troubleshooter_child, null);
			holder = new ViewHolder();
			holder.childTitle = (TextView) vi.findViewById(R.id.childtext);
			vi.setTag(holder);      
		}
		else{
			holder = (ViewHolder) vi.getTag();
		}
		Troubleshooter item_section = getChild(groupPosition, childPosition);
		holder.childTitle.setText(item_section.troubleshooter.get(childPosition).name);

		return vi;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if(mainTroubleshooter.troubleshooter.get(groupPosition).troubleshooter != null)
			return mainTroubleshooter.troubleshooter.get(groupPosition).troubleshooter.size();
		else
			return 0;
	}

	@Override
	public Troubleshooter getGroup(int groupPosition) {
		return mainTroubleshooter.troubleshooter.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		if(mainTroubleshooter != null)
			return mainTroubleshooter.troubleshooter.size();
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
			vi = View.inflate(parent.getContext(), R.layout.troubleshooter_parent, null);
			holder = new ViewHolder();
			holder.parentTitle = (TextView) vi.findViewById(R.id.parenttext);
			vi.setTag(holder);      
		}
		else{
			holder = (ViewHolder) vi.getTag();
		}
		Troubleshooter group = getGroup(groupPosition);
		
		holder.parentTitle.setText(group.name);

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
		TextView parentTitle;
		TextView childTitle;
	}
}