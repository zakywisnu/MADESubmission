package com.zeroemotion.madejava5.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.zeroemotion.madejava5.BuildConfig.BASE_URL;

public class MovieInstance {
    private static Retrofit retrofit = null;

    public static MovieService getService(){
        if (retrofit == null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MovieService.class);
    }
}
