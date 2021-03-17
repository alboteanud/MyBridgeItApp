package com.craiovadata.mybridgeitapplication.dataSource;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.craiovadata.mybridgeitapplication.model.WeatherItem;

import java.util.Date;
import java.util.List;

@Dao
public interface WeatherDao {

        @Query("SELECT * FROM weatherItem")
        List<WeatherItem> getAll();

        @TypeConverters(DateConverter.class)
        @Query("SELECT * FROM weatherItem WHERE dt >= :dt ORDER BY dt ASC LIMIT 7")
        WeatherItem getLatest(Long dt);

        @Insert
        void insertAll(WeatherItem... weatherItems);

        @Delete
        void delete(WeatherItem weatherItem);
    }
