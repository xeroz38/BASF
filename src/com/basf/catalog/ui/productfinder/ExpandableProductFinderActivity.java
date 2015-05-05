package com.basf.catalog.ui.productfinder;

import java.io.InputStream;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.component.BaseActivity;
import com.basf.catalog.service.CategoryAssetsXml;
import com.basf.catalog.service.ServiceParser;
import com.basf.catalog.service.core.structs.BundleInformation;
import com.basf.catalog.service.core.structs.MainSection;
import com.basf.catalog.service.core.structs.Section;
import com.basf.catalog.ui.productfinder.adapter.ExpandableProductFinderAdapter;

public class ExpandableProductFinderActivity extends BaseActivity {
	
	ExpandableListView elv;
	ExpandableProductFinderAdapter epfa;
	TextView toptext;
	TextView bottext;
	
	String currentMenu;
	String file;
	String grouptext;
	String childtext;
	
	InputStream inputstream;
	
	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.expandable_product_finder;
	}
	
	@Override
	protected int getCurrentMenu() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.expandable_product_finder);
		
		Bundle bundle = getIntent().getExtras();
        if(bundle != null){
			currentMenu = bundle.getString(BundleInformation.CurrentMenu);
			file = bundle.getString(BundleInformation.File);
			grouptext = bundle.getString(BundleInformation.GroupText);
			childtext = bundle.getString(BundleInformation.ChildText);
        }
		
		toptext = (TextView) findViewById(R.id.title);
		bottext = (TextView) findViewById(R.id.subtitle);

		if(childtext != null) {
			toptext.setText(currentMenu + " - " + grouptext);
			bottext.setText(childtext);
		} else {
			toptext.setText(currentMenu);
			bottext.setText(grouptext);
		}
		
		epfa = new ExpandableProductFinderAdapter();
		
		elv = (ExpandableListView) findViewById(R.id.explist);
		elv.setAdapter(epfa);
		elv.setOnChildClickListener(new ExpChildClickListener());
		
		getDataList(file);
		setIndicatorBounds();
	}

	private void getDataList(String xmlPath){
		try {
			inputstream = getAssets().open(CategoryAssetsXml.Assets.XML_ROOT_DATA + xmlPath);
			MainSection mainSect = ServiceParser.getMainSectionList(inputstream);
			epfa.setContent(mainSect);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class ExpChildClickListener implements OnChildClickListener {
		@Override
		public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			Section childsec = epfa.getChild(groupPosition, childPosition);

			Intent intent = new Intent(ExpandableProductFinderActivity.this, DetailProductFinderActivity.class);
//			intent.putExtra(BundleInformation.DataPosition, childPosition+1);
			intent.putExtra(BundleInformation.DataFile, childsec.secItems.get(childPosition).file);
			intent.putExtra(BundleInformation.DataName, childsec.secItems.get(childPosition).name);
			intent.putExtra(BundleInformation.DataPolymer, childsec.secItems.get(childPosition).polymer);
			
			if (childsec.secItems.get(childPosition).file.equals("nofile"))
			{
				final TextView message = new TextView(ExpandableProductFinderActivity.this);
				final SpannableString spanstring = new SpannableString("For more information, please email to\nplasticsportal.asia@basf.com");
				Linkify.addLinks(spanstring, Linkify.EMAIL_ADDRESSES);
				message.setText(spanstring);
				message.setTextSize(17);
				message.setPadding(25, 10, 25, 10);
				message.setMovementMethod(LinkMovementMethod.getInstance());

				Builder ad = new AlertDialog.Builder(ExpandableProductFinderActivity.this);
		    	ad.setView(message);
			    ad.setPositiveButton("Cancel", null);
			    ad.show();
			} else {
				startActivity(intent);
			}
			
			return false;
		}
	}

	private void setIndicatorBounds() {
		// TODO Auto-generated method stub
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		elv.setIndicatorBounds(width - GetDipsFromPixel(40), width - GetDipsFromPixel(10));
	}

	public int GetDipsFromPixel(float pixels) {
		// TODO Auto-generated method stub
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (pixels * scale + 0.5f);
	}

}