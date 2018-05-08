package com.danieldogeanu.android.newsapp;

public class Article {

    private String mArticleTitle;
    private String mArticleUrl;
    private String mArticleThumbnailUrl;
    private String mArticlePublishedDate;

    public Article(String title, String url, String thumbnail, String published) {
        mArticleTitle = title;
        mArticleUrl = url;
        mArticleThumbnailUrl = thumbnail;
        mArticlePublishedDate = published;
    }

    public String getArticleTitle() {
        return mArticleTitle;
    }

    public String getArticleUrl() {
        return mArticleUrl;
    }

    public String getArticleThumbnailUrl() {
        return mArticleThumbnailUrl;
    }

    public String getArticlePublishedDate() {
        return mArticlePublishedDate;
    }

    @Override
    public String toString() {
        return "Article {" +
                "mArticleTitle='" + mArticleTitle + "', " +
                "mArticleUrl='" + mArticleUrl + "' " +
                "}";
    }
}
