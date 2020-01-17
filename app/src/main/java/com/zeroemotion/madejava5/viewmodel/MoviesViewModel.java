package com.zeroemotion.madejava5.viewmodel;

import android.app.Application;

import com.zeroemotion.madejava5.model.Movie;
import com.zeroemotion.madejava5.service.repository.MovieRepository;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MoviesViewModel extends AndroidViewModel{
    private MovieRepository movieRepository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }
    public LiveData<ArrayList<Movie>> getListMovie(String type, String api_key){
        return movieRepository.getListMovie(type, api_key);
    }

}
