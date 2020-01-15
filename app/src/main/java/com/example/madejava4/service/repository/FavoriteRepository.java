package com.example.madejava4.service.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.madejava4.database.MovieDao;
import com.example.madejava4.database.MovieDatabase;
import com.example.madejava4.model.Movie;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public class FavoriteRepository {
    private MovieDao movieDao;
    private LiveData<ArrayList<Movie>> list;

    public FavoriteRepository(Application application){
        MovieDatabase movieDatabase = MovieDatabase.getDatabase(application);
        movieDao = movieDatabase.getMovie();
    }

    public LiveData<List<Movie>> getAllMovie(){
        return movieDao.getAllMovieDb();
    }

    public LiveData<List<Movie>> getMovieByType(String type){
        return movieDao.getMovieByType(type);
    }

    public void insert(Movie movie){
        new insertAsyncTask(movieDao).execute(movie);
    }
    private class insertAsyncTask  extends AsyncTask<Movie, Void, Void> {
        private MovieDao asyncTaskDao;
        public insertAsyncTask(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            asyncTaskDao.insert(movies[0]);
            return null;
        }
    }

}
