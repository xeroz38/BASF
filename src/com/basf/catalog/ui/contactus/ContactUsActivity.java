package com.basf.catalog.ui.contactus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.component.BaseActivity;
import com.basf.catalog.service.CategoryAssetsXml;

public class ContactUsActivity extends BaseActivity {

	TextView txt;
	WebView wv;


	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.contactus;
	}
	
	@Override
	protected int getCurrentMenu() {
		// TODO Auto-generated method stub
		return 6;
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
		//		setContentView(R.layout.contactus);

		txt = (TextView) findViewById(R.id.title);
		txt.setText("Contact Us");

		wv = (WebView) findViewById(R.id.detailweb);
		wv.loadUrl(CategoryAssetsXml.Assets.ASSET_ROOT_URL + CategoryAssetsXml.Assets.CONTACT_US);
		wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		wv.setWebViewClient(new WebViewClientListener());
	}

	private class WebViewClientListener extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.startsWith("tel:")) { 
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url)); 
				startActivity(intent); 
			}else if(url.startsWith("mailto:")) {
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url)); 
				startActivity(intent); 
			}else if(url.startsWith("http:") || url.startsWith("https:")) {
				view.loadUrl(url);
			}

			return true;
		}
	}

}