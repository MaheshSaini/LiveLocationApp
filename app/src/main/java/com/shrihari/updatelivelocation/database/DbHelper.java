package com.shrihari.updatelivelocation.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "location_database.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    private static final String DATABASE_CREATE_tbl_Notification = " create table "
            + Constants.TABLE_LOCATION
            + "("
            + Constants.ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT not null,"
            + Constants.LATITUDE
            + " text,"
            + Constants.LONGITUDE
            + " text,"
            + Constants.ACCURACY
            + " text);";

    /**
     * this method is called when the database is created first time
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_tbl_Notification);
    }

    /**
     * this method is called when the database version is changed
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_LOCATION);
        onCreate(db);
    }

}
