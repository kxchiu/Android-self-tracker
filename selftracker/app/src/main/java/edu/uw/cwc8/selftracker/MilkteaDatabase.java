package edu.uw.cwc8.selftracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.sql.SQLDataException;

import static edu.uw.cwc8.selftracker.MilkteaDatabase.Helper.getHelper;

/**
 * Class to manage a database to store info about milktea drinking activity
 * Ideally this should be made into a Provider
 */

public final class MilkteaDatabase {

    private static final String TAG = "MilkteaDatabase";

    //empty constructor so cannot be called (so no new database will be created)
    public MilkteaDatabase(){}

    //defines the schema
    public static abstract class MilkteaEntry implements BaseColumns {
        //_ID = "_id"
        public static final String TABLE_NAME = "milktea";
        public static final String COL_TITLE = "title";
        public static final String COL_CUP = "cup";
        public static final String COL_TIME = "time"; //actual stored info is changed to Store Name instead
        public static final String COL_RATING = "rating";
        public static final String COL_TIMESTAMP = "timestamp";
    }

    //constant String for creating the milktea table
    public static final String CREATE_MILKTEA_TABLE =
            "CREATE TABLE " + MilkteaEntry.TABLE_NAME + "(" +
                    MilkteaEntry._ID + " INTEGER PRIMARY KEY" + "," +
                    MilkteaEntry.COL_TITLE + " TEXT" + "," +
                    MilkteaEntry.COL_CUP + " INTEGER" + "," +
                    MilkteaEntry.COL_TIME + " TEXT" + "," +
                    MilkteaEntry.COL_RATING + " INTEGER" + "," +
                    MilkteaEntry.COL_TIMESTAMP + " TEXT" +
                    ")";

    //constant String for dropping the milktea table
    public static final String DROP_FAVORITE_TABLE = "DROP TABLE IF EXISTS " + MilkteaEntry.TABLE_NAME;

    //code pulled from lab
    public static class Helper extends SQLiteOpenHelper {

        private static Helper instance;

        public static final String DATABASE_NAME = "milktea.db";
        public static final int DATABASE_VERSION = 1;

        public Helper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //singleton constructor!
        public static synchronized Helper getHelper(Context context){
            if(instance != null){
                instance = new Helper(context);
            }
            return instance;

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.v(TAG, "creating a milktea table");
            db.execSQL(CREATE_MILKTEA_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_FAVORITE_TABLE);
            onCreate(db);
        }
    }

    public static void addToDatabase(Context context, int cup, String time, int rating, String tStamp){
        Log.v("AddToDatabase", "Adding...");
        Helper helper =  new Helper(context);

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put(MilkteaEntry.COL_TITLE, "Milktea");
        content.put(MilkteaEntry.COL_CUP, cup);
        content.put(MilkteaEntry.COL_TIME, time);
        content.put(MilkteaEntry.COL_RATING, rating);
        content.put(MilkteaEntry.COL_TIMESTAMP, tStamp);
        try {
            long newRowId = db.insert(MilkteaEntry.TABLE_NAME, null, content);
            Log.v("Database", "" + newRowId);
        }catch (SQLiteConstraintException e){
            Log.v("Database", "Unexpected SQLite error: "+e);
        }
    }

    public static Cursor queryDatabase(Context context){
        Log.v("QueryDatabase", "Query from database");
        Helper helper = new Helper(context);

        SQLiteDatabase db = helper.getWritableDatabase();

        String descOrder = MilkteaEntry._ID+" DESC";

        //select query takes in 8 params: table to select from, column(s) to select, etc.
        //put null for place you want to leave blank

        //specify columns to select from the table
        String[] cols = new String[]{
                MilkteaEntry._ID,
                MilkteaEntry.COL_TITLE,
                MilkteaEntry.COL_CUP,
                MilkteaEntry.COL_TIME,
                MilkteaEntry.COL_RATING,
                MilkteaEntry.COL_TIMESTAMP
        };
        Cursor results = db.query(MilkteaEntry.TABLE_NAME, cols, null, null, null, null, descOrder, null);

        return results;
    }

}
