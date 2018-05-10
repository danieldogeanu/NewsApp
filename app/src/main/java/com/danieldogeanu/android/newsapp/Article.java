package com.danieldogeanu.android.newsapp;

import android.graphics.Bitmap;

public class Article {

    private String mArticleTitle;
    private String mArticleUrl;
    private Bitmap mArticleThumbnail;
    private String mArticlePublishedDate;

    public Article(String title, String url, Bitmap thumbnail, String published) {
        mArticleTitle = title;
        mArticleUrl = url;
        mArticleThumbnail = thumbnail;
        mArticlePublishedDate = published;
    }

    public String getArticleTitle() {
        return mArticleTitle;
    }

    public String getArticleUrl() {
        return mArticleUrl;
    }

    public Bitmap getArticleThumbnail() {
        return mArticleThumbnail;
    }

    public String getArticlePublishedDate() {
        return mArticlePublishedDate;
    }

    public boolean hasThumbnail() {
        return mArticleThumbnail != null;
    }

    @Override
    public String toString() {
        return "Article {" +
                "mArticleTitle='" + mArticleTitle + "', " +
                "mArticleUrl='" + mArticleUrl + "', " +
                "mArticleThumbnail='" + mArticleThumbnail.toString() + "', " +
                "mArticlePublishedDate='" + mArticlePublishedDate + "' " +
                "}";
    }
}
