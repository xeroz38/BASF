package com.basf.catalog.ui.productfinder.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.basf.catalog.R;

public class ProductFinderImageAdapter extends BaseAdapter {

	public int[] imagelist = new int[] {R.drawable.pd_powertrain_chassis, R.drawable.pd_interior, R.drawable.pd_exterior, R.drawable.pd_electronic};

	private Context mContext;

	public ProductFinderImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return Integer.MAX_VALUE;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView = new ImageView(mContext);
		
        int itemPos = (position % imagelist.length);

		imageView.setImageResource(imagelist[itemPos]);
		imageView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		((BitmapDrawable) imageView.getDrawable()).setAntiAlias(true);

		return imageView;
	}
}
