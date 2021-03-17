package com.craiovadata.mybridgeitapplication.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.craiovadata.mybridgeitapplication.model.NewsItem;
import com.craiovadata.mybridgeitapplication.dataSource.repositories.NewsRepository;

import java.util.List;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<List<NewsItem>> newsItems;

    public NewsViewModel() {
        newsItems = NewsRepository.getInstance().getNews();
    }

    public MutableLiveData<List<NewsItem>> getNews() {
        return newsItems;
    }

}