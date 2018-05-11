package com.danieldogeanu.android.newsapp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public final class Utils {

    public static final String LOG_TAG = Utils.class.getSimpleName();

    private Utils() {}

    public static String capitalize(String string) {
        String[] strArray = string.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
            builder.append(cap).append(" ");
        }
        return builder.toString();
    }

    public static void fillText(Activity activity, int id, CharSequence text) {
        TextView thisTextView = activity.findViewById(id);
        thisTextView.setText(text);
    }

    public static void fillText(View view, int id, CharSequence text) {
        TextView thisTextView = view.findViewById(id);
        thisTextView.setText(text);
    }

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

}
