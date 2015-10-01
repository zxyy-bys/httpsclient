package com.example.baiyongshu.httpsclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;

/**
 * Created by baiyongshu on 9/2/15.
 */
public class  WebViewActivity extends ActionBarActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = new WebView(this);
        setContentView(webview);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {

            // Get endResult
            String htmlString = extras.getString("htmlString");
            webview.loadData(htmlString, "text/html", "utf-8");
        }
    }
}
