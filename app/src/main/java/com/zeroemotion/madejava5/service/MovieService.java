package com.zeroemotion.madejava5.service;

import com.zeroemotion.madejava5.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
      @GET("discover/{media_type}")
    Call<MovieResponse> getListType (@Path("media_type") String media_type, @Query("api_key") String api_key);
}
