package com.craiovadata.mybridgeitapplication.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class WeatherItems {

    @SerializedName("current")
    private Current current;

    @SerializedName("daily")
    private Daily daily;

    public List<WeatherItem> getWeather() {
        if (daily != null)
            return daily.getList();

        WeatherItem weatherItem = new WeatherItem();
        weatherItem.wind_speed = 3.1;

        List<WeatherItem> dummyList = new ArrayList<>();
        dummyList.add(weatherItem);

        return dummyList;
    }

    // TODO Custom GSON Converter Factory
    private static class Daily {

        public List<WeatherItem> getList() {
            return list;
        }

        @SerializedName("id")
        private List<WeatherItem> list;

    }

    private static class Current {

        @SerializedName("temp")
        Double temp;


    }


}
