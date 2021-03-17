package com.craiovadata.mybridgeitapplication.dataSource;


import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.craiovadata.mybridgeitapplication.model.WeatherItem;

@Database(entities = {WeatherItem.class}, version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {

    private static final String TAG = WeatherDatabase.class.getSimpleName();
    private static WeatherDatabase weatherDatabase = null;

    public abstract WeatherDao weatherDao();

    public static WeatherDatabase getInstance(Context context) {
        Log.d(TAG, "Getting the database");
        if (weatherDatabase == null) {
            weatherDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    WeatherDatabase.class, "weather-database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return weatherDatabase;
    }

}
