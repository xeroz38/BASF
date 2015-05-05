package com.basf.catalog.ui.productfinder;

import java.io.InputStream;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.component.BaseActivity;
import com.basf.catalog.component.OnFlingGallery;
import com.basf.catalog.service.CategoryAssetsXml;
import com.basf.catalog.service.CategoryParser;
import com.basf.catalog.service.core.structs.BundleInformation;
import com.basf.catalog.service.core.structs.Category;
import com.basf.catalog.service.core.structs.MainCategory;
import com.basf.catalog.ui.productfinder.adapter.ProductFinderAdapter;
import com.basf.catalog.ui.productfinder.adapter.ProductFinderImageAdapter;
import com.basf.catalog.ui.productfinder.adapter.ProductFinderListAdapter;

public class ProductFinderActivity extends BaseActivity {

	private final int CAT_CHASSIS = 0;
	private final int CAT_INTERIOR = 1;
	private final int CAT_EXTERIOR = 2;
	private final int CAT_AUTO = 3;

//	private final int categories[] = {CAT_CHASSIS, CAT_INTERIOR, CAT_EXTERIOR, CAT_AUTO};

	String currentMenu = "Powertrain";
//	int indexMenu = CAT_CHASSIS;
	boolean isVisible;
	public static boolean isNewIntent;

	LinearLayout root;
	LinearLayout firstMenu;
	LinearLayout linearFigIndicator;
	ImageView fig_indicator;
	Button menubtn;
	RelativeLayout list_app;
	ExpandableListView explist;
	ListView lv;
	HorizontalScrollView hsv;
	InputStream inputstream;

	OnFlingGallery gallery;
	ProductFinderImageAdapter pfia;
	ProductFinderAdapter pfa;
	ProductFinderListAdapter pfla;
	MainCategory mainCat;

	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.product_finder;
	}

	@Override
	protected int getCurrentMenu() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		
		if (!isNewIntent) loadCategoryXml(CAT_CHASSIS);
		isNewIntent = false;
		//		LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(BroadcastInformation.IntentName));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.product_finder);

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		firstMenu = (LinearLayout) inflater.inflate(R.layout.scroll_header, null);
		firstMenu.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		root = (LinearLayout) findViewById(R.id.topNavigation);
		root.addView(firstMenu);
		
	    Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay(); 
		if (display.getRotation() == Surface.ROTATION_0 || display.getRotation() == Surface.ROTATION_180){
			hsv = (HorizontalScrollView) firstMenu.findViewById(R.id.hsv);
			disableOverscroll(hsv);
			linearFigIndicator = (LinearLayout) findViewById(R.id.fig_linear_indicator);
			linearFigIndicator.setPadding(0, 0, 0, 80);
		} else {
			linearFigIndicator = (LinearLayout) findViewById(R.id.fig_linear_indicator);
			linearFigIndicator.setPadding(0, 0, 0, 10);
		}
		

		firstMenu.findViewById(R.id.btn0).setOnClickListener(new CategoryClickedListener());
		firstMenu.findViewById(R.id.btn1).setOnClickListener(new CategoryClickedListener());
		firstMenu.findViewById(R.id.btn2).setOnClickListener(new CategoryClickedListener());
		firstMenu.findViewById(R.id.btn3).setOnClickListener(new CategoryClickedListener());

		ImageView next_btn = (ImageView) firstMenu.findViewById(R.id.next_btn);
		ImageView prev_btn = (ImageView) firstMenu.findViewById(R.id.prev_btn);
		next_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if(indexMenu < CAT_AUTO){
				//indexMenu++;
				//loadCategoryXml(categories[indexMenu]);
				//} 
				gallery.setSelection(gallery.getSelectedItemPosition() +1);
			}
		});
		prev_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if (indexMenu > CAT_CHASSIS){
				//indexMenu--;
				//loadCategoryXml(categories[indexMenu]);
				//}
				gallery.setSelection(gallery.getSelectedItemPosition() -1);
			}
		});

		list_app = (RelativeLayout) findViewById(R.id.list_app);
		list_app.setOnClickListener(new ListAppClickListener());

		pfa = new ProductFinderAdapter();
		pfla = new ProductFinderListAdapter();

		explist = (ExpandableListView) findViewById(R.id.listsystem);
		explist.setAdapter(pfa);
		explist.setOnChildClickListener(new ExpandableChildClickListener());

		lv = (ListView) findViewById(R.id.listsystem_subcategory);
		lv.setAdapter(pfla);
		lv.setOnItemClickListener(new ListItemClickListener());


		setIndicatorBounds();
		setGallery();
