package com.zeroemotion.madejava5.service;

import com.zeroemotion.madejava5.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
      @GET("discover/{media_type}")
    Call<MovieResponse> getListType (@Path("media_type") String media_type, @Query("api_key") String api_key);
      @GET("discover/movie")
    Call<MovieResponse> getReleased (@Query("api_key") String api_key, @Query("primary_release_date.gte") String currentdate, @Query("primary_release_date.lte") String date);
      @GET("search/{media_type}")
    Call<MovieResponse> getSearch (@Path("media_type") String media_type, @Query("api_key") String api_key, @Query("query") String query, @Query("language") String language);
}
