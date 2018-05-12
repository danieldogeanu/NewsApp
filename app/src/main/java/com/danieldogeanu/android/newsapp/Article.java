package com.danieldogeanu.android.newsapp;

import android.graphics.Bitmap;

/**
 * Class that creates the objects used to display the News.
 */
public class Article {

    private String mArticleTitle;
    private String mArticleUrl;
    private Bitmap mArticleThumbnail;
    private String mArticlePublishedDate;

    /**
     * The Article object constructor.
     * @param title The title of the article.
     * @param url The URL of the article.
     * @param thumbnail The thumbnail (bitmap) of the article.
     * @param published The date when the article was published.
     */
    public Article(String title, String url, Bitmap thumbnail, String published) {
        mArticleTitle = title;
        mArticleUrl = url;
        mArticleThumbnail = thumbnail;
        mArticlePublishedDate = published;
    }

    /** @return Returns the article title. */
    public String getArticleTitle() {
        return mArticleTitle;
    }

    /** @return Returns the article URL. */
    public String getArticleUrl() {
        return mArticleUrl;
    }

    /** @return Returns the article thumbnail (bitmap). */
    public Bitmap getArticleThumbnail() {
        return mArticleThumbnail;
    }

    /** @return Returns the date when the article was published. */
    public String getArticlePublishedDate() {
        return mArticlePublishedDate;
    }

    /** @return Checks to see if the article has a thumbnail and returns a boolean. */
    public boolean hasThumbnail() {
        return mArticleThumbnail != null;
    }

    /**
     * Overrides the toString() method for debug purposes.
     * @return Returns a concatenated string with all the fields contents.
     */
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
