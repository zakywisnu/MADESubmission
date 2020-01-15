package com.example.madejava4.view.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madejava4.R;
import com.example.madejava4.adapter.MovieAdapter;
import com.example.madejava4.model.Movie;
import com.example.madejava4.view.activity.DetailMovieActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment {
    @BindView(R.id.rv_movie_fav)
    RecyclerView recyclerView;
    @BindView(R.id.fav_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private MovieAdapter adapter;
    private ArrayList<Movie> movies = new ArrayList<>();


    public MovieFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_favorite, container, false);
        ButterKnife.bind(this, view);
        adapter = new MovieAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickCallback(movie -> {
            Intent intent = new Intent(getContext(), DetailMovieActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }



}
