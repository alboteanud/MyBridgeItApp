package com.craiovadata.mybridgeitapplication.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.craiovadata.mybridgeitapplication.dataSource.repositories.WeatherRepository;

public class WeatherViewModelFactory implements ViewModelProvider.Factory {
    WeatherRepository mRepository;

    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WeatherViewModel(mRepository) ;
    }

    public WeatherViewModelFactory(WeatherRepository mRepository) {
        this.mRepository = mRepository;
    }
}
