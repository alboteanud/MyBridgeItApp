package com.craiovadata.mybridgeitapplication.dataSource.network;

import com.craiovadata.mybridgeitapplication.model.NewsItems;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public class NewsApiService {
    private NewsServiceInterface newsServiceInterface;
    private static NewsApiService newsApiService;

    private NewsApiService() {
    }

    public static NewsApiService getInstance() {
        if (newsApiService == null) {
            newsApiService = new NewsApiService();
        }
        return newsApiService;
    }

    public Call<NewsItems> listNews() {
        if (newsServiceInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Configuration.newsUrl)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
            newsServiceInterface = retrofit.create(NewsServiceInterface.class);
        }
        return newsServiceInterface.listNews();
    }


    interface NewsServiceInterface {
        @Headers("Cache-Control: max-age=640000")
        @GET("rss.xml")
        Call<NewsItems> listNews();
    }
}
