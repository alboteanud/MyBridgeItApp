package com.craiovadata.mybridgeitapplication.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.craiovadata.mybridgeitapplication.dataSource.repositories.WeatherRepository;
import com.craiovadata.mybridgeitapplication.model.WeatherItem;

import java.util.List;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<List<WeatherItem>> weatherItems;

    public WeatherViewModel(WeatherRepository mRepository) {
        weatherItems = mRepository.getLatestWeather();
    }

    public MutableLiveData<List<WeatherItem>> getWeather() {
        return weatherItems;
    }

}

