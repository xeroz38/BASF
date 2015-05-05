package com.basf.catalog.ui.troubleshooter;

import java.io.InputStream;

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
import com.basf.catalog.service.TroubleshooterParser;
import com.basf.catalog.service.core.structs.BundleInformation;
import com.basf.catalog.service.core.structs.MainTroubleshooter;
import com.basf.catalog.service.core.structs.Troubleshooter;
import com.basf.catalog.ui.troubleshooter.adapter.TroubleshooterAdapter;

public class TroubleshooterActivity extends BaseActivity {

	TroubleshooterAdapter tsa;
	TextView txt;
	ExpandableListView exp_list;
	
	InputStream inputstream;
	
	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.troubleshooter;
	}
	
	@Override
	protected int getCurrentMenu() {
		// TODO Auto-generated method stub
		return 2;
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
//		setContentView(R.layout.troubleshooter);

		txt = (TextView) findViewById(R.id.title);
		txt.setText("Injection Moulding Troubleshooter");

		tsa = new TroubleshooterAdapter();
		
		exp_list = (ExpandableListView) findViewById(R.id.explist);
		exp_list.setAdapter(tsa);
		exp_list.setOnGroupClickListener(new GroupClickListener());
		exp_list.setOnChildClickListener(new ChildClickListener());
		
		try {
			inputstream = getAssets().open(CategoryAssetsXml.Assets.TROUBLESHOOTER);
			MainTroubleshooter mainTroubleshooter = TroubleshooterParser.getMainTroubleshooterList(inputstream);
			tsa.setContent(mainTroubleshooter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setIndicatorBounds();
	}
	
	private class GroupClickListener implements OnGroupClickListener {
		@Override
		public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
			// TODO Auto-generated method stub
			Troubleshooter ts = tsa.getGroup(groupPosition);
			
			Intent intent = new Intent(TroubleshooterActivity.this, DetailTroubleshooterActivity.class);
			intent.putExtra(BundleInformation.File, ts.file);
			intent.putExtra(BundleInformation.Name, ts.name);
			
			if (ts.file != null){
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
			Troubleshooter ts = tsa.getChild(groupPosition, childPosition);
			
			Intent intent = new Intent(TroubleshooterActivity.this, DetailTroubleshooterActivity.class);
			intent.putExtra(BundleInformation.File, ts.troubleshooter.get(childPosition).file);
			intent.putExtra(BundleInformation.Name, ts.troubleshooter.get(childPosition).name);
			
			intent.putExtra(BundleInformation.ListFile, tsa.getGroup(groupPosition));
			intent.putExtra(BundleInformation.ListItem, ts.troubleshooter.get(childPosition));
			
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