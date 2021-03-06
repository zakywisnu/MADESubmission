package com.zeroemotion.madejava5.viewmodel;

import android.app.Application;

import com.zeroemotion.madejava5.model.Movie;
import com.zeroemotion.madejava5.service.repository.FavoriteRepository;

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

    public LiveData<Movie> selectMovie(long movieId){
        return movieRepository.selectById(movieId);
    }

    public LiveData<List<Movie>> getFavoriteType(String type){
        return movieRepository.getMovieByType(type);
    }


    public void insert(Movie movie){
        movieRepository.insert(movie);
    }
    public void delete(Movie movie){
        movieRepository.delete(movie);
    }

    public void deleteById(long movieId){
        movieRepository.deleteId(movieId);
    }

}
