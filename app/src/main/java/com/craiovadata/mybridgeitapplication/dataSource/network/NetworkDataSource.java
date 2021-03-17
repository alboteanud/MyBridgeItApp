package com.craiovadata.mybridgeitapplication.dataSource.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.craiovadata.mybridgeitapplication.model.NewsItem;
import com.craiovadata.mybridgeitapplication.model.NewsItems;
import com.craiovadata.mybridgeitapplication.model.WeatherItem;
import com.craiovadata.mybridgeitapplication.model.WeatherItems;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class NetworkDataSource {
    private static final String TAG = NetworkDataSource.class.getSimpleName();
    private static NetworkDataSource networkDataSource;

    // LiveData storing the latest downloaded entries
    public final MutableLiveData<List<NewsItem>> mDownloadedNews = new MutableLiveData<>();
    public final MutableLiveData<List<WeatherItem>> mDownloadedWeather = new MutableLiveData<>();

    private NetworkDataSource() {
    }

    public static NetworkDataSource getInstance() {
        if (networkDataSource == null) {
            networkDataSource = new NetworkDataSource();
        }
        return networkDataSource;
    }

    public void downloadNews() {
        Call<NewsItems> call = NewsApiService.getInstance().listNews();
        call.enqueue(new Callback<NewsItems>() {
            @Override
            public void onResponse(Call<NewsItems> call, Response<NewsItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "yeee!!! We have news data.");
                    List<NewsItem> latestNews = response.body().getNews();
                    mDownloadedNews.postValue(latestNews);
                }

            }

            @Override
            public void onFailure(Call<NewsItems> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                // TODO do something
            }
        });
    }

    public void downloadWeather() {

        Call<WeatherItems> call = WeatherApiService.getInstance().listWeather();
//        HttpUrl url = call.request().url();
//     Log.d(TAG, url.toString());

        call.enqueue(new Callback<WeatherItems>() {
            @Override
            public void onResponse(Call<WeatherItems> call, Response<WeatherItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "yeee!!! We have weather data: " + response.raw());
//                    List<WeatherItem> latestWeather = response.body().getWeather();
//                    mDownloadedWeather.postValue(list);
                    // TODO Custom GSON Converter Factory

                    // inserting Dummy weather
                    WeatherItem weatherItem = new WeatherItem();
                    weatherItem.wind_speed = 3.1;

                    List<WeatherItem> list = new ArrayList<>();
                    list.add(weatherItem);
                    mDownloadedWeather.postValue(list);
                }

            }

            @Override
            public void onFailure(Call<WeatherItems> call, Throwable t) {
                Log.e(TAG, t.getMessage() + t.toString());
                // TODO something

                // inserting Dummy weather
                WeatherItem weatherItem = new WeatherItem();
                weatherItem.wind_speed = 4.1;

                List<WeatherItem> list = new ArrayList<>();
                list.add(weatherItem);
                mDownloadedWeather.postValue(list);
            }
        });

    }


}
