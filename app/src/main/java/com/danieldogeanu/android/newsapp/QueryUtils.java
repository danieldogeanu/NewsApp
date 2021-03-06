package com.danieldogeanu.android.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Class that contains utility methods to query the API URL.
 */
public final class QueryUtils {

    // Constant used for debugging.
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /** Private constructor, so we can't instantiate the class. */
    private QueryUtils() {}

    /**
     * Method that makes the HTTP request in order to retrieve the articles from the given URL.
     * @param requestUrl The API URL to make the request to.
     * @return Returns a list with articles retrieved from the URL.
     */
    public static List<Article> fetchNews(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return extractArticleFromJson(jsonResponse);
    }

    /**
     * Method to create the URL from a string.
     * @param stringUrl The string from which to create the URL.
     * @return Returns the created URL object.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL.", e);
        }
        return url;
    }

    /**
     * Method to make the HTTP request to the provided URL.
     * @param url The URL to make the HTTP request to.
     * @return Returns the JSON response from the HTTP request.
     * @throws IOException Throws IOException if it can't retrieve the JSON response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
            if (inputStream != null) inputStream.close();
        }
        return jsonResponse;
    }

    /**
     * Method to read from the InputStream of the HTTP request and build the JSON string.
     * @param inputStream The InputStream to read from.
     * @return Returns the built JSON string.
     * @throws IOException Throws IOException if it can't read from the InputStream.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Method to extract Articles from the JSON string provided.
     * @param stringJson The JSON string to extract articles from.
     * @return Returns the list with the extracted articles.
     */
    private static List<Article> extractArticleFromJson(String stringJson) {
        if (TextUtils.isEmpty(stringJson)) return null;

        List<Article> articles = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(stringJson);
            JSONObject jsonResponse = baseJsonResponse.getJSONObject("response");
            String status = jsonResponse.getString("status");

            if (status.equals("ok")) {
                JSONArray articleArray = jsonResponse.getJSONArray("results");

                for (int i = 0; i < articleArray.length(); i++) {
                    JSONObject currentArticle = articleArray.getJSONObject(i);

                    String section = currentArticle.getString("sectionName");
                    String published = formatDate(currentArticle.getString("webPublicationDate"));
                    String title = currentArticle.getString("webTitle");
                    String url = currentArticle.getString("webUrl");

                    JSONObject fields = currentArticle.optJSONObject("fields");
                    JSONArray tags = currentArticle.optJSONArray("tags");

                    String thumbnail = "";
                    Bitmap bitmap = null;
                    if (!currentArticle.isNull("fields")) {
                        if (fields.has("thumbnail")) thumbnail = fields.optString("thumbnail");
                        if (!thumbnail.isEmpty()) bitmap = downloadImage(thumbnail);
                    }

                    String author = "";
                    if (!currentArticle.isNull("tags") && !tags.isNull(0)) {
                        JSONObject firstObject = tags.optJSONObject(0);
                        if (firstObject.has("webTitle")) author = firstObject.optString("webTitle");
                    }

                    articles.add(new Article(title, url, bitmap, published, author, section));
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing JSON results.", e);
        }
        return articles;
    }

    /**
     * Method to convert and format the date for each Article.
     * @param rawDate The input raw date string.
     * @return Returns the proper formatted date string to be displayed for each Article.
     */
    private static String formatDate(String rawDate) {
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

    /**
     * Method to download the thumbnail image and convert it to Bitmap.
     * @param imgUrl The URL from which to download the image.
     * @return Returns the Bitmap to be used as thumbnail for the Article.
     */
    private static Bitmap downloadImage(String imgUrl) {
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new URL(imgUrl).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "There's a problem with the URL provided.", e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem opening Input Stream.", e);
        }
        return bitmap;
    }

}
