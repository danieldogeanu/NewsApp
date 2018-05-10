package com.danieldogeanu.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    private static final String LOG_TAG = NewsActivity.class.getSimpleName();
    private static final int LOADER_ID = 1;

    private static final String API_URL = "https://content.guardianapis.com/search";
    private static final String API_KEY = BuildConfig.API_KEY;

    private ArticleAdapter mAdapter;
    private View mEmptyStateView;
    private TextView mEmptyStateTextView;
    private View mLoadingIndicator;

    private ConnectivityManager mConnectivityManager;
    private NetworkInfo mActiveNetwork;
    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mActiveNetwork = mConnectivityManager.getActiveNetworkInfo();
        isConnected = mActiveNetwork != null && mActiveNetwork.isConnectedOrConnecting();

        ListView newsList = findViewById(R.id.newsList);
        mAdapter = new ArticleAdapter(this, new ArrayList<>());
        newsList.setAdapter(mAdapter);

        mEmptyStateView = findViewById(R.id.emptyState);
        newsList.setEmptyView(mEmptyStateView);
        mEmptyStateTextView = mEmptyStateView.findViewById(R.id.emptyTxt);

        mLoadingIndicator = findViewById(R.id.loadingIndicator);

        newsList.setOnItemClickListener((adapterView, view, i, l) -> {

            Article currentArticle = mAdapter.getItem(i);

            Uri articleUri = Uri.parse(currentArticle.getArticleUrl());
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);
            startActivity(websiteIntent);

        });

        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            mLoadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }

    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(API_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("section", "technology");
        uriBuilder.appendQueryParameter("show-fields", "thumbnail");
        uriBuilder.appendQueryParameter("page-size", "20");
        uriBuilder.appendQueryParameter("api-key", API_KEY);

        return new ArticleLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        mLoadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_articles);

        mAdapter.clear();
        if (articles != null && !articles.isEmpty()) {
            mAdapter.addAll(articles);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        mAdapter.clear();
    }
}
