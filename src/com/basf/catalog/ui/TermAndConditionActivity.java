package com.basf.catalog.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.basf.catalog.R;
import com.basf.catalog.service.CategoryAssetsXml;

public class TermAndConditionActivity extends Activity {

	private WebView wv;
	private Button button;

	SharedPreferences sharedPreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.term_and_condition);

		wv = (WebView) findViewById(R.id.termweb);
		wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		wv.setWebViewClient(new WebViewClientListener());

		if(isOnline()){
			wv.loadUrl(CategoryAssetsXml.Web.TOC_WEB);
		} else {
			wv.loadUrl(CategoryAssetsXml.Assets.ASSET_ROOT_URL + CategoryAssetsXml.Assets.TOC);
		}
		

		sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
		int accepted = sharedPreferences.getInt("accepted", 0);

		if (accepted == 1){
			finish();

			Intent intent = new Intent(TermAndConditionActivity.this, SplashScreen.class);
			startActivity(intent);
		}

		button = (Button) findViewById(R.id.accept_btn);
		button.setOnClickListener(new AcceptClickListener());
		button = (Button) findViewById(R.id.decline_btn);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private class AcceptClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			finish();

			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putInt("accepted", 1);
			editor.commit();

			Intent intent = new Intent(TermAndConditionActivity.this, SplashScreen.class);
			startActivity(intent);
		}
	}

	public boolean isOnline() 
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
			return true;
		}
		return false;
	}

	private class WebViewClientListener extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
			view.loadUrl(CategoryAssetsXml.Assets.ASSET_ROOT_URL + CategoryAssetsXml.Assets.TOC);
		}
	}
}