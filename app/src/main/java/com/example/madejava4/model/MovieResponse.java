package com.example.madejava4.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    private ArrayList<Movie> results;
    @SerializedName("total_results")
    private int totalResults;

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
