package com.basf.catalog.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.basf.catalog.R;

public class SplashScreen extends Activity
{
	private VideoView mView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		mView = (VideoView) findViewById(R.id.video);
		mView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer player)
			{
				finish();
				Intent mainIntent = new Intent().setClass(SplashScreen.this, MainMenuActivity.class);
				startActivity(mainIntent);
			}
		});
		mView.setVideoURI(Uri.parse("android.resource://com.basf.catalog/" + R.raw.intro));
		mView.start();
	}
}
