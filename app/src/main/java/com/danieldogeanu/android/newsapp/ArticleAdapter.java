package com.danieldogeanu.android.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {

    public static final String LOG_TAG = ArrayAdapter.class.getSimpleName();

    public ArticleAdapter(Activity activity, ArrayList<Article> articles) {
        super(activity, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        Article currentArticle = getItem(position);

        TextView newsHeadline = (TextView) listItemView.findViewById(R.id.newsHeadline);
        newsHeadline.setText(currentArticle.getArticleTitle());

        return listItemView;
    }
}
