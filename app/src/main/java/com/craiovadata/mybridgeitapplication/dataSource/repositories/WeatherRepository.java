package com.craiovadata.mybridgeitapplication.dataSource.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.craiovadata.mybridgeitapplication.dataSource.WeatherDatabase;
import com.craiovadata.mybridgeitapplication.dataSource.network.AppExecutor;
import com.craiovadata.mybridgeitapplication.dataSource.network.NetworkDataSource;
import com.craiovadata.mybridgeitapplication.model.WeatherItem;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class WeatherRepository {
    private static final String TAG = WeatherRepository.class.getSimpleName();
    private WeatherDatabase weatherDatabase;
    private static WeatherRepository repository;
    private final NetworkDataSource networkDataSource;
    private final AppExecutor mExecutor;

    // private constructor restricted to this class itself
    private WeatherRepository(WeatherDatabase weatherDatabase, NetworkDataSource networkDataSource, AppExecutor appExecutor) {
        this.weatherDatabase = weatherDatabase;
        this.mExecutor = appExecutor;
        this.networkDataSource = networkDataSource;
    }


    // static method to create instance of Repository class
    private static WeatherRepository getInstance(WeatherDatabase weatherDatabase, NetworkDataSource networkDataSource, AppExecutor appExecutor) {
        if (repository == null) {

            repository = new WeatherRepository(weatherDatabase, networkDataSource, appExecutor);
        }

        return repository;
    }


    public MutableLiveData<List<WeatherItem>> getLatestWeather() {
//        weatherDatabase.weatherDao().getAll();

        if (isFetchWeatherNeeded()){
            fetchLatestWeather();
        }

        return networkDataSource.mDownloadedWeather;
    }

    private void fetchLatestWeather() {
        if (isFetchWeatherNeeded()) {
            Log.d(TAG, "Fetching the latest weather");
            mExecutor.execute(networkDataSource::downloadWeather);
        }

    }

    private boolean isFetchWeatherNeeded() {
        // TODO check timestamp - latest news added to DB
        return true;
    }


    public static WeatherRepository provideRepository(Context context) {
        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(context.getApplicationContext());
        NetworkDataSource networkDataSource = NetworkDataSource.getInstance();
        Executor executor = Executors.newSingleThreadExecutor();
        AppExecutor appExecutor = new AppExecutor(executor);

        return WeatherRepository.getInstance(weatherDatabase, networkDataSource, appExecutor);

    }


}
