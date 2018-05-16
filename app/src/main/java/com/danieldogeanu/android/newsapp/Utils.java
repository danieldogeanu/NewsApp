package com.danieldogeanu.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Class that contains general utility methods to be used across the app.
 */
public final class Utils {

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
            String cap;
            if (s.length() > 1) cap = s.substring(0, 1).toUpperCase() + s.substring(1);
            else cap = s.toUpperCase();
            builder.append(cap).append(" ");
        }
        return builder.toString();
    }

    /**
     * Method to attach a Share Intent to the Share Button.
     * @param context The context from which this method is called.
     * @param button The button to attach the OnClickListener and Intent to.
     * @param url The URL of the Article to share.
     */
    public static void attachShareIntent(Context context, ImageButton button, String url) {
        String subject = context.getResources().getString(R.string.intent_subject);
        String title = context.getResources().getString(R.string.intent_title);
        button.setOnClickListener(view -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, url);
            context.startActivity(Intent.createChooser(sharingIntent, title));
        });
    }

    /**
     * Method to compose the string for the Author-and-Date TextView.
     * @param context The Context from which this method is called.
     * @param view The TextView to set the text to.
     * @param article The Article to extract the date and author from.
     */
    public static void composeAuthorDate(Context context, TextView view, Article article) {
        String emDash = context.getResources().getString(R.string.em_dash);
        String composedString;
        if (article.hasAuthor()) {
            composedString = capitalize(article.getArticleAuthor()) + "  " + emDash + "  " + article.getArticlePublishedDate();
        } else {
            composedString = article.getArticlePublishedDate();
        }
        view.setText(composedString);
    }

    /**
     * Method to compose the API KEY string.
     * @return Returns the API KEY.
     */
    public static String getApiKey() {
        String sep = "-";
        String a = BuildConfig.GUARD_A;
        String b = BuildConfig.GUARD_B;
        String c = BuildConfig.GUARD_C;
        String d = BuildConfig.GUARD_D;
        String e = BuildConfig.GUARD_E;
        return b+sep+a+sep+d+sep+c+sep+e;
    }

    /**
     * Method to show short Toast message in the app.
     * @param context The Context from which this method is called.
     * @param message The message to show in the Toast.
     */
    public static void showToast(Context context, CharSequence message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
