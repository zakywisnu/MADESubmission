package com.example.madejava4.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.btn_fav)
    ImageButton fav;

    private FavoriteViewModel viewModel;
    String media_type;

    static boolean isFavorite;

    private Movie movie;
    private FavoriteAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        loading(true);
        Intent intent = getIntent();
        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        if (isFavorite) {
            fav.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

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
            loading(false);
        }
        Log.d("mediatype", "Favorite State : " + isFavorite);
        Log.d("mediatype", "MediaType : " + media_type);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markFavorite();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle("");
        collapsingToolbarLayout.setTitleEnabled(true);
    }


    private void markFavorite() {
        if (!isFavorite) {
            viewModel.insert(movie, media_type);
            fav.setImageResource(R.drawable.ic_favorite_black_24dp);
            Toast.makeText(this, getResources().getString(R.string.fav_add), Toast.LENGTH_SHORT).show();
            isFavorite = true;
        } else if (isFavorite) {
            viewModel.delete(movie);
            fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            isFavorite = false;
            Toast.makeText(this, getResources().getString(R.string.fav_removed), Toast.LENGTH_SHORT).show();
        }


    }

    private void loading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else progressBar.setVisibility(View.GONE);
    }

}
