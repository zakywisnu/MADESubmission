package com.zeroemotion.madejava5.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.zeroemotion.madejava5.BuildConfig;
import com.zeroemotion.madejava5.R;
import com.zeroemotion.madejava5.database.MovieDatabase;
import com.zeroemotion.madejava5.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.zeroemotion.madejava5.widget.MovieBannerWidget.EXTRA_ITEM;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<Movie> movieList = new ArrayList<>();
    private MovieDatabase movieDatabase;
    private Context context;

    StackRemoteViewsFactory(Context context){
        this.context = context;
    }

    @Override
    public void onCreate() {
        movieDatabase = MovieDatabase.getDatabase(context);
        try {
            movieList = new GetMovieFavAsyncTask(movieDatabase).execute().get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDataSetChanged() {
        try {
            movieList = new GetMovieFavAsyncTask(movieDatabase).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
        if (movieList.size() > 0){
            Movie movie = movieList.get(i);
            try {
                Bitmap bitmap = Glide.with(context)
                        .asBitmap()
                        .load(BuildConfig.BASE_IMAGE_URL + movie.getPoster_path())
                        .submit(512,512)
                        .get();
                remoteViews.setImageViewBitmap(R.id.image_widget, bitmap);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            Bundle extras = new Bundle();
            extras.putString(EXTRA_ITEM, movie.getTitle());
            Intent newIntent = new Intent();
            newIntent.putExtras(extras);
            remoteViews.setOnClickFillInIntent(R.id.image_widget, newIntent);
            return remoteViews;
        }
        else return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private class GetMovieFavAsyncTask  extends AsyncTask<MovieDatabase, Void, List<Movie>> {
        MovieDatabase movieDatabase;
        public GetMovieFavAsyncTask(MovieDatabase movieDb) {
            movieDatabase = movieDb;
        }

        @Override
        protected List<Movie> doInBackground(MovieDatabase... movieDatabases) {
            return movieDatabase.getMovie().getAllMovieDb("movie");
        }
    }
}
