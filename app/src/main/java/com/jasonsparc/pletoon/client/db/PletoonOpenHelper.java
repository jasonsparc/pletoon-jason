package com.jasonsparc.pletoon.client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jasonsparc.pletoon.client.cookies.CookieEntry;

import static com.jasonsparc.pletoon.sqlbrite.BriteUtils.getDropIfExists;

/**
 * Created by jasonsparc on 4/22/2016.
 */
public class PletoonOpenHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Pletoon.db";

	PletoonOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CookieEntry.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// The upgrade policy for cookies is to simply discard the data and start over.
		db.execSQL(getDropIfExists(CookieEntry.TABLE_NAME));

		onCreate(db);
	}
}
