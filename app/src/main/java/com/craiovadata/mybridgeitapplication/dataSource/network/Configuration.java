package com.craiovadata.mybridgeitapplication.dataSource.network;

import android.net.Uri;
import android.util.Log;

public class Configuration {
    private static final String TAG = Configuration.class.getSimpleName();
    public static final String newsUrl = "https://www.nytimes.com/svc/collections/v1/publish/https:/www.nytimes.com/section/world/";

    // OpenWeatherMap API
    public static final String apiKey = "d53bff8f3256eaf090be3c94964b0cb8";

    //    api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    public static final String baseUrl_currentWeather = "https://api.openweathermap.org/data/2.5/weather/";

    // pro.openweathermap.org/data/2.5/forecast/hourly?q={city name}&appid={API key}
//    public static final String baseUrl_hourlyForecast  = "https://pro.openweathermap.org/data/2.5/forecast/hourly/";
    public static final String baseUrl_hourlyForecast  = "https://pro.openweathermap.org";

    // https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}
    public static final String baseUrl_daily  = "https://api.openweathermap.org";

    public static final String cityName = "Craiova";
    public static final String cityLat = "44";
    public static final String cityLon = "24";


//    public static String getWeatherUrl() {
//        Uri uri = Uri.parse(baseUrl_hourlyForecast)
//                .buildUpon()
//                .appendQueryParameter("appid", apiKey)
//                .appendQueryParameter("city", cityName)
//                .build();
//
//        String url = uri.toString();
//        Log.d(TAG, "weather url: "+ url);
//        return url;
//    }

}
