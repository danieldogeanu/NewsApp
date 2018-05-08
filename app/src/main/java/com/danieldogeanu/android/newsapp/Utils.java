package com.danieldogeanu.android.newsapp;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class Utils {

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

}
