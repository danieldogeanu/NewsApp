package com.danieldogeanu.android.newsapp;

public class Article {

    private String mArticleTitle;
    private String mArticleUrl;

    public Article(String title, String url) {
        mArticleTitle = title;
        mArticleUrl = url;
    }

    public String getArticleTitle() {
        return mArticleTitle;
    }

    public String getArticleUrl() {
        return mArticleUrl;
    }

    public void setArticleTitle(String title) {
        mArticleTitle = title;
    }

    public void setArticleUrl(String url) {
        mArticleUrl = url;
    }

    @Override
    public String toString() {
        return "Article {" +
                "mArticleTitle='" + mArticleTitle + "', " +
                "mArticleUrl='" + mArticleUrl + "' " +
                "}";
    }
}