//		loadCategoryXml(CAT_CHASSIS);
	}

	private void loadCategoryXml(int id) {

		String xmlPath = "";

		switch (id) {
			case CAT_CHASSIS:{
//				indexMenu = CAT_CHASSIS;
				currentMenu = "Powertrain";
				gallery.setSelection(100000 * pfia.imagelist.length + CAT_CHASSIS);

				xmlPath = CategoryAssetsXml.Assets.POWERTRAIN_CHASSIS;
				resetBackground();
				firstMenu.findViewById(R.id.btn0).setBackgroundResource(R.drawable.product_selector_active);
				menubtn = (Button) firstMenu.findViewById(R.id.btn0);
				menubtn.setTextColor(Color.BLACK);
				break;
			}
			case CAT_INTERIOR:{
//				indexMenu = CAT_INTERIOR;
				currentMenu = "Interior";
				gallery.setSelection(100000 * pfia.imagelist.length + CAT_INTERIOR);

				xmlPath = CategoryAssetsXml.Assets.INTERIOR;
				resetBackground();
				firstMenu.findViewById(R.id.btn1).setBackgroundResource(R.drawable.product_selector_active);
				menubtn = (Button) firstMenu.findViewById(R.id.btn1);
				menubtn.setTextColor(Color.BLACK);
				break;
			}
			case CAT_EXTERIOR:{
//				indexMenu = CAT_EXTERIOR;
				currentMenu = "Exterior";
				gallery.setSelection(100000 * pfia.imagelist.length + CAT_EXTERIOR);

				xmlPath = CategoryAssetsXml.Assets.EXTERIOR;
				resetBackground();
				firstMenu.findViewById(R.id.btn2).setBackgroundResource(R.drawable.product_selector_active);
				menubtn = (Button) firstMenu.findViewById(R.id.btn2);
				menubtn.setTextColor(Color.BLACK);
				break;
			}
			case CAT_AUTO:{
//				indexMenu = CAT_AUTO;
				currentMenu = "Automotive";
				gallery.setSelection(100000 * pfia.imagelist.length + CAT_AUTO);

				xmlPath = CategoryAssetsXml.Assets.AUTOMOTIVE;
				resetBackground();
				firstMenu.findViewById(R.id.btn3).setBackgroundResource(R.drawable.product_selector_active);
				menubtn = (Button) firstMenu.findViewById(R.id.btn3);
				menubtn.setTextColor(Color.BLACK);
				break;
			}
		}
		try {
			inputstream = getAssets().open(xmlPath);
			mainCat = CategoryParser.getMainCategoryList(inputstream);

			if (mainCat.isMultiLevel.equals("true")){
				pfa.setContent(mainCat);
				pfa.notifyDataSetChanged();
				pfla.setContent(null);
				pfla.notifyDataSetChanged();
			} else if (mainCat.isMultiLevel.equals("false")){
				pfla.setContent(mainCat);
				pfla.notifyDataSetChanged();
				pfa.setContent(null);
				pfa.notifyDataSetChanged();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void resetBackground()
	{
		firstMenu.findViewById(R.id.btn0).setBackgroundResource(R.layout.top_navigation_selector);
		firstMenu.findViewById(R.id.btn1).setBackgroundResource(R.layout.top_navigation_selector);
		firstMenu.findViewById(R.id.btn2).setBackgroundResource(R.layout.top_navigation_selector);
		firstMenu.findViewById(R.id.btn3).setBackgroundResource(R.layout.top_navigation_selector);
		
		menubtn = (Button) firstMenu.findViewById(R.id.btn0);
		menubtn.setTextColor(Color.WHITE);
		menubtn = (Button) firstMenu.findViewById(R.id.btn1);
		menubtn.setTextColor(Color.WHITE);
		menubtn = (Button) firstMenu.findViewById(R.id.btn2);
		menubtn.setTextColor(Color.WHITE);
		menubtn = (Button) firstMenu.findViewById(R.id.btn3);
		menubtn.setTextColor(Color.WHITE);
	}

	private class ListItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> av, View view, int post, long id) {
			// TODO Auto-generated method stub
			Category cat = pfla.getItem(post);

			Intent intent = new Intent(ProductFinderActivity.this, ExpandableProductFinderActivity.class);
			intent.putExtra(BundleInformation.CurrentMenu, currentMenu);
			intent.putExtra(BundleInformation.File, cat.file);
			intent.putExtra(BundleInformation.GroupText, cat.text);
			isNewIntent = true;
			if(isNewIntent) startActivity(intent);
		}
	}

	private class ExpandableChildClickListener implements OnChildClickListener {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			Category childcat = pfa.getChild(groupPosition, childPosition);
			Category parentcat = pfa.getGroup(groupPosition);

			Intent intent = new Intent(ProductFinderActivity.this, ExpandableProductFinderActivity.class);
			intent.putExtra(BundleInformation.CurrentMenu, currentMenu);
			intent.putExtra(BundleInformation.File, childcat.file);
			intent.putExtra(BundleInformation.GroupText, parentcat.text);
			intent.putExtra(BundleInformation.ChildText, childcat.text);
			isNewIntent = true;
			if(isNewIntent) startActivity(intent);

			return false;
		}
	}

	private class CategoryClickedListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
				case R.id.btn0: {
					loadCategoryXml(CAT_CHASSIS);
					break;
				}
				case R.id.btn1: {
//					indexMenu = CAT_INTERIOR;
					loadCategoryXml(CAT_INTERIOR);
					break;
				}
				case R.id.btn2: {
//					indexMenu = CAT_EXTERIOR;
					loadCategoryXml(CAT_EXTERIOR);
					break;
				}
				case R.id.btn3: {
//					indexMenu = CAT_AUTO;
					loadCategoryXml(CAT_AUTO);
					break;
				}
				default:
					break;
			}
		}
	}

	private void setGallery() 
	{
		pfia = new ProductFinderImageAdapter(this);

		gallery = (OnFlingGallery) findViewById(R.id.gallery);
		gallery.setAnimationDuration(1000);
		gallery.setAdapter(pfia);
		gallery.setSelection((pfia.getCount()/20)*10);
		gallery.setOnItemSelectedListener(new GalleryOnItemSelectedListener());
		
		linearFigIndicator = (LinearLayout) findViewById(R.id.fig_linear_indicator);
		linearFigIndicator.removeAllViews();
		
		for(int i=0; i < pfia.imagelist.length; i++){
			fig_indicator = new ImageView(this);
			fig_indicator.setPadding(2, 0, 2, 0);
			linearFigIndicator.addView(fig_indicator);
		}
	}


	private class GalleryOnItemSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			String xmlPath = "";
			resetBackground();
			
			position = position % pfia.imagelist.length;
			
			switch(position){
				case 0: {
					xmlPath = CategoryAssetsXml.Assets.POWERTRAIN_CHASSIS;
					firstMenu.findViewById(R.id.btn0).setBackgroundResource(R.drawable.product_selector_active);
					menubtn = (Button) firstMenu.findViewById(R.id.btn0);
					menubtn.setTextColor(Color.BLACK);
					if (hsv != null) hsv.fullScroll(HorizontalScrollView.FOCUS_LEFT);
					break;
				}
				case 1: {
					xmlPath = CategoryAssetsXml.Assets.INTERIOR;
					firstMenu.findViewById(R.id.btn1).setBackgroundResource(R.drawable.product_selector_active);
					menubtn = (Button) firstMenu.findViewById(R.id.btn1);
					menubtn.setTextColor(Color.BLACK);
					break;
				}
				case 2: {
					xmlPath = CategoryAssetsXml.Assets.EXTERIOR;
					firstMenu.findViewById(R.id.btn2).setBackgroundResource(R.drawable.product_selector_active);
					menubtn = (Button) firstMenu.findViewById(R.id.btn2);
					menubtn.setTextColor(Color.BLACK);
					break;
				}
				case 3: {
					xmlPath = CategoryAssetsXml.Assets.AUTOMOTIVE;
					firstMenu.findViewById(R.id.btn3).setBackgroundResource(R.drawable.product_selector_active);
					menubtn = (Button) firstMenu.findViewById(R.id.btn3);
					menubtn.setTextColor(Color.BLACK);
					if (hsv != null) hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
					break;
				}
				default: break;
			}

			try {
				inputstream = getAssets().open(xmlPath);
				mainCat = CategoryParser.getMainCategoryList(inputstream);

				if (mainCat.isMultiLevel.equals("true")){
					pfa.setContent(mainCat);
					pfa.notifyDataSetChanged();
					pfla.setContent(null);
					pfla.notifyDataSetChanged();
				} else if (mainCat.isMultiLevel.equals("false")){
					pfla.setContent(mainCat);
					pfla.notifyDataSetChanged();
					pfa.setContent(null);
					pfa.notifyDataSetChanged();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i=0; i < pfia.imagelist.length; i++){
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

	private class ListAppClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			ImageView iv = (ImageView) findViewById(R.id.arr_status);
			TextView tv = (TextView) findViewById(R.id.list_app_status);

			if (isVisible){
				tv.setText("List Applications");
				iv.setImageResource(R.drawable.arrow_white_down);
				lv.setVisibility(View.GONE);
				explist.setVisibility(View.GONE);
				isVisible = false;
			} else {
				tv.setText("Hide Applications");
				iv.setImageResource(R.drawable.arrow_white_up);
				lv.setVisibility(View.VISIBLE);
				explist.setVisibility(View.VISIBLE);
				isVisible = true;
			}
		}
	}

	private void setIndicatorBounds() {
		// TODO Auto-generated method stub
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		explist.setIndicatorBounds(width - GetDipsFromPixel(40), width - GetDipsFromPixel(10));
	}

	public int GetDipsFromPixel(float pixels) {
		// TODO Auto-generated method stub
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (pixels * scale + 0.5f);
	}
	
	public void disableOverscroll(View view) {
	    Class<?> viewCls = view.getClass();
	    try {
	        Method m = viewCls.getMethod("setOverScrollMode", new Class[] { int.class });
	        int OVER_SCROLL_NEVER = (Integer) viewCls.getField("OVER_SCROLL_NEVER").get(view);
	        m.invoke(view, OVER_SCROLL_NEVER);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}



	//	@Override
	//	protected void onPause() {
	//		// TODO Auto-generated method stub
	//		LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
	//		super.onPause();
	//	}
	//
	//	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
	//		@Override
	//		public void onReceive(Context context, Intent intent) {
	//			// Get extra data included in the Intent
	//			int indexFling = intent.getIntExtra(BroadcastInformation.IndexMenu, 0);
	//			String xmlPath = "";
	//			resetBackground();
	//			if(indexFling == 0){
	//				xmlPath = CategoryAssetsXml.Assets.POWERTRAIN_CHASSIS;
	//				firstMenu.findViewById(R.id.btn0).setBackgroundResource(R.drawable.product_selector_active);
	//			} else if (indexFling == 1){
	//				xmlPath = CategoryAssetsXml.Assets.INTERIOR;
	//				firstMenu.findViewById(R.id.btn1).setBackgroundResource(R.drawable.product_selector_active);
	//			} else if (indexFling == 2){
	//				xmlPath = CategoryAssetsXml.Assets.EXTERIOR;
	//				firstMenu.findViewById(R.id.btn2).setBackgroundResource(R.drawable.product_selector_active);
	//			} else if (indexFling == 3){
	//				xmlPath = CategoryAssetsXml.Assets.AUTOMOTIVE;
	//				firstMenu.findViewById(R.id.btn3).setBackgroundResource(R.drawable.product_selector_active);
	//			}
	//
	//			try {
	//				inputstream = getAssets().open(xmlPath);
	//				mainCat = CategoryParser.getMainCategoryList(inputstream);
	//
	//				if (mainCat.isMultiLevel.equals("true")){
	//					pfa.setContent(mainCat);
	//					pfa.notifyDataSetChanged();
	//					pfla.setContent(null);
	//					pfla.notifyDataSetChanged();
	//				} else if (mainCat.isMultiLevel.equals("false")){
	//					pfla.setContent(mainCat);
	//					pfla.notifyDataSetChanged();
	//					pfa.setContent(null);
	//					pfa.notifyDataSetChanged();
	//				}
	//			} catch (Exception e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//		}
	//	};
}