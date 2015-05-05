package com.basf.catalog.ui.technicalsupport.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.basf.catalog.R;

public class TechnicalSupportAdapter extends BaseExpandableListAdapter {

	Context context;
	
	private final String[] category = {"CAE Labs Capability", "Ultrasim®", "Material Testing Labs", 
						"Parts Testing Labs", "Technical Support", "Product Development Labs"};
	private final String[][] sub_category = {{}, {"Basic", "Flow"}, {},
								{}, {}, {}};
	
	public TechnicalSupportAdapter(Context ctx) {
		super();
		context = ctx;
	}


	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
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
			vi = View.inflate(parent.getContext(), R.layout.technical_support_child, null);
			holder = new ViewHolder();
			holder.childTitle = (TextView) vi.findViewById(R.id.childtext);
			vi.setTag(holder);      
		}
		else{
			holder = (ViewHolder) vi.getTag();
		}

		holder.childTitle.setText(sub_category[groupPosition][childPosition]);
		
		return vi;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return sub_category[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return category.length;
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
			vi = View.inflate(parent.getContext(), R.layout.technical_support_parent, null);
			holder = new ViewHolder();
			holder.parentTitle = (TextView) vi.findViewById(R.id.parenttext);
			vi.setTag(holder);      
		}
		else{
			holder = (ViewHolder) vi.getTag();
		}
		
		holder.parentTitle.setText(category[groupPosition]);

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