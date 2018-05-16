package com.danieldogeanu.android.newsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

        // Get Empty State views.
        View emptyStateView = findViewById(R.id.empty_state_view);
        ImageView emptyStateImageView = findViewById(R.id.empty_image_view);
        TextView emptyStateTextView = findViewById(R.id.empty_text_view);

        // Get the error message for Empty Bookmarks.
        String noBookmarksMsg = getString(R.string.no_bookmarks);

        // Set the EmptyState text and image icon.
        emptyStateImageView.setImageResource(R.drawable.ic_bookmark);
        emptyStateTextView.setText(noBookmarksMsg);

        // Initialize the Adapter with the Bookmarked articles.
        ListView bookmarksList = findViewById(R.id.bookmarks_list_view);
        ArticleAdapter adapter = new ArticleAdapter(BookmarksActivity.this, articles, bookmarks);
        bookmarksList.setAdapter(adapter);
        bookmarksList.setEmptyView(emptyStateView);
        bookmarks.setAdapter(adapter);

        // Attach Intent to open the Articles in the browser, on item click.
        bookmarksList.setOnItemClickListener((adapterView, view, i, l) -> {
            Article currentArticle = adapter.getItem(i);
            Utils.openWebsite(BookmarksActivity.this, currentArticle.getArticleUrl());
        });

    }
}
