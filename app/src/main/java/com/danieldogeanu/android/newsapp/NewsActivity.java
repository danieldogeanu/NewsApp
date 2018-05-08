package com.danieldogeanu.android.newsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ArrayList<Article> articles = new ArrayList<>();
        String placeholderHeadline = getString(R.string.placeholder_headline);
        for (int i = 0; i < 10; i++) {
            articles.add(new Article(placeholderHeadline, "", "", ""));
        }

        ListView listView = findViewById(R.id.newsList);
        ArticleAdapter adapter = new ArticleAdapter(this, articles);
        listView.setAdapter(adapter);
    }
}
