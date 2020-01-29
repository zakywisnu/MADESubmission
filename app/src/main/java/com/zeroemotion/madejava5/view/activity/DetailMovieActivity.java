package com.zeroemotion.madejava5.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zeroemotion.madejava5.R;
import com.zeroemotion.madejava5.adapter.FavoriteAdapter;
import com.zeroemotion.madejava5.database.MovieDao;
import com.zeroemotion.madejava5.model.Movie;
import com.zeroemotion.madejava5.viewmodel.FavoriteViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.zeroemotion.madejava5.widget.MovieBannerWidget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zeroemotion.madejava5.BuildConfig.BASE_IMAGE_URL;

public class DetailMovieActivity extends AppCompatActivity {
    @BindView(R.id.detail_loading)
    ProgressBar progressBar;
    @BindView(R.id.detail_title)
    TextView detailTitle;
    @BindView(R.id.detail_overview)
    TextView detailOverview;
    @BindView(R.id.detail_release)
    TextView detailRelease;
    @BindView(R.id.detail_rating)
    TextView detailRating;
    @BindView(R.id.detail_poster)
    ImageView detailPoster;
    @BindView(R.id.backdrop_poster)
    ImageView detailBackdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private FavoriteViewModel viewModel;
    String media_type;
    private LiveData<Movie> movieLiveData;

    boolean isFavorite = false;

    private Movie movie;
    private Menu menuItem;
    private FavoriteAdapter adapter;
    private long id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        loading(true);
        Intent intent = getIntent();
        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);


        initToolbar();
        if (intent.hasExtra("movie")) {
            movie = getIntent().getParcelableExtra("movie");
            if (intent.hasExtra("movies")) {
                media_type = "movie";
            } else if (intent.hasExtra("tv")) {
                media_type = "tv";
            }
            String imageUrl = BASE_IMAGE_URL + movie.getPoster_path();
            String backdropUrl = BASE_IMAGE_URL + movie.getBackdrop_path();
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_filter_tilt_shift_black_24dp)
                    .error(R.drawable.ic_clear_black_24dp)
                    .into(detailPoster);
            Glide.with(this)
                    .load(backdropUrl)
                    .placeholder(R.drawable.ic_filter_tilt_shift_black_24dp)
                    .error(R.drawable.ic_clear_black_24dp)
                    .into(detailBackdrop);
            toolbar.setTitle(movie.getTitle());
            detailTitle.setText(movie.getTitle());
            detailOverview.setText(movie.getOverview());
            collapsingToolbarLayout.setTitle(movie.getTitle());
            detailRating.setText(String.valueOf(movie.getVote_average()));
            detailRelease.setText(movie.getRelease_date());
            movie.setMedia_type(media_type);
            id = movie.getId();
            loading(false);
        }
        Log.d("mediatype", "Favorite State : " + isFavorite);
        Log.d("mediatype", "MediaType : " + media_type);
        Log.d("mediatype", "Movie ID : " + id);

        movieLiveData = viewModel.selectMovie(id);
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }


    private void favoriteState(){
        movieLiveData.observe(this, it ->{
            if (it != null){
                menuItem.getItem(0).setIcon(R.drawable.ic_add_favorite_24dp);
                isFavorite = true;
            }
            else {
                menuItem.getItem(0).setIcon(R.drawable.ic_favorite_border);
                isFavorite = false;
            }
            invalidateOptionsMenu();
        });
    }

    private void loading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else progressBar.setVisibility(View.GONE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        menuItem = menu;
        favoriteState();
        Log.d("isfavorite","Favorite State : " + isFavorite);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ic_fav) {
            if (isFavorite) {
                removeFavorite();
            } else {
                addToFavorite();
            }
            invalidateOptionsMenu();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void removeFavorite() {
        viewModel.deleteById(id);
        isFavorite = false;
        menuItem.getItem(0).setIcon(R.drawable.ic_favorite_border);
        Toast.makeText(this, getString(R.string.fav_removed), Toast.LENGTH_SHORT).show();
        MovieBannerWidget.updateWidget(this);
    }

    private void addToFavorite() {
        menuItem.getItem(0).setIcon(R.drawable.ic_add_favorite_24dp);
        isFavorite = true;
        Movie movies = new Movie();
        movies.setMedia_type(media_type);
        movies.setId(movie.getId());
        movies.setTitle(movie.getTitle());
        movies.setPoster_path(movie.getPoster_path());
        movies.setBackdrop_path(movie.getBackdrop_path());
        movies.setOverview(movie.getOverview());
        movies.setRelease_date(movie.getRelease_date());
        movies.setVote_average(movie.getVote_average());
        viewModel.insert(movie);
        Toast.makeText(this, getResources().getString(R.string.fav_add), Toast.LENGTH_SHORT).show();
        MovieBannerWidget.updateWidget(this);
    }
}
