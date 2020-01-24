package com.zeroemotion.madejava5.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.zeroemotion.madejava5.database.MovieDao;
import com.zeroemotion.madejava5.database.MovieDatabase;
import com.zeroemotion.madejava5.model.Movie;
import com.zeroemotion.madejava5.service.repository.FavoriteRepository;

import java.util.regex.Matcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MovieContentProvider extends ContentProvider{
    public static final String AUTHORITY = "com.zeroemotion.madejava5.provider";
    public static final Uri URI_MOVIE = Uri.parse(
            "content://" + AUTHORITY +"/" + Movie.TABLE_NAME
    );
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        MATCHER.addURI(AUTHORITY, Movie.TABLE_NAME,MOVIE);
        MATCHER.addURI(AUTHORITY, Movie.TABLE_NAME+"/*",MOVIE_ID);
    }
    private FavoriteRepository favoriteRepository;

    @Override
    public boolean onCreate() {
        favoriteRepository = new FavoriteRepository(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final int code = MATCHER.match(uri);
        Cursor cursor;
        switch (code){
            case MOVIE:
                cursor = favoriteRepository.getAllMovieCursor();
                return cursor;
            case MOVIE_ID:
                cursor = favoriteRepository.getMovieCursorId(ContentUris.parseId(uri));
                return cursor;
            default:
                throw new IllegalArgumentException("Invalid URI cannot access with ID : " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id;
        final Context context = getContext();
        if (context == null){
            return null;
        }
        switch (MATCHER.match(uri)){
            case MOVIE:
                id = favoriteRepository.insertMovieFavoriteCursor(Movie.fromContentValues(contentValues));
                context.getContentResolver().notifyChange(URI_MOVIE,null);
                return ContentUris.withAppendedId(uri,id);
            case MOVIE_ID:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
