package com.basf.catalog.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class OnFlingGallery extends Gallery {
	
//	private Context myContext;

//	int indexMenu = 0;
	
	public OnFlingGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
//		myContext = context;
	}
	
//	private void sendMessage(int indexMenu){
//		
//		Intent intent = new Intent(BroadcastInformation.IntentName);
//		intent.putExtra(BroadcastInformation.IndexMenu, indexMenu);
//		LocalBroadcastManager.getInstance(myContext).sendBroadcast(intent);
//	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		setAnimationDuration(500);
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		float velMax = 2500f;
		float velMin = 1000f;
		float velX = Math.abs(velocityX);
		if (velX > velMax) {
			velX = velMax;
		} else if (velX < velMin) {
			velX = velMin;
		}
		velX -= 600;
		setAnimationDuration(500);

		int kEvent;
		if (isScrollingLeft(e1, e2)) {
			// Check if scrolling left
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
//			if (indexMenu > 0) {
//				indexMenu--;
//				sendMessage(indexMenu);
//			}
		} else {
			// Otherwise scrolling right
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
//			if (indexMenu < 3) {
//				indexMenu++;
//				sendMessage(indexMenu);
//			}
		}
		onKeyDown(kEvent, null);

		return true;
	}
}