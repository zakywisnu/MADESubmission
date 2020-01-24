package com.zeroemotion.favoritefilm.viewmodel;

import android.database.Cursor;
import android.util.Log;

import com.zeroemotion.favoritefilm.model.Movie;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoriteViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();
    private List<Movie> movies = new ArrayList<>();

    public MutableLiveData<List<Movie>> getMovieList(){
        return movieList;
    }

    public void setMovieList(Cursor cursor){
        if (cursor != null && cursor.moveToFirst()){
            do {
                Movie movie = new Movie(cursor);
                movies.add(movie);
            } while (cursor.moveToNext());
            cursor.close();
            movieList.postValue(movies);
            Log.d("Check", "Data:" + movies + "");
        }
    }
}
