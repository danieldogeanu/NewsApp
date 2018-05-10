package com.danieldogeanu.android.newsapp;

import android.widget.ImageButton;

import java.util.ArrayList;

public class Bookmarks {

    private static final String LOG_TAG = Bookmarks.class.getSimpleName();

    private static Bookmarks INSTANCE = new Bookmarks();

    private ArrayList<Article> mBookmarks = new ArrayList<>();

    private Bookmarks() {}

    public static Bookmarks getInstance() {
        return(INSTANCE);
    }

    public void addArticleToBookmarks(Article article) {
        if ((article != null) && !isBookmark(article)) {
            mBookmarks.add(article);
        }
    }

    public void removeArticleFromBookmarks(Article article) {
        for (int i = 0; i < mBookmarks.size(); i++) {
            Article currentArticle = mBookmarks.get(i);
            if (currentArticle.getArticleTitle().equals(article.getArticleTitle())) {
                mBookmarks.remove(i);
            }
        }
    }

    public boolean isBookmark(Article article) {
        boolean bookmarkState = false;
        for (int i = 0; i < mBookmarks.size(); i++) {
            Article currentArticle = mBookmarks.get(i);
            if (currentArticle.getArticleTitle().equals(article.getArticleTitle())) {
                bookmarkState = true;
            }
        }
        return bookmarkState;
    }

    public void toggleBookmarkButton(ImageButton button, Article article) {
        if (isBookmark(article)) {
            button.setActivated(true);
        } else {
            button.setActivated(false);
        }

        button.setOnClickListener(view -> {
            if (!isBookmark(article) && !button.isActivated()) {
                addArticleToBookmarks(article);
                button.setActivated(true);
            } else {
                removeArticleFromBookmarks(article);
                button.setActivated(false);
            }
        });
    }

    @Override
    public String toString() {
        return LOG_TAG + " { mBookmarks=" + mBookmarks + " }";
    }
}
