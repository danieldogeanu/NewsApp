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

public class ArticleAdapter extends ArrayAdapter<Article> {

    private static final String LOG_TAG = ArrayAdapter.class.getSimpleName();

    Bookmarks mBookmarks;

    public ArticleAdapter(Activity activity, ArrayList<Article> articles, Bookmarks bookmarks) {
        super(activity, 0, articles);
        mBookmarks = bookmarks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        final Article currentArticle = getItem(position);

        Utils.fillText(listItemView, R.id.newsHeadline, Utils.capitalize(currentArticle.getArticleTitle()));

        ImageView newsThumbnail = listItemView.findViewById(R.id.newsThumbnail);
        if (currentArticle.hasThumbnail()) {
            Bitmap thumbnailImg = currentArticle.getArticleThumbnail();
            newsThumbnail.setImageBitmap(thumbnailImg);
            newsThumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        final ImageButton bookmarkBtn = listItemView.findViewById(R.id.bookmarkBtn);
        mBookmarks.toggleBookmarkButton(bookmarkBtn, currentArticle);

        return listItemView;
    }

}
