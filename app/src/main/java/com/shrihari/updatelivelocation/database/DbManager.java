package com.shrihari.updatelivelocation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.shrihari.updatelivelocation.database.Constants.ACCURACY;
import static com.shrihari.updatelivelocation.database.Constants.LATITUDE;
import static com.shrihari.updatelivelocation.database.Constants.LONGITUDE;

public class DbManager {
    /**
     * Singleton object of DbManager
     */
    private static DbManager dbManager = null;
    private Context context;
    private static DbHelper dbHelper;
    private SQLiteDatabase myDatabase;

    private DbManager(Context context) {
        this.context = context;
    }

    /**
     * Method to create the instance for the {@link DbManager} class. This
     * method should be called before the getInstance() method
     *
     * @param context Application context
     */
    public static void createInstance(Context context) {
        dbManager = new DbManager(context);
        dbHelper = new DbHelper(context);
    }

    /**
     * Method to get the singleton object of the {@link DbManager} class
     *
     * @return object of the {@link DbManager} class
     * @throws IllegalStateException if this method is called before calling the createInstance()
     *                               method.
     */
    public static DbManager getInstance() {
        // return dbManager;
        if (dbManager != null)
            return dbManager;
        else
            return null;
    }

    public void open() throws SQLException {
        myDatabase = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertRows(ContentValues values, String tableName) {
        myDatabase.insert(tableName, null, values);
    }


    public Cursor selectQuery(String query) throws SQLException {
        Cursor mCursor = myDatabase.rawQuery(query, null);
        mCursor.moveToFirst();
        return mCursor;
    }

    public void DeleteCases(String query) {
        myDatabase.execSQL(query);
    }

    public void insertLocationData(String lat, String lon,
                                   String acc, String tableName) {
        ContentValues data = new ContentValues();
        data.put(LATITUDE, lat);
        data.put(LONGITUDE, lon);
        data.put(ACCURACY, acc);
        insertRows(data, tableName);
    }


}
