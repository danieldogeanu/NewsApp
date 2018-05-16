package com.danieldogeanu.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that initializes the News (Main) Activity for the app.
 */
public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    // Constant used for debugging.
    private static final String LOG_TAG = NewsActivity.class.getSimpleName();

    // The ID used for the LoaderManager.
    private static final int LOADER_ID = 1;

    // The URL used for API calls.
    private static final String API_URL = "https://content.guardianapis.com/search";

    // Variables declared here so we can use them in all methods in this class.
    private ArticleAdapter mAdapter;
    private View mEmptyStateView, mLoadingIndicator;
    private TextView mEmptyStateTextView;
    private String mNoInternetMsg, mNoArticlesMsg;

    /**
     * Overrides the onCreate() method to assemble and display the News Activity.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Find out whether we're connected to the Internet or not.
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = (activeNetwork != null) && activeNetwork.isConnectedOrConnecting();

        // Initialize the Adapter.
        ListView newsList = findViewById(R.id.news_list_view);
        mAdapter = new ArticleAdapter(this, new ArrayList<>(), Bookmarks.getInstance());
        newsList.setAdapter(mAdapter);

        // Find required views.
        mEmptyStateView = findViewById(R.id.empty_state_view);
        mEmptyStateTextView = findViewById(R.id.empty_text_view);
        mLoadingIndicator = findViewById(R.id.loading_indicator);

        // Get the error messages strings.
        mNoArticlesMsg = getString(R.string.no_articles);
        mNoInternetMsg = getString(R.string.no_internet);

        // Assign the view to be used for the empty state.
        newsList.setEmptyView(mEmptyStateView);

        // Set the onItemClickListener() to handle item clicks.
        newsList.setOnItemClickListener((adapterView, view, i, l) -> {

            // Get the current Article clicked.
            Article currentArticle = mAdapter.getItem(i);

            // Attach Intent to open the Article in the browser.
            Uri articleUri = Uri.parse(currentArticle.getArticleUrl());
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);
            startActivity(websiteIntent);

        });

        // Initialize the loading of the articles.
        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            mLoadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(mNoInternetMsg);
            Log.e(LOG_TAG, mNoInternetMsg);
        }

    }

    /**
     * Overrides the onCreateLoader() method in order to compose and pass the URL to the ArticleLoader.
     * @param id The ID of the Loader.
     * @param bundle The args to be saved.
     * @return Returns the loader with the list of articles retrieved from the passed URL.
     */
    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle bundle) {

        // Hide the empty state view.
        mEmptyStateView.setVisibility(View.GONE);

        // Get default preferences.
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Set default preferences.
        String selectedSection = sharedPrefs.getString(
                getString(R.string.settings_section_key),
                getString(R.string.settings_section_default)
        );
        String pageSize = sharedPrefs.getString(
                getString(R.string.settings_page_size_key),
                getString(R.string.settings_page_size_default)
        );
        String numberOfPages = sharedPrefs.getString(
                getString(R.string.settings_pages_key),
                getString(R.string.settings_pages_default)
        );

        // Create the URL.
        Uri baseUri = Uri.parse(API_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Add parameters to the URL.
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("section", selectedSection);
        uriBuilder.appendQueryParameter("show-fields", "thumbnail");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", pageSize);
        uriBuilder.appendQueryParameter("pages", numberOfPages);
        uriBuilder.appendQueryParameter("api-key", Utils.getApiKey());

        // Pass the URL to the ArticleLoader.
        return new ArticleLoader(this, uriBuilder.toString());

    }

    /**
     * Overrides the onLoadFinished() method in order to add all articles loaded into the adapter.
     * @param loader The loader that retrieved the articles.
     * @param articles The list of articles retrieved.
     */
    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {

        // Hide the loading indicator and set the error message.
        mLoadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(mNoArticlesMsg);

        // Clear the adapter.
        mAdapter.clear();

        // Add the articles to the adapter.
        if (articles != null && !articles.isEmpty()) mAdapter.addAll(articles);
        if (articles == null || articles.isEmpty()) Log.e(LOG_TAG, mNoArticlesMsg);

    }

    /**
     * Overrides the onLoaderReset() method in order to reset the adapter.
     * @param loader The loader to reset.
     */
    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        mAdapter.clear();
    }

    /**
     * Overrides the onCreateOptionsMenu() method in order to add Settings to the App Bar menu.
     * @param menu The menu to inflate.
     * @return Returns the inflated menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Overrides the onOptionsItemSelected() method in order to detect which item was clicked,
     * and set the Intent to open the SettingsActivity.
     * @param item The item that was clicked in the menu.
     * @return Returns the clicked item from the menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
