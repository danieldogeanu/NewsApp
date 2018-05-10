package com.danieldogeanu.android.newsapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {

    private static final String LOG_TAG = ArrayAdapter.class.getSimpleName();

    public ArticleAdapter(Activity activity, ArrayList<Article> articles) {
        super(activity, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        Article currentArticle = getItem(position);

        Utils.fillText(listItemView, R.id.newsHeadline, Utils.capitalize(currentArticle.getArticleTitle()));

        ImageView newsThumbnail = listItemView.findViewById(R.id.newsThumbnail);
        if (currentArticle.hasThumbnail()) {
            DownloadImageTask task = new DownloadImageTask(newsThumbnail);
            task.execute(currentArticle.getArticleThumbnailUrl());
        }

        return listItemView;
    }

    // TODO: Make this class to not download every time the ListView is scrolled.
    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        private WeakReference<ImageView> mImageViewReference;

        public DownloadImageTask(ImageView imageView) {
            mImageViewReference = new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String imgUrl = urls[0];
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

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (mImageViewReference != null) {
                ImageView imageView = mImageViewReference.get();
                if ((imageView != null) && (bitmap != null)) {
                    imageView.setImageBitmap(bitmap);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
        }
    }

}
