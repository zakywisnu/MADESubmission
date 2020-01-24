package com.zeroemotion.favoritefilm.model;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

public class Movie {
    private long id;
    @SerializedName(value = "title", alternate = "name")
    private String title;
    private String poster_path;
    private String overview;
    private Double vote_average;

    public Movie(){

    }

    public Movie(Cursor cursor){
        id = cursor.getLong(cursor.getColumnIndex("id"));
        title = cursor.getString(cursor.getColumnIndex("title"));
        poster_path = cursor.getString(cursor.getColumnIndex("poster_path"));
    }

    public Movie(long id, String title, String poster_path, String overview, Double vote_average) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }
}
