package com.craiovadata.mybridgeitapplication.dataSource.network;

import com.craiovadata.mybridgeitapplication.model.WeatherItems;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class WeatherApiService {
    private WeatherServiceInterface weatherServiceInterface;
    private static WeatherApiService weatherApiService;

    private WeatherApiService() {
    }

    public static WeatherApiService getInstance() {
        if (weatherApiService == null) {
            weatherApiService = new WeatherApiService();
        }
        return weatherApiService;
    }

    public Call<WeatherItems> listWeather() {

        if (weatherServiceInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Configuration.baseUrl_daily)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            weatherServiceInterface = retrofit.create(WeatherServiceInterface.class);
        }
        Call<WeatherItems>  call = weatherServiceInterface.listWeather(Configuration.apiKey, Configuration.cityLat, Configuration.cityLon);
        return call;
    }

// https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}
// https://api.openweathermap.org/data/2.5/onecall?lat=44&lon=24&appid=d53bff8f3256eaf090be3c94964b0cb8
    interface WeatherServiceInterface {
        @Headers("Cache-Control: max-age=640000")
        @GET("/data/2.5/onecall")
        Call<WeatherItems> listWeather( @Query("appid") String apiKey,
                                     @Query("lat") String latitude,
                                     @Query("lon") String longitude
        );
    }

}
