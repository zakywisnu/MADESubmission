package com.zeroemotion.madejava5.service.repository;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.zeroemotion.madejava5.database.MovieDao;
import com.zeroemotion.madejava5.database.MovieDatabase;
import com.zeroemotion.madejava5.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class FavoriteRepository {
    private MovieDao movieDao;
    private LiveData<ArrayList<Movie>> list;
    private static Cursor movieFavoriteCursor;


    public FavoriteRepository(Context context) {
        MovieDatabase movieDatabase = MovieDatabase.getDatabase(context);
        movieDao = movieDatabase.getMovie();
    }
    public void deleteId(long movieId){
        new deleteIdAsyncTask(movieDao).execute(movieId);
    }

    private class deleteIdAsyncTask extends AsyncTask<Long, Void, Void>{
        private MovieDao movieDao;

        public deleteIdAsyncTask(MovieDao dao){
            movieDao = dao;
        }

//        @Override
//        protected Void doInBackground(Integer... integers) {
//
//            return null;
//        }

        @Override
        protected Void doInBackground(Long... longs) {
            movieDao.deleteAMovie(longs[0]);
            return null;
        }
    }


    public LiveData<Movie> selectById(long movieId){
        LiveData<Movie> movieLiveData = new MutableLiveData<>();
        try {
            movieLiveData = new SelectMovieById(movieDao).execute(movieId).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return movieLiveData;
    }

    public LiveData<List<Movie>> getMovieByType(String type) {
        return movieDao.getMovieByType(type);
    }


    public void insert(Movie movie) {
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

    public Cursor getAllMovieCursor() {
        new SelectMovieCursor(movieDao).execute();
        return movieFavoriteCursor;
    }

    private class SelectMovieCursor extends AsyncTask<Void, Cursor, Cursor> {
        private MovieDao movieDao;
        public SelectMovieCursor(MovieDao dao) {
            movieDao = dao;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            movieFavoriteCursor = movieDao.getMovieFavs();
            return movieFavoriteCursor;
        }
    }

    public long insertMovieFavoriteCursor(Movie movie){
        new InsertMovieFavoriteCursor(movieDao).execute(movie);
        return 0;
    }

    private class InsertMovieFavoriteCursor extends AsyncTask<Movie, Void, Long>{
        private MovieDao movieDao;
        public InsertMovieFavoriteCursor(MovieDao dao) {
            movieDao = dao;
        }

        @Override
        protected Long doInBackground(Movie... movies) {
            return movieDao.insertToCursor(movies[0]);
        }
    }

//    public Cursor getAllTvCursor(){
//        new SelectTvCursor(movieDao).execute();
//        return tvFavoriteCursor;
//    }
//
//    private class SelectTvCursor extends AsyncTask<Void, Cursor,Cursor>{
//        private MovieDao movieDao;
//        public SelectTvCursor(MovieDao dao) {
//            movieDao = dao;
//        }
//
//        @Override
//        protected Cursor doInBackground(Void... voids) {
//            tvFavoriteCursor = movieDao.getMovieFavs("tv");
//            return tvFavoriteCursor;
//        }
//    }

    private class SelectMovieById extends AsyncTask<Long, Void, LiveData<Movie>>{
        private MovieDao movieDao;
        public SelectMovieById(MovieDao dao) {
            movieDao = dao;
        }


        @Override
        protected LiveData<Movie> doInBackground(Long... longs) {
            return movieDao.selectById(longs[0]);
        }
    }

    public Cursor getMovieCursorId(long id){
        new SelectMovieCursorId(movieDao).execute(id);
        return movieFavoriteCursor;
    }

    private class SelectMovieCursorId extends AsyncTask<Long,Cursor,Cursor> {
        private MovieDao movieDao;
        public SelectMovieCursorId(MovieDao dao) {
            movieDao = dao;
        }

        @Override
        protected Cursor doInBackground(Long... longs) {
            return movieDao.selectId(longs[0]);
        }
    }
}
