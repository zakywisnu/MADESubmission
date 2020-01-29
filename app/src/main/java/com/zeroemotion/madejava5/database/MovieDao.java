package com.zeroemotion.madejava5.database;

import android.database.Cursor;

import com.zeroemotion.madejava5.model.Movie;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {

    @Insert(onConflict = REPLACE)
    void insert(Movie movie);

    @Insert(onConflict = REPLACE)
    long insertToCursor(Movie movie);

    @Query("UPDATE movie_table SET media_type = :media_type WHERE id = :id")
    void update(int id, String media_type);

    @Query("DELETE FROM movie_table")
    void deleteAll();

    @Query("DELETE FROM movie_table WHERE id = :id")
    void deleteAMovie(long id);

    @Delete
    void delete(Movie movie);

    @Insert
    long insertProvider(Movie movie);

    @Query("SELECT * FROM movie_table WHERE media_type = :movie_type")
    List<Movie> getAllMovieDb(String movie_type);

    @Query("SELECT * FROM movie_table WHERE media_type = :movieType")
    LiveData<List<Movie>> getMovieByType(String movieType);

    @Query("SELECT * FROM movie_table")
    Cursor getMovieFavs();

    @Query("SELECT * FROM movie_table WHERE id = :id")
    LiveData<Movie> selectById(long id);

    @Query("SELECT * FROM movie_table WHERE id = :id")
    Cursor selectId(long id);

    @Query("SELECT EXISTS (SELECT 1 FROM movie_table WHERE id = :id)")
    public int isFavorite(int id);

}
