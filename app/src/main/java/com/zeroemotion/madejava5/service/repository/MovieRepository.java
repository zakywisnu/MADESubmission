package com.zeroemotion.madejava5.service.repository;

import android.app.Application;
import android.util.Log;

import com.zeroemotion.madejava5.model.Movie;
import com.zeroemotion.madejava5.model.MovieResponse;
import com.zeroemotion.madejava5.service.MovieInstance;
import com.zeroemotion.madejava5.service.MovieService;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private Application application;
    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();

    public MovieRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<ArrayList<Movie>> getListMovie(String type, String api_key) {
        MovieService movieService = MovieInstance.getService();
        Call<MovieResponse> movieResponseCall = movieService.getListType(type, api_key);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    listMovie.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.w("Response Failed", t.getMessage());
            }
        });
        return listMovie;
    }
}
