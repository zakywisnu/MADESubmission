package com.example.madejava4.database;

import com.example.madejava4.model.Movie;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MovieDao {

    @Insert
    void insert(Movie movie);

    @Query("DELETE FROM movie_table")
    void deleteAll();

    @Query("DELETE FROM movie_table WHERE uid = :id")
    void deleteAMovie(int id);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM movie_table ORDER BY title DESC")
    LiveData<List<Movie>> getAllMovieDb();

    @Query("SELECT * FROM movie_table WHERE movie_type = :movieType")
    LiveData<List<Movie>> getMovieByType(String movieType);
}
