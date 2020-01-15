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

    public FavoriteRepository(Application application) {
        MovieDatabase movieDatabase = MovieDatabase.getDatabase(application);
        movieDao = movieDatabase.getMovie();
    }

    public LiveData<List<Movie>> getAllMovie() {
        return movieDao.getAllMovieDb();
    }

    public LiveData<List<Movie>> getMovieByType(String type) {
        return movieDao.getMovieByType(type);
    }

    public void insert(Movie movie, String type) {
        Movie movies = new Movie();
        movies.setMedia_type(type);
        movies.setTitle(movie.getTitle());
        movies.setPoster_path(movie.getPoster_path());
        movies.setBackdrop_path(movie.getBackdrop_path());
        movies.setOverview(movie.getOverview());
        movies.setRelease_date(movie.getRelease_date());
        movies.setVote_average(movie.getVote_average());

        insertNew(movies);
    }


    public void insertNew(Movie movie) {
        new insertAsyncTask(movieDao).execute(movie);
    }

    private class insertAsyncTask extends AsyncTask<Movie, Void, Void> {
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

    public void delete(Movie movie) {
        new deleteAsyncTask(movieDao).execute(movie);
    }

    private class deleteAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao asyncTaskDao;

        public deleteAsyncTask(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            asyncTaskDao.delete(movies[0]);
            return null;
        }
    }
}
