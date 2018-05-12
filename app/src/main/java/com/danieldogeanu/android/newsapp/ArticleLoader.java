package com.danieldogeanu.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Class used to load the Articles from the given URL, in the background.
 */
public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    private String mUrl;

    /**
     * ArticleLoader constructor. Accepts 2 parameters.
     * @param context The context from which this class is called.
     * @param url The URL from which to retrieve the articles.
     */
    public ArticleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    /** Overrides the onStartLoading() method in order to force the loading. */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * Overrides the loadInBackground() method in order to retrieve the articles list from the given URL.
     * @return Returns the list of articles retrieved.
     */
    @Override
    public List<Article> loadInBackground() {
        if (mUrl == null) return null;
        return QueryUtils.fetchNews(mUrl);
    }
}
