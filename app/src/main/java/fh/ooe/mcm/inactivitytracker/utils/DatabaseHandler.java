package fh.ooe.mcm.inactivitytracker.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fh.ooe.mcm.inactivitytracker.interfaces.Observable;
import fh.ooe.mcm.inactivitytracker.interfaces.Observer;

public class DatabaseHandler extends SQLiteOpenHelper implements Observer, Observable {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "database";

    private static final String TABLE_ACTIVITIES = "activities";

    //private static final String KEY_ID = "id"; ??
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_ACTIVITY = "activity";

    ArrayList<Observer> observers;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        observers = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_ACTIVITIES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ACTIVITIES + "("
                    //+ KEY_ID + " INTEGER PRIMARY KEY,"
                    + KEY_TIMESTAMP + " INTEGER,"
                    + KEY_ACTIVITY + " TEXT" + ")";
            db.execSQL(CREATE_ACTIVITIES_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES);

        onCreate(db);
    }

    public void addActivity(Long timestamp, String activity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIMESTAMP, timestamp);
        values.put(KEY_ACTIVITY, activity);

        db.insert(TABLE_ACTIVITIES, null, values);
        db.close();
    }

    public Map<Long, String> getAllPhysicalActivitiesForDays(long timestampFrom, long timestampTo) {
        if(timestampTo == 0) {
            timestampTo = System.currentTimeMillis();
        }
        Map<Long, String> activities = new LinkedHashMap<>();

        String selectQuery = "SELECT  * FROM " + TABLE_ACTIVITIES +
                                " WHERE " + KEY_TIMESTAMP  + " BETWEEN " + timestampFrom +
                                " AND " + timestampTo + " ORDER BY " + KEY_TIMESTAMP + " ASC ";

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    activities.put(cursor.getLong(0), cursor.getString(1));
                } while (cursor.moveToNext());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return activities;
    }

    public void deletePhysicalActivities(long timestampFrom, long timestampTo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACTIVITIES, KEY_TIMESTAMP + " >= ? AND" + KEY_TIMESTAMP + "<= ?",
                new String[]{String.valueOf(timestampFrom), String.valueOf(timestampTo)});
        db.close();
    }

    public void dropTable() {
        this.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES);
    }

    @Override
    public void update(Observable observable, Object object) {
       if(observable instanceof Recognizer) {
            if(object instanceof Map) {
                Map<Long, String> activities = (ConcurrentHashMap) object;
                for(Map.Entry<Long, String> activity: activities.entrySet()) {
                    addActivity(activity.getKey(), activity.getValue());
                }
            }
       }
    }

    @Override
    public void notifyAll(Object object) {
        for (Observer observer: observers) {
            observer.update(this, object);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
}