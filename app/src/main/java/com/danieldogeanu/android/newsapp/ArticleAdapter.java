package com.danieldogeanu.android.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the News Item layout.
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
            // Initialize the ViewHolder.
            viewHolder = new ViewHolder();
            // Find all necessary Views in the News Item layout.
            viewHolder.sectionName = convertView.findViewById(R.id.sectionName);
            viewHolder.newsHeadline = convertView.findViewById(R.id.newsHeadline);
            viewHolder.bookmarkBtn = convertView.findViewById(R.id.bookmarkBtn);
            viewHolder.shareBtn = convertView.findViewById(R.id.shareBtn);
            viewHolder.newsThumbnail = convertView.findViewById(R.id.newsThumbnail);
            viewHolder.authorAndDate = convertView.findViewById(R.id.authorAndDate);
            // Add ViewHolder as a Tag on the News Item layout.
            convertView.setTag(viewHolder);
        } else {
            // Restore ViewHolder from Tag.
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get current Article object from the ArrayList.
        Article currentArticle = getItem(position);

        // Set the Section name.
        viewHolder.sectionName.setText(currentArticle.getArticleSection());

        // Set the Article title.
        viewHolder.newsHeadline.setText(Utils.capitalize(currentArticle.getArticleTitle()));

        // Set the Article thumbnail.
        if (currentArticle.hasThumbnail()) {
            viewHolder.newsThumbnail.setImageBitmap(currentArticle.getArticleThumbnail());
        }

        // Set the OnClickListener for the Bookmark button.
        mBookmarks.toggleBookmarkButton(viewHolder.bookmarkBtn, currentArticle);

        // Attach the Share Intent to the Share button.
        Utils.attachShareIntent(convertView.getContext(), viewHolder.shareBtn, currentArticle.getArticleUrl());

        // Set the name of the author and the published date of the Article.
        Utils.composeAuthorDate(convertView.getContext(), viewHolder.authorAndDate, currentArticle);

        // Return the fully assembled News Item.
        return convertView;
    }

    /** Private inner class that stores all the views necessary to the News Item. */
    private static class ViewHolder {
        private TextView sectionName, newsHeadline, authorAndDate;
        private ImageButton bookmarkBtn, shareBtn;
        private ImageView newsThumbnail;
    }

}
