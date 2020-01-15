package com.example.madejava4.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.madejava4.R;
import com.example.madejava4.adapter.FavoriteAdapter;
import com.example.madejava4.model.Movie;
import com.example.madejava4.viewmodel.FavoriteViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.madejava4.BuildConfig.BASE_IMAGE_URL;

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
    @BindView(R.id.fab_fav)
    FloatingActionButton fab;

    private FavoriteViewModel viewModel;


    private int id;
    private Movie movie;
    private FavoriteAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        loading(true);
        Intent intent = getIntent();

        fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_favorite_border_black_24dp));

        initToolbar();
        if (intent.hasExtra("movie")) {
            movie = getIntent().getParcelableExtra("movie");
            id = movie.getId();
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
            loading(false);

        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markFavorite();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle("");
        collapsingToolbarLayout.setTitleEnabled(true);
    }


    private void markFavorite() {
        viewModel.insert(movie);
        Toast.makeText(this,"Added to Favorite",Toast.LENGTH_SHORT).show();
    }

    private void loading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else progressBar.setVisibility(View.GONE);
    }

}
