package com.craiovadata.mybridgeitapplication.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.craiovadata.mybridgeitapplication.R;
import com.craiovadata.mybridgeitapplication.dataSource.repositories.WeatherRepository;
import com.craiovadata.mybridgeitapplication.model.WeatherItem;
import com.craiovadata.mybridgeitapplication.viewModel.WeatherViewModel;
import com.craiovadata.mybridgeitapplication.viewModel.WeatherViewModelFactory;

import java.util.List;

public class WeatherFragment extends Fragment {

    private WeatherViewModel weatherViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        WeatherRepository weatherRepository = WeatherRepository.provideRepository(getContext());
        ViewModelProvider.Factory factory = new WeatherViewModelFactory(weatherRepository);
        weatherViewModel = new ViewModelProvider(this, factory).get(WeatherViewModel.class);
        View root = inflater.inflate(R.layout.fragment_weather, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        weatherViewModel.getWeather().observe(getViewLifecycleOwner(), new Observer<List<WeatherItem>>() {
            @Override
            public void onChanged(List<WeatherItem> weatherItems) {
                if (weatherItems != null && weatherItems.size() > 0)
                    textView.setText(weatherItems.get(0).wind_speed.toString());
            }
        });
        return root;
    }



}