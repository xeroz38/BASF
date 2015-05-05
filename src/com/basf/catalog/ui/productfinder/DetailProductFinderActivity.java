package com.basf.catalog.ui.productfinder;

import java.io.IOException;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.component.BaseActivity;
import com.basf.catalog.service.CategoryAssetsXml;
import com.basf.catalog.service.core.structs.BundleInformation;
import com.basf.catalog.service.core.structs.BundleInformation.FavoriteColumns;

public class DetailProductFinderActivity extends BaseActivity {

	Button prop_btn;
	Button proc_btn;
	Button flam_btn;
	Button mech_btn;
	Button ther_btn;
	Button elec_btn;
	TextView tv;
	WebView wv;
	ImageView iv;

	String[] catalogUrls = null;
	boolean isFavorite = false;
//	int positiontext;
	String filetext;
	String nametext;
	String polymertext;

	ContentValues values;
	

	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.detail_product_finder;
	}
	
	@Override
	protected int getCurrentMenu() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.detail_product_finder);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
//			positiontext = bundle.getInt(BundleInformation.DataPosition);
			filetext = bundle.getString(BundleInformation.DataFile);
			nametext = bundle.getString(BundleInformation.DataName);
			polymertext = bundle.getString(BundleInformation.DataPolymer);
		}

		tv = (TextView) findViewById(R.id.titlebold);
		tv.setText(polymertext);

		tv = (TextView) findViewById(R.id.titlesmall);
		tv.setText(nametext);

		try {
			catalogUrls = getAssets().list(CategoryAssetsXml.Assets.XML_ROOT + filetext);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		prop_btn = (Button) findViewById(R.id.prop_btn);
		prop_btn.setBackgroundResource(R.drawable.detail_button_pressed);
		proc_btn = (Button) findViewById(R.id.proc_btn);
		flam_btn = (Button) findViewById(R.id.flam_btn);
		mech_btn = (Button) findViewById(R.id.mech_btn);
		ther_btn = (Button) findViewById(R.id.ther_btn);
		elec_btn = (Button) findViewById(R.id.elec_btn);

		for (int i = 0; i < catalogUrls.length; i++) {
			if(catalogUrls[i].equals(CategoryAssetsXml.Assets.PROPERTIES)){
				prop_btn.setVisibility(View.VISIBLE);
				prop_btn.setOnClickListener(new DetailMenuClickListener());
			} else if (catalogUrls[i].equals(CategoryAssetsXml.Assets.PROCESSING)){
				proc_btn.setVisibility(View.VISIBLE);
				proc_btn.setOnClickListener(new DetailMenuClickListener());
			} else if (catalogUrls[i].equals(CategoryAssetsXml.Assets.FLAMMABILITY)){
				flam_btn.setVisibility(View.VISIBLE);
				flam_btn.setOnClickListener(new DetailMenuClickListener());
			} else if (catalogUrls[i].equals(CategoryAssetsXml.Assets.MECHANICAL)){
				mech_btn.setVisibility(View.VISIBLE);
				mech_btn.setOnClickListener(new DetailMenuClickListener());
			} else if (catalogUrls[i].equals(CategoryAssetsXml.Assets.THERMAL)){
				ther_btn.setVisibility(View.VISIBLE);
				ther_btn.setOnClickListener(new DetailMenuClickListener());
			} else if (catalogUrls[i].equals(CategoryAssetsXml.Assets.ELECTRICAL)){
				elec_btn.setVisibility(View.VISIBLE);
				elec_btn.setOnClickListener(new DetailMenuClickListener());
			}
		}

		wv = (WebView) findViewById(R.id.detailweb);
		wv.loadUrl(CategoryAssetsXml.Assets.XML_ROOT_URL + filetext + "/" + CategoryAssetsXml.Assets.PROPERTIES);
		wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		wv.setWebViewClient(new WebViewClientListener());
		
		iv = (ImageView) findViewById(R.id.favorite_btn);
		
		values = new ContentValues();
		values.put(FavoriteColumns.NAME, nametext);
		values.put(FavoriteColumns.POLYMER, polymertext);
		values.put(FavoriteColumns.XML_PATH, filetext);

		Cursor cursor = getContentResolver().query(FavoriteColumns.CONTENT_URI, FavoriteColumns.QUERY_SHORT,  FavoriteColumns.NAME + "=?", new String[]{nametext}, null);
		
		if(cursor.getCount() > 0){
			iv.setImageResource(R.drawable.favorites_on);
			isFavorite = true;
		} else {
			iv.setImageResource(R.drawable.favorites_off);
			isFavorite = false;
		}
		
		iv.setOnClickListener(new FavoriteClickListener());
	}

	private class FavoriteClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(isFavorite){
				getContentResolver().delete(FavoriteColumns.CONTENT_URI, FavoriteColumns.NAME + "=?", new String[]{nametext});
				iv.setImageResource(R.drawable.favorites_off);
				isFavorite = false;
			} else {
				getContentResolver().insert(FavoriteColumns.CONTENT_URI, values);
				iv.setImageResource(R.drawable.favorites_on);
				isFavorite = true;
			}
		}
	}

	private class DetailMenuClickListener implements OnClickListener {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId())
			{
				case R.id.prop_btn:
				{
					wv.loadUrl(CategoryAssetsXml.Assets.XML_ROOT_URL + filetext + "/" + CategoryAssetsXml.Assets.PROPERTIES);
					wv.setWebViewClient(new WebViewClientListener());
					resetBackground();
					prop_btn.setBackgroundResource(R.drawable.detail_button_pressed);
					break;
				}
				case R.id.proc_btn:
				{
					wv.loadUrl(CategoryAssetsXml.Assets.XML_ROOT_URL + filetext + "/" + CategoryAssetsXml.Assets.PROCESSING);
					wv.setWebViewClient(new WebViewClientListener());
					resetBackground();
					proc_btn.setBackgroundResource(R.drawable.detail_button_pressed);
					break;
				}
				case R.id.flam_btn:
				{
					wv.loadUrl(CategoryAssetsXml.Assets.XML_ROOT_URL + filetext + "/" + CategoryAssetsXml.Assets.FLAMMABILITY);
					wv.setWebViewClient(new WebViewClientListener());
					resetBackground();
					flam_btn.setBackgroundResource(R.drawable.detail_button_pressed);
					break;
				}
				case R.id.mech_btn:
				{
					wv.loadUrl(CategoryAssetsXml.Assets.XML_ROOT_URL + filetext + "/" + CategoryAssetsXml.Assets.MECHANICAL);
					wv.setWebViewClient(new WebViewClientListener());
					resetBackground();
					mech_btn.setBackgroundResource(R.drawable.detail_button_pressed);
					break;
				}
				case R.id.ther_btn:
				{
					wv.loadUrl(CategoryAssetsXml.Assets.XML_ROOT_URL + filetext + "/" + CategoryAssetsXml.Assets.THERMAL);
					wv.setWebViewClient(new WebViewClientListener());
					resetBackground();
					ther_btn.setBackgroundResource(R.drawable.detail_button_pressed);
					break;
				}
				case R.id.elec_btn:
				{
					wv.loadUrl(CategoryAssetsXml.Assets.XML_ROOT_URL + filetext + "/" + CategoryAssetsXml.Assets.ELECTRICAL);
					wv.setWebViewClient(new WebViewClientListener());
					resetBackground();
					elec_btn.setBackgroundResource(R.drawable.detail_button_pressed);
					break;
				}
				default: break;
			}
		}
	}

	private void resetBackground(){

		prop_btn.setBackgroundResource(R.layout.detail_navigation_selector);
		proc_btn.setBackgroundResource(R.layout.detail_navigation_selector);
		flam_btn.setBackgroundResource(R.layout.detail_navigation_selector);
		mech_btn.setBackgroundResource(R.layout.detail_navigation_selector);
		ther_btn.setBackgroundResource(R.layout.detail_navigation_selector);
		elec_btn.setBackgroundResource(R.layout.detail_navigation_selector);
	}

	private class WebViewClientListener extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
}