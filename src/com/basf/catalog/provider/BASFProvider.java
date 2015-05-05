package com.basf.catalog.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.basf.catalog.service.core.structs.BundleInformation.FavoriteColumns;
import com.basf.catalog.util.AutoCloseCursorFactory;

public class BASFProvider extends ContentProvider
{
	private final String TABLE_FAVORITE = "favorite";

	class BASFSQLiteOpenHelper extends SQLiteOpenHelper
	{
		private static final int BASF_DB_VERSION = 1;

		public BASFSQLiteOpenHelper(Context context, SQLiteDatabase.CursorFactory cursorFactory)
		{
			super(context, "BASF", cursorFactory, BASF_DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITE + " ("
					+ FavoriteColumns.NAME + " TEXT PRIMARY KEY,"
					+ FavoriteColumns.POLYMER + " TEXT,"
					+ FavoriteColumns.XML_PATH + " TEXT,"
					+ "UNIQUE (" + FavoriteColumns.NAME + ") ON CONFLICT REPLACE)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
			db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITE + " ("
					+ FavoriteColumns.NAME + " TEXT PRIMARY KEY AUTOINCREMENT,"
					+ FavoriteColumns.POLYMER + " TEXT,"
					+ FavoriteColumns.XML_PATH + " TEXT,"
					+ "UNIQUE (" + FavoriteColumns.NAME + ") ON CONFLICT REPLACE)");
		}
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] columns, String selection, String[] selectionArgs, String sort)
	{
		BASFSQLiteOpenHelper openHelper = new BASFSQLiteOpenHelper(getContext(), new AutoCloseCursorFactory());
		SQLiteDatabase db = openHelper.getWritableDatabase();

		if (sort == null){
			sort = FavoriteColumns.NAME + " DESC";
		}

		return db.query(TABLE_FAVORITE, columns, selection, selectionArgs, null, null, sort);
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		BASFSQLiteOpenHelper openHelper = new BASFSQLiteOpenHelper(getContext(), null);
		SQLiteDatabase db = openHelper.getWritableDatabase();

		db.beginTransaction();

		db.insert(TABLE_FAVORITE, null, values);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		String favoriteId = values.getAsString(FavoriteColumns.ID);

		return FavoriteColumns.CONTENT_URI.buildUpon().appendPath(favoriteId).build();
	}


	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		BASFSQLiteOpenHelper openHelper = new BASFSQLiteOpenHelper(getContext(), null);
		SQLiteDatabase db = openHelper.getWritableDatabase();

		db.beginTransaction();

		db.delete(TABLE_FAVORITE, selection, selectionArgs);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
		
		return 1;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
}
