package com.basf.catalog.ui.technicalsupport;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.component.BaseActivity;
import com.basf.catalog.service.CategoryAssetsXml;
import com.basf.catalog.service.core.structs.BundleInformation;

public class DetailTechnicalSupportActivity extends BaseActivity {

	String xmlPath;

	TextView txt;
	WebView wv;


	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.detail_technical_support;
	}
	
	@Override
	protected int getCurrentMenu() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.detail_technical_support);

		Bundle bundle = getIntent().getExtras();
		if(bundle!= null){
			xmlPath = bundle.getString(BundleInformation.XmlUrl);
		}

		wv = (WebView) findViewById(R.id.detailweb);
		wv.loadUrl(CategoryAssetsXml.Assets.ASSET_ROOT_URL + xmlPath);
		wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		wv.setWebViewClient(new WebViewClientListener());
	}

	private class WebViewClientListener extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

}