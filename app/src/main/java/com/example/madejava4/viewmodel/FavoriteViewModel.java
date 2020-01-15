package com.example.madejava4.viewmodel;

import android.app.Application;

import com.example.madejava4.model.Movie;
import com.example.madejava4.service.repository.FavoriteRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FavoriteViewModel extends AndroidViewModel{
    private FavoriteRepository movieRepository;
    private LiveData<List<Movie>> movieList;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new FavoriteRepository(application);
    }

    public LiveData<List<Movie>> getFavoriteList(){
        return movieRepository.getAllMovie();
    }

    public LiveData<List<Movie>> getFavoriteType(String type){
        return movieRepository.getMovieByType(type);
    }

    public void insert(Movie movie, String type){
        movieRepository.insert(movie,type);
    }
    public void delete(Movie movie){
        movieRepository.delete(movie);
    }

}
