package com.example.madejava4.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.madejava4.BuildConfig;
import com.example.madejava4.R;
import com.example.madejava4.adapter.MovieAdapter;
import com.example.madejava4.model.Movie;
import com.example.madejava4.view.activity.DetailMovieActivity;
import com.example.madejava4.viewmodel.MoviesViewModel;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFragment extends Fragment {
    @BindView(R.id.movie_loading)
    ProgressBar progressBar;
    @BindView(R.id.rv_movie)
    RecyclerView recView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MovieAdapter adapter;
    private MoviesViewModel viewModel;

    private String media_type;

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                adapter.setMovieList((ArrayList<Movie>) movies);
                loading(false);
            }
        }
    };

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        ButterKnife.bind(this, view);
        adapter = new MovieAdapter(getContext());
        recView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recView.setAdapter(adapter);
        getMovieList();
        showMovie();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovieList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        adapter.setOnItemClickCallback(movie -> {
            Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
            intent.putExtra("movie", movie);
            intent.putExtra("movies", media_type);
            startActivity(intent);
        });
        loading(true);

    }


    public void getMovieList() {
        viewModel.getListMovie("movie", BuildConfig.API_KEY).observe(this, getMovie);
        showMovie();
    }

    public void showMovie() {
        recView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void loading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else progressBar.setVisibility(View.GONE);
    }
}
