package com.basf.catalog.ui.technicalsupport;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.component.BaseActivity;
import com.basf.catalog.service.CategoryAssetsXml;
import com.basf.catalog.service.core.structs.BundleInformation;
import com.basf.catalog.ui.technicalsupport.adapter.TechnicalSupportAdapter;

public class TechnicalSupportActivity extends BaseActivity {

	TechnicalSupportAdapter tsa;
	TextView txt;
	ExpandableListView exp_list;

	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.technical_support;
	}
	
	@Override
	protected int getCurrentMenu() {
		// TODO Auto-generated method stub
		return 3;
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
		//		setContentView(R.layout.technical_support);

		txt = (TextView) findViewById(R.id.title);
		txt.setText("Technical Support");

		tsa = new TechnicalSupportAdapter(this);

		exp_list = (ExpandableListView) findViewById(R.id.explist);
		exp_list.setAdapter(tsa);
		exp_list.setOnGroupClickListener(new GroupClickListener());
		exp_list.setOnChildClickListener(new ChildClickListener());

		setIndicatorBounds();
	}

	private class GroupClickListener implements OnGroupClickListener {
		@Override
		public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
			// TODO Auto-generated method stub
			String xmlPath = null;
			if (groupPosition == 0){
				xmlPath = CategoryAssetsXml.Assets.CAE_LABS_CAPABILITY;
			} else if (groupPosition == 2){
				xmlPath = CategoryAssetsXml.Assets.MATERIAL_TESTING_LABS;
			} else if (groupPosition == 3){
				xmlPath = CategoryAssetsXml.Assets.PARTS_TESTING_LABS;
			} else if (groupPosition == 4){
				xmlPath = CategoryAssetsXml.Assets.TECHNICAL_SERVICES;
			} else if (groupPosition == 5){
				xmlPath = CategoryAssetsXml.Assets.PRODUCT_DEVELOPMENT_LABS;
			}
			Intent intent = new Intent(TechnicalSupportActivity.this, DetailTechnicalSupportActivity.class);
			intent.putExtra(BundleInformation.XmlUrl, xmlPath);

			if(groupPosition != 1){
				startActivity(intent);
				return true;
			} else {
				return false;
			}
		}
	}

	private class ChildClickListener implements OnChildClickListener {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			String xmlPath = null;
			if (childPosition == 0){
				xmlPath = CategoryAssetsXml.Assets.ULTRA_SIM_BASIC;
			} else if (childPosition == 1){
				xmlPath = CategoryAssetsXml.Assets.ULTRA_SIM_FLOW;
			}
			Intent intent = new Intent(TechnicalSupportActivity.this, DetailTechnicalSupportActivity.class);
			intent.putExtra(BundleInformation.XmlUrl, xmlPath);
			startActivity(intent);

			return false;
		}
	}

	private void setIndicatorBounds() {
		// TODO Auto-generated method stub
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		exp_list.setIndicatorBounds(width - GetDipsFromPixel(40), width - GetDipsFromPixel(10));
	}

	public int GetDipsFromPixel(float pixels) {
		// TODO Auto-generated method stub
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (pixels * scale + 0.5f);
	}

}