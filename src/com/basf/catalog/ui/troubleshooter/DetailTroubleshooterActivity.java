package com.basf.catalog.ui.troubleshooter;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.component.BaseActivity;
import com.basf.catalog.component.OnFlingGallery;
import com.basf.catalog.service.CategoryAssetsXml;
import com.basf.catalog.service.core.structs.BundleInformation;
import com.basf.catalog.service.core.structs.Troubleshooter;
import com.basf.catalog.util.IOUtils;

public class DetailTroubleshooterActivity extends BaseActivity {

	String filetext;
	String nametext;
	String headerHtml;
	String appearance;
	String cause;
	String correction;
	String[] file;
	String[] caption;
	
	int curIndex;

	TextView toptext;
	TextView bottext;
	Button app_btn;
	Button cause_btn;
	Button cor_btn;
	ImageView right_arrow;
	ImageView left_arrow;
	ImageView fig_indicator;
	LinearLayout linearFigIndicator;
	WebView description;

	OnFlingGallery gallery;
	BaseAdapter troubleshooterAdapter;

	Troubleshooter listTroubleshooter = null;
	Troubleshooter itemTroubleshooter = null;
	
	InputStream in;
	
	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.detail_troubleshooter;
	}
	
	@Override
	protected int getCurrentMenu() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.detail_troubleshooter);

		Bundle bundle = getIntent().getExtras();
		if(bundle!= null){
			filetext = bundle.getString(BundleInformation.File);
			nametext = bundle.getString(BundleInformation.Name);
			listTroubleshooter = (Troubleshooter) bundle.getSerializable(BundleInformation.ListFile);
			itemTroubleshooter = (Troubleshooter) bundle.getSerializable(BundleInformation.ListItem);
		}

		toptext = (TextView) findViewById(R.id.title);
		bottext = (TextView) findViewById(R.id.subtitle);		
		toptext.setText("Injection Moulding Troubleshooter");
		bottext.setText(nametext);
		
		
		right_arrow = (ImageView) findViewById(R.id.rightarrow);
		left_arrow = (ImageView) findViewById(R.id.leftarrow);
		
		if(listTroubleshooter != null){
			curIndex = (listTroubleshooter.troubleshooter.indexOf(itemTroubleshooter));
			right_arrow.setOnClickListener(new NextClickListener());
			left_arrow.setOnClickListener(new PreviousClickListener());
		} else {
			right_arrow.setVisibility(View.GONE);
			left_arrow.setVisibility(View.GONE);
		}
		

		app_btn = (Button) findViewById(R.id.app);
		app_btn.setBackgroundResource(R.drawable.detail_troubleshoot_button_pressed);
		app_btn.setOnClickListener(new DetailClickListener());
		cause_btn = (Button) findViewById(R.id.cause);
		cause_btn.setOnClickListener(new DetailClickListener());
		cor_btn = (Button) findViewById(R.id.cor);
		cor_btn.setOnClickListener(new DetailClickListener());

		try {
			getJSONList();
			setGallery();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void setGallery() {
		// TODO Auto-generated method stub
		linearFigIndicator = (LinearLayout) findViewById(R.id.fig_linear_indicator);
		linearFigIndicator.setOrientation(LinearLayout.HORIZONTAL);

		linearFigIndicator.removeAllViews();
		
		for(int i=0; i < file.length; i++){
			fig_indicator = new ImageView(this);
			linearFigIndicator.addView(fig_indicator);
		}

		troubleshooterAdapter = new TroubleshooterImageAdapter();

		gallery = (OnFlingGallery) findViewById(R.id.gallery);
		gallery.setAnimationDuration(1000);
		gallery.setAdapter(troubleshooterAdapter);
		gallery.setSelection(0);

		gallery.setOnItemSelectedListener(new IndicatorItemSelectedListener());
	}

	private void getJSONList() throws JSONException, IOException {
		// TODO Auto-generated method stub
		in = getAssets().open(CategoryAssetsXml.Assets.DATA_TROUBLESHOOTER + filetext);
		String jsonFile = IOUtils.read(in);
		in = getAssets().open(CategoryAssetsXml.Assets.TROUBLESHOOTER_HEADER);
		headerHtml = IOUtils.read(in);

		JSONObject jsonObj = new JSONObject(jsonFile);
		JSONArray picArray = jsonObj.getJSONArray("picture");  

		appearance = jsonObj.getString("appearance");
		cause = jsonObj.getString("cause");
		correction = jsonObj.getString("correction");

		file = new String[picArray.length()];
		caption = new String[picArray.length()];

		for(int i=0; i < picArray.length(); i++)  
		{  
			file[i] = picArray.getJSONObject(i).getString("file");  
			caption[i] = picArray.getJSONObject(i).getString("caption");
		}  
		
		description = (WebView) findViewById(R.id.description);
		description.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		description.loadData(headerHtml + appearance + "</body></html>", "text/html", "UTF-8");
	}

	private class PreviousClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(curIndex > 0){
				curIndex--;
			}
			
			filetext = listTroubleshooter.troubleshooter.get(curIndex).file;
			nametext = listTroubleshooter.troubleshooter.get(curIndex).name;
			bottext.setText(nametext);
			
			try {
				getJSONList();
				setGallery();
				resetBackground();
				app_btn.setBackgroundResource(R.drawable.detail_troubleshoot_button_pressed);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class NextClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(curIndex < listTroubleshooter.troubleshooter.size()-1){
				curIndex++;
			}
			
			filetext = listTroubleshooter.troubleshooter.get(curIndex).file;
			nametext = listTroubleshooter.troubleshooter.get(curIndex).name;
			bottext.setText(nametext);
			
			try {
				getJSONList();
				setGallery();
				resetBackground();
				app_btn.setBackgroundResource(R.drawable.detail_troubleshoot_button_pressed);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class IndicatorItemSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			for(int i=0; i < file.length; i++){
				ImageView indicator = (ImageView) linearFigIndicator.getChildAt(i);
				indicator.setImageResource(R.drawable.bullet_off);
			}
			ImageView indicator = (ImageView) linearFigIndicator.getChildAt(position);
			indicator.setImageResource(R.drawable.bullet_on);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	}

	private class TroubleshooterImageAdapter extends BaseAdapter {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LinearLayout linearGallery = new LinearLayout(getApplicationContext());
			ImageView fig_image = new ImageView(getApplicationContext());
			TextView fig_caption = new TextView(getApplicationContext());
			
			InputStream bitmap;
			try {
				bitmap = getAssets().open(CategoryAssetsXml.Assets.DATA_TROUBLESHOOTER + file[position]);
				Bitmap bit = BitmapFactory.decodeStream(bitmap);
				fig_image.setImageBitmap(bit);
				fig_image.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 200));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fig_caption.setPadding(25, 0, 25, 10);
			fig_caption.setTextColor(Color.GRAY);
			fig_caption.setText(caption[position]);

			linearGallery.setOrientation(LinearLayout.VERTICAL);
			linearGallery.setGravity(Gravity.CENTER);
			linearGallery.addView(fig_image);
			linearGallery.addView(fig_caption);

			return linearGallery;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return file.length;
		}
	}

	private class DetailClickListener implements OnClickListener {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId())
			{
				case R.id.app:
				{
					description.loadData(headerHtml + appearance + "</body></html>", "text/html", "UTF-8");
					resetBackground();
					app_btn.setBackgroundResource(R.drawable.detail_troubleshoot_button_pressed);
					break;
				}
				case R.id.cause:
				{
					description.loadData(headerHtml + cause + "</body></html>", "text/html", "UTF-8");
					resetBackground();
					cause_btn.setBackgroundResource(R.drawable.detail_troubleshoot_button_pressed);
					break;
				}
				case R.id.cor:
				{
					description.loadData(headerHtml + correction + "</body></html>", "text/html", "UTF-8");
					resetBackground();
					cor_btn.setBackgroundResource(R.drawable.detail_troubleshoot_button_pressed);
					break;
				}
				default: break;
			}
		}
	}

	private void resetBackground()
	{
		app_btn.setBackgroundResource(R.layout.detail_troubleshooter_navigation_selector);
		cause_btn.setBackgroundResource(R.layout.detail_troubleshooter_navigation_selector);
		cor_btn.setBackgroundResource(R.layout.detail_troubleshooter_navigation_selector);
	}

}