package com.craiovadata.mybridgeitapplication.ui.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.craiovadata.mybridgeitapplication.R;

public class NewsWebViewActivity extends AppCompatActivity {

    public static final String ARG_WEB_LINK = "web_link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_view);

        String linkUrl = getIntent().getStringExtra(ARG_WEB_LINK);
        if (linkUrl == null) {
            String message = getString(R.string.link_unavailable_alert);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            WebView myWebView = (WebView) findViewById(R.id.id_news_webview);
            myWebView.loadUrl(linkUrl);
        }


    }
}