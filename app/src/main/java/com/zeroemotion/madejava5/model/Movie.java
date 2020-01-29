package com.zeroemotion.madejava5.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Movie.TABLE_NAME)
public class Movie implements Parcelable {
    public static final String TABLE_NAME = "movie_table";


    @PrimaryKey(autoGenerate = true)
    private long id;
    @SerializedName(value = "title", alternate = {"name"})
    private String title;
    private String poster_path;
    private double vote_average;
    private String overview;
    @SerializedName(value = "release_date", alternate = {"first_air_date"})
    private String release_date;
    private String backdrop_path;
    @SerializedName("media_type")
    private String media_type;

    public Movie() {

    }

    public static Movie fromContentValues(ContentValues values){
        Movie movie = new Movie();

        if (values.containsKey("id")) {
            movie.setId(values.getAsLong("id"));
        }
        if (values.containsKey("title")){
            movie.setTitle(values.getAsString("title"));
        }
        if (values.containsKey("name")){
            movie.setTitle(values.getAsString("name"));
        }
        if (values.containsKey("poster_path")){
            movie.setPoster_path(values.getAsString("poster_path"));
        }
        if (values.containsKey("overview")){
            movie.setOverview(values.getAsString("overview"));
        }
        if (values.containsKey("vote_average")){
            movie.setVote_average(values.getAsDouble("vote_average"));
        }
        if (values.containsKey("backdrop_path")){
            movie.setBackdrop_path(values.getAsString("backdrop_path"));
        }
        if (values.containsKey("media_type")){
            movie.setMedia_type(values.getAsString("media_type"));
        }
        return movie;
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

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }


    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeDouble(vote_average);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(backdrop_path);
        parcel.writeString(media_type);
    }
    protected Movie(Parcel in) {
        id = in.readLong();
        title = in.readString();
        poster_path = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();
        release_date = in.readString();
        backdrop_path = in.readString();
        media_type = in.readString();
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
}

