package com.danieldogeanu.android.newsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Class that initializes the Bookmarks Activity for the app.
 */
public class BookmarksActivity extends AppCompatActivity {

    /**
     * Overrides the onCreate() method to assemble and display the Bookmarks Activity.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        // Get Bookmarks instance and extract bookmarked articles.
        Bookmarks bookmarks = Bookmarks.getInstance();
        ArrayList<Article> articles = bookmarks.getBookmarks();

        // Initialize the Adapter with the Bookmarked articles.
        ListView bookmarksList = findViewById(R.id.bookmarks_list_view);
        ArticleAdapter adapter = new ArticleAdapter(BookmarksActivity.this, articles, bookmarks);
        bookmarksList.setAdapter(adapter);

        // Attach Intent to open the Articles in the browser, on item click.
        bookmarksList.setOnItemClickListener((adapterView, view, i, l) -> {
            Article currentArticle = adapter.getItem(i);
            Utils.openWebsite(BookmarksActivity.this, currentArticle.getArticleUrl());
        });

    }
}
