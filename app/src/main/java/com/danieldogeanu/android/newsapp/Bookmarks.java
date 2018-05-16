package com.danieldogeanu.android.newsapp;

import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Singleton class that stores the Bookmarks list at a global level.
 */
public class Bookmarks {

    // Constant used for debugging.
    private static final String LOG_TAG = Bookmarks.class.getSimpleName();

    // Initialize the class instance as soon as possible in order to be thread-safe.
    private static Bookmarks INSTANCE = new Bookmarks();

    // The global Bookmarks list that stores the bookmarked articles.
    private ArrayList<Article> mBookmarks = new ArrayList<>();

    /** Private constructor, so it can't be accessed from outside the class. */
    private Bookmarks() {}

    /** @return Returns the existing instance of the class. */
    public static Bookmarks getInstance() {
        return(INSTANCE);
    }

    /** @return Returns the Bookmarks list. */
    public ArrayList<Article> getBookmarks() {
        return mBookmarks;
    }

    /**
     * Method to add the Article to the Bookmarks list.
     * @param article The Article to add to Bookmarks list.
     */
    private void addArticleToBookmarks(Article article) {
        if ((article != null) && !isBookmark(article)) {
            mBookmarks.add(article);
        }
    }

    /**
     * Method to remove the Article from the Bookmarks list.
     * @param article The Article to remove from the Bookmarks list.
     */
    private void removeArticleFromBookmarks(Article article) {
        for (int i = 0; i < mBookmarks.size(); i++) {
            Article currentArticle = mBookmarks.get(i);
            if (currentArticle.getArticleTitle().equals(article.getArticleTitle())) {
                mBookmarks.remove(i);
            }
        }
    }

    /**
     * Method to scan the Bookmarks list to see if the Article is there or not.
     * @param article The Article to search for.
     * @return Returns whether or not it contains the Article.
     */
    private boolean isBookmark(Article article) {
        boolean bookmarkState = false;
        for (int i = 0; i < mBookmarks.size(); i++) {
            Article currentArticle = mBookmarks.get(i);
            if (currentArticle.getArticleTitle().equals(article.getArticleTitle())) {
                bookmarkState = true;
            }
        }
        return bookmarkState;
    }

    /**
     * Method to toggle the Bookmark button and to add or remove the Article to the Bookmarks list.
     * @param button The Bookmark button reference.
     * @param article The Article to add or remove from the Bookmarks list.
     */
    public void toggleBookmarkButton(ImageButton button, Article article) {

        // Set the button state ahead of time.
        if (isBookmark(article)) {
            button.setActivated(true);
        } else {
            button.setActivated(false);
        }

        // Attach the OnClickListener to the button.
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

    /**
     * Overrides the toString() method for debug purposes.
     * @return Returns the list of Bookmarks contained.
     */
    @Override
    public String toString() {
        return LOG_TAG + " { mBookmarks=" + mBookmarks + " }";
    }
}
