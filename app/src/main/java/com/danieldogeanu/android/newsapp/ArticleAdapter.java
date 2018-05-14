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
            viewHolder.sectionNameTextView = convertView.findViewById(R.id.section_name_tv);
            viewHolder.newsHeadlineTextView = convertView.findViewById(R.id.news_headline_tv);
            viewHolder.bookmarkButton = convertView.findViewById(R.id.bookmark_img_btn);
            viewHolder.shareButton = convertView.findViewById(R.id.share_img_btn);
            viewHolder.newsThumbnailImageView = convertView.findViewById(R.id.news_thumbnail_iv);
            viewHolder.authorAndDateTextView = convertView.findViewById(R.id.author_and_date_tv);
            // Add ViewHolder as a Tag on the News Item layout.
            convertView.setTag(viewHolder);
        } else {
            // Restore ViewHolder from Tag.
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get current Article object from the ArrayList.
        Article currentArticle = getItem(position);

        // Set the Section name.
        viewHolder.sectionNameTextView.setText(currentArticle.getArticleSection());

        // Set the Article title.
        viewHolder.newsHeadlineTextView.setText(Utils.capitalize(currentArticle.getArticleTitle()));

        // Set the Article thumbnail.
        if (currentArticle.hasThumbnail()) {
            viewHolder.newsThumbnailImageView.setImageBitmap(currentArticle.getArticleThumbnail());
        }

        // Set the OnClickListener for the Bookmark button.
        mBookmarks.toggleBookmarkButton(viewHolder.bookmarkButton, currentArticle);

        // Attach the Share Intent to the Share button.
        Utils.attachShareIntent(convertView.getContext(), viewHolder.shareButton, currentArticle.getArticleUrl());

        // Set the name of the author and the published date of the Article.
        Utils.composeAuthorDate(convertView.getContext(), viewHolder.authorAndDateTextView, currentArticle);

        // Return the fully assembled News Item.
        return convertView;
    }

    /** Private inner class that stores all the views necessary to the News Item. */
    private static class ViewHolder {
        private TextView sectionNameTextView, newsHeadlineTextView, authorAndDateTextView;
        private ImageButton bookmarkButton, shareButton;
        private ImageView newsThumbnailImageView;
    }

}
