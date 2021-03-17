package com.craiovadata.mybridgeitapplication.dataSource.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.craiovadata.mybridgeitapplication.dataSource.network.AppExecutor;
import com.craiovadata.mybridgeitapplication.dataSource.network.NetworkDataSource;
import com.craiovadata.mybridgeitapplication.model.NewsItem;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class NewsRepository {
    private static final String TAG = NewsRepository.class.getSimpleName();
    private static NewsRepository newsRepository;
    private final NetworkDataSource networkDataSource;
    private final AppExecutor mExecutor;

    // private constructor restricted to this class itself
    private NewsRepository(NetworkDataSource networkDataSource, AppExecutor appExecutor) {
        this.mExecutor = appExecutor;
        this.networkDataSource = networkDataSource;
    }

    // static method to create instance of Repository class
    public static NewsRepository getInstance() {
        if (newsRepository == null) {
            NetworkDataSource networkDataSource = NetworkDataSource.getInstance();
            Executor executor = Executors.newSingleThreadExecutor();
            AppExecutor appExecutor = new AppExecutor(executor);

            newsRepository = new NewsRepository(networkDataSource, appExecutor);
        }

        return newsRepository;
    }


    public MutableLiveData<List<NewsItem>> getNews() {
        fetchLatestNews();
        return networkDataSource.mDownloadedNews;
    }

    private void fetchLatestNews() {
        if (isFetchNewsNeeded()){
            Log.d(TAG, "Fetching the latest news");
            mExecutor.execute(networkDataSource::downloadNews);
        }

    }

    private boolean isFetchNewsNeeded() {
        // TODO check timestamp - latest news added to DB
        return true;
    }


}
