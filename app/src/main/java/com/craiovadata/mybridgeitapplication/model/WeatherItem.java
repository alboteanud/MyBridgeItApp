package com.craiovadata.mybridgeitapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class WeatherItem {

    @PrimaryKey
    public Long dt;

    @ColumnInfo(name = "sunrise")
    @SerializedName("sunrise")
    public Long sunrise;

    @ColumnInfo(name = "wind_speed")
    @SerializedName("wind_speed")
    public Double wind_speed;





}


