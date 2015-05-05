package com.basf.catalog.ui.favorite;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.basf.catalog.R;
import com.basf.catalog.component.BaseActivity;
import com.basf.catalog.service.core.structs.BundleInformation;
import com.basf.catalog.service.core.structs.BundleInformation.FavoriteColumns;
import com.basf.catalog.service.core.structs.FavoriteItem;
import com.basf.catalog.service.core.structs.MainQuiz;
import com.basf.catalog.ui.favorite.adapter.FavoriteAdapter;
import com.basf.catalog.ui.productfinder.DetailProductFinderActivity;
import com.basf.catalog.util.IOUtils;

public class FavoriteActivity extends BaseActivity {

	TextView txt;
	ListView lv;

	InputStream inputstream;

	MainQuiz mainQuiz;
	FavoriteAdapter fa;


	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.favorite;
	}
	
	@Override
	protected int getCurrentMenu() {
		// TODO Auto-generated method stub
		return 5;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		fa.setContent(getFavoriteList());
		fa.notifyDataSetChanged();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.favorite);

		txt = (TextView) findViewById(R.id.title);
		txt.setText("Favorites");

		fa = new FavoriteAdapter();
		fa.setContent(getFavoriteList());

		lv = (ListView) findViewById(R.id.list);
		lv.setAdapter(fa);
		lv.setOnItemClickListener(new ListItemClickListener());
	}

	private ArrayList<FavoriteItem> getFavoriteList()
	{
		Cursor cursor = null;
		try
		{
			cursor = getContentResolver().query(FavoriteColumns.CONTENT_URI, FavoriteColumns.QUERY_SHORT, null, null, null);
			if (cursor != null && cursor.getCount() > 0)
			{
				int columnName = cursor.getColumnIndexOrThrow(FavoriteColumns.NAME);
				int columnPolymer = cursor.getColumnIndexOrThrow(FavoriteColumns.POLYMER);
				int columnXmlPath = cursor.getColumnIndexOrThrow(FavoriteColumns.XML_PATH);

				ArrayList<FavoriteItem> list = new ArrayList<FavoriteItem>(cursor.getCount());
				while (cursor.moveToNext())
				{
					FavoriteItem fav = new FavoriteItem();
					fav.name = cursor.getString(columnName);
					fav.polymer = cursor.getString(columnPolymer);
					fav.xml_path = cursor.getString(columnXmlPath);

					list.add(fav);
				}

				return list;
			}
			else
			{
				return null;
			}
		}
		finally
		{
			IOUtils.close(cursor);
		}
	}

	private class ListItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> av, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			FavoriteItem fav = fa.getItem(position);

			Intent intent = new Intent(FavoriteActivity.this, DetailProductFinderActivity.class);
			intent.putExtra(BundleInformation.DataFile, fav.xml_path);
			intent.putExtra(BundleInformation.DataName, fav.name);
			intent.putExtra(BundleInformation.DataPolymer, fav.polymer);
			startActivity(intent);
		}
	}

}