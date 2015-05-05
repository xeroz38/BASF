package com.basf.catalog.component;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.basf.catalog.R;
import com.basf.catalog.ui.contactus.ContactUsActivity;
import com.basf.catalog.ui.favorite.FavoriteActivity;
import com.basf.catalog.ui.productfinder.ProductFinderActivity;
import com.basf.catalog.ui.quiz.QuizActivity;
import com.basf.catalog.ui.technicalsupport.TechnicalSupportActivity;
import com.basf.catalog.ui.troubleshooter.TroubleshooterActivity;

public abstract class BaseActivity extends Activity {

	private ImageView pfinder;
	private ImageView troubleshooter;
	private ImageView tservices;
	private ImageView quiz;
	private ImageView favorite;
	private ImageView contact;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResourceId());

		MenuListClickListener menuClick = new MenuListClickListener();

		pfinder = (ImageView) findViewById(R.id.product_finder_id);
		pfinder.setOnClickListener(menuClick);

		troubleshooter = (ImageView) findViewById(R.id.troubleshooter_id);
		troubleshooter.setOnClickListener(menuClick);

		tservices = (ImageView) findViewById(R.id.technical_services_id);
		tservices.setOnClickListener(menuClick);

		quiz = (ImageView) findViewById(R.id.quiz_id);
		quiz.setOnClickListener(menuClick);

		favorite = (ImageView) findViewById(R.id.favorite_id);
		favorite.setOnClickListener(menuClick);

		contact = (ImageView) findViewById(R.id.contact_us_id);
		contact.setOnClickListener(menuClick);
		
		setCurrentIndicatorMenu();
	}

	protected abstract int getLayoutResourceId();
	protected abstract int getCurrentMenu();
	
	
	private void setCurrentIndicatorMenu() {
		// TODO Auto-generated method stub
		resetBackground();
		
		switch(getCurrentMenu())
		{
			case 1:
			{
				pfinder.setBackgroundResource(R.drawable.bottom_bar_active);
				break;
			}
			case 2:
			{
				troubleshooter.setBackgroundResource(R.drawable.bottom_bar_active);
				break;
			}
			case 3:
			{
				tservices.setBackgroundResource(R.drawable.bottom_bar_active);
				break;
			}
			case 4:
			{
				quiz.setBackgroundResource(R.drawable.bottom_bar_active);
				break;
			}
			case 5:
			{
				favorite.setBackgroundResource(R.drawable.bottom_bar_active);
				break;
			}
			case 6:
			{
				contact.setBackgroundResource(R.drawable.bottom_bar_active);
				break;
			}
			default:break;
		}
	}

	private class MenuListClickListener implements View.OnClickListener{

		public void onClick(View view){
			switch (view.getId())
			{
				case R.id.product_finder_id:
				{
					Intent intent = new Intent(BaseActivity.this, ProductFinderActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					ProductFinderActivity.isNewIntent = false;
					startActivity(intent);
					break;
				}
				case R.id.troubleshooter_id:
				{
					Intent intent = new Intent(BaseActivity.this, TroubleshooterActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);	
					break;
				}
				case R.id.technical_services_id:
				{
					Intent intent = new Intent(BaseActivity.this, TechnicalSupportActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);	
					break;
				}
				case R.id.quiz_id:
				{
					Intent intent = new Intent(BaseActivity.this, QuizActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);					
					break;
				}
				case R.id.favorite_id:
				{
					Intent intent = new Intent(BaseActivity.this, FavoriteActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);						
					break;
				}
				case R.id.contact_us_id:
				{
					Intent intent = new Intent(BaseActivity.this, ContactUsActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					break;
				}
				default: break;
			}
		}
	}
	
	private void resetBackground()
	{
		pfinder.setBackgroundResource(Color.TRANSPARENT);
		troubleshooter.setBackgroundResource(Color.TRANSPARENT);
		tservices.setBackgroundResource(Color.TRANSPARENT);
		quiz.setBackgroundResource(Color.TRANSPARENT);
		favorite.setBackgroundResource(Color.TRANSPARENT);
		contact.setBackgroundResource(Color.TRANSPARENT);
	}
}