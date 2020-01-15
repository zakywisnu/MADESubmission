package com.example.madejava4.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_table")
public class Movie implements Parcelable {

    private int id;
    @SerializedName(value = "title", alternate = {"name"})
    private String title;
    private String poster_path;
    private double vote_average;
    private String overview;
    @SerializedName(value = "release_date", alternate = {"first_air_date"})
    private String release_date;
    private String backdrop_path;
    @SerializedName("media_type")
    private String movie_type;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        poster_path = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();
        release_date = in.readString();
        backdrop_path = in.readString();
        movie_type = in.readString();
    }

    public Movie(){

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeDouble(vote_average);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(backdrop_path);
        dest.writeString(movie_type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getMovie_type() {
        return movie_type;
    }

    public void setMovie_type(String movie_type) {
        this.movie_type = movie_type;
    }
}

