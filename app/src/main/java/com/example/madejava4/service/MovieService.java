package com.example.madejava4.service;

import com.example.madejava4.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
//    @GET("discover/movie")
//    Call<MovieResponse> getMovieList (@Query("api_key") String api_key);
//    @GET("discover/tv")
//    Call<MovieResponse> getTvShowList (@Query("api_key") String api_key);
    @GET("discover/{type}")
    Call<MovieResponse> getListType (@Path("type") String type, @Query("api_key") String api_key);
}
