package com.basf.catalog.ui.favorite.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.service.core.structs.FavoriteItem;

public class FavoriteAdapter extends BaseAdapter{

	private List<FavoriteItem> mList;
	
	public void setContent(List<FavoriteItem> list) {
        mList = list;
        notifyDataSetChanged();
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mList != null)
			return mList.size();
		else 
			return 0;
	}


	@Override
	public FavoriteItem getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
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
			vi = View.inflate(parent.getContext(), R.layout.favorite_item, null);
			holder=new ViewHolder();
			holder.title = (TextView) vi.findViewById(R.id.title);
			holder.subtitle = (TextView) vi.findViewById(R.id.subtitle);
			vi.setTag(holder);      
		}
		else{
			holder = (ViewHolder) vi.getTag();
		}

		holder.title.setText(mList.get(position).polymer);
		holder.subtitle.setText(mList.get(position).name);
		
		return vi;
	}

	public class ViewHolder{
		TextView title;
		TextView subtitle;
	}
}
