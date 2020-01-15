package com.example.madejava4.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.madejava4.R;
import com.example.madejava4.adapter.FavoriteAdapter;
import com.example.madejava4.model.Movie;
import com.example.madejava4.view.activity.DetailMovieActivity;
import com.example.madejava4.viewmodel.FavoriteViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment {
    @BindView(R.id.rv_movie_fav)
    RecyclerView recyclerView;
    @BindView(R.id.fav_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fav_movie_loading)
    ProgressBar progressBar;
    private FavoriteAdapter adapter;
    FavoriteViewModel viewModel;


    public MovieFavoriteFragment() {
        // Required empty public constructor
    }

    private Observer<List<Movie>> getMovie = new Observer<List<Movie>>() {
        @Override
        public void onChanged(List<Movie> movies) {
            if (movies != null) {
                adapter.setFavoriteMovie((ArrayList<Movie>) movies);
                loading(false);
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_favorite, container, false);
        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        ButterKnife.bind(this, view);
        adapter = new FavoriteAdapter(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
        getMovieList();
        adapter.setOnItemClickCallback(movie -> {
            Intent intent = new Intent(getContext(), DetailMovieActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovieList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void getMovieList() {
        viewModel.getFavoriteType("movie").observe(this, getMovie);
    }

    public void loading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else progressBar.setVisibility(View.GONE);
    }

}
