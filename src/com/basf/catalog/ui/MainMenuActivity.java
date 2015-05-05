package com.basf.catalog.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.basf.catalog.R;
import com.basf.catalog.ui.contactus.ContactUsActivity;
import com.basf.catalog.ui.favorite.FavoriteActivity;
import com.basf.catalog.ui.productfinder.ProductFinderActivity;
import com.basf.catalog.ui.quiz.QuizActivity;
import com.basf.catalog.ui.technicalsupport.TechnicalSupportActivity;
import com.basf.catalog.ui.troubleshooter.TroubleshooterActivity;

public class MainMenuActivity extends Activity {

	private LinearLayout linearlayout;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MenuListClickListener listener = new MenuListClickListener();
        
        linearlayout = (LinearLayout) findViewById(R.id.product_finder_id);
        linearlayout.setOnClickListener(listener);
        
        linearlayout = (LinearLayout) findViewById(R.id.troubleshooter_id);
        linearlayout.setOnClickListener(listener);
        
        linearlayout = (LinearLayout) findViewById(R.id.technical_services_id);
        linearlayout.setOnClickListener(listener);
        
        linearlayout = (LinearLayout) findViewById(R.id.quiz_id);
        linearlayout.setOnClickListener(listener);
        
        linearlayout = (LinearLayout) findViewById(R.id.favorite_id);
        linearlayout.setOnClickListener(listener);
        
        linearlayout = (LinearLayout) findViewById(R.id.contact_us_id);
        linearlayout.setOnClickListener(listener);
    }
	
	private class MenuListClickListener implements View.OnClickListener{
		
		public void onClick(View view){
			switch (view.getId())
			{
				case R.id.product_finder_id:
				{
					Intent intent = new Intent(MainMenuActivity.this, ProductFinderActivity.class);
					startActivity(intent);
					break;
				}
				case R.id.troubleshooter_id:
				{
					Intent intent = new Intent(MainMenuActivity.this, TroubleshooterActivity.class);
					startActivity(intent);	
					break;
				}
				case R.id.technical_services_id:
				{
					Intent intent = new Intent(MainMenuActivity.this, TechnicalSupportActivity.class);
					startActivity(intent);	
					break;
				}
				case R.id.quiz_id:
				{
					Intent intent = new Intent(MainMenuActivity.this, QuizActivity.class);
					startActivity(intent);					
					break;
				}
				case R.id.favorite_id:
				{
					Intent intent = new Intent(MainMenuActivity.this, FavoriteActivity.class);
					startActivity(intent);						
					break;
				}
				case R.id.contact_us_id:
				{
					Intent intent = new Intent(MainMenuActivity.this, ContactUsActivity.class);
					startActivity(intent);
					break;
				}
				default: break;
			}
		}
	}
}