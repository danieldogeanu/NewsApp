package com.danieldogeanu.android.newsapp;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class that contains general utility methods to be used across the app.
 */
public final class Utils {

    // Constant used for debugging.
    private static final String LOG_TAG = Utils.class.getSimpleName();

    /** Private constructor, so we can't instantiate the class. */
    private Utils() {}

    /**
     * Method to capitalize given strings. Used to capitalize Article titles.
     * @param string The string to capitalize.
     * @return Returns the capitalized string.
     */
    public static String capitalize(String string) {
        String[] strArray = string.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
            builder.append(cap).append(" ");
        }
        return builder.toString();
    }

    /**
     * Method to set the text to the selected TextView.
     * @param view The ListItemView for which this method is called. Used to find the TextView.
     * @param id The ID of the TextView.
     * @param text The text to set to the TextView.
     */
    public static void fillText(View view, int id, CharSequence text) {
        TextView thisTextView = view.findViewById(id);
        thisTextView.setText(text);
    }

    /**
     * Method to attach a Share Intent to the Share button.
     * @param listItemView The ListItemView from which this method is called.
     * @param buttonId The button ID to attach the Intent to.
     * @param articleUrl The URL of the Article to share.
     */
    public static void attachShareIntent(View listItemView, int buttonId, String articleUrl) {
        ImageButton button = listItemView.findViewById(buttonId);
        String subject = listItemView.getResources().getString(R.string.intent_subject);
        String title = listItemView.getResources().getString(R.string.intent_title);
        button.setOnClickListener(view -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, articleUrl);
            listItemView.getContext().startActivity(Intent.createChooser(sharingIntent, title));
        });
    }

    /**
     * Method to convert and format the date for each Article.
     * @param rawDate The input raw date string.
     * @return Returns the proper formatted date string to be displayed for each Article.
     */
    public static String formatDate(String rawDate) {
        String unformattedDate = rawDate.substring(0, 10);
        String formattedDate = "";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(unformattedDate);
            formattedDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(date);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Date could not be parsed from the string provided.", e);
        }
        return formattedDate;
    }

}
