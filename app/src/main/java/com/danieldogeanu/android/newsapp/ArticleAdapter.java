package com.danieldogeanu.android.newsapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * The ArticleAdapter is a custom ArrayAdapter for Article objects.
 * It's used to build the list of articles to display in the NewsActivity.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {

    private Bookmarks mBookmarks;

    /**
     * ArticleAdapter constructor. Accepts 3 parameters.
     * @param activity The activity from which this adapter is called.
     * @param articles The ArrayList with all the articles.
     * @param bookmarks The Bookmarks class reference, so we can keep track of bookmarks.
     */
    public ArticleAdapter(Activity activity, ArrayList<Article> articles, Bookmarks bookmarks) {
        super(activity, 0, articles);
        mBookmarks = bookmarks;
    }

    /**
     * Overrides the getView() method to display a custom layout for each item in the ListView.
     * @param position The position in the ListView.
     * @param convertView The view for the Item in the ListView.
     * @param parent The parent view.
     * @return Returns the assembled Item to display in the ListView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get or inflate the News Item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        // Get current Article object from the ArrayList.
        Article currentArticle = getItem(position);

        // Set the Article title.
        Utils.fillText(listItemView, R.id.newsHeadline, Utils.capitalize(currentArticle.getArticleTitle()));

        // Set the Article thumbnail.
        ImageView newsThumbnail = listItemView.findViewById(R.id.newsThumbnail);
        if (currentArticle.hasThumbnail()) {
            Bitmap thumbnailImg = currentArticle.getArticleThumbnail();
            newsThumbnail.setImageBitmap(thumbnailImg);
        }

        // Set the OnClickListener for the Bookmark button.
        ImageButton bookmarkBtn = listItemView.findViewById(R.id.bookmarkBtn);
        mBookmarks.toggleBookmarkButton(bookmarkBtn, currentArticle);

        // Attach the Share Intent to the Share button.
        Utils.attachShareIntent(listItemView, R.id.shareBtn, currentArticle.getArticleUrl());

        // Set the published date of the Article.
        Utils.fillText(listItemView, R.id.publishedDate, Utils.formatDate(currentArticle.getArticlePublishedDate()));

        // Return the fully assembled News Item.
        return listItemView;
    }

}
