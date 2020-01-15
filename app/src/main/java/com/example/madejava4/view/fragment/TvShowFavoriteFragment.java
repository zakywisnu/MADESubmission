package com.example.madejava4.view.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madejava4.R;
import com.example.madejava4.adapter.FavoriteAdapter;
import com.example.madejava4.model.Movie;
import com.example.madejava4.view.activity.DetailMovieActivity;
import com.example.madejava4.viewmodel.FavoriteViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment {
    @BindView(R.id.rv_tv_fav)
    RecyclerView recyclerView;
    @BindView(R.id.fav_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private FavoriteAdapter adapter;
    private FavoriteViewModel viewModel;

    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }

    private Observer<ArrayList<Movie>> getTvs = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null){
                adapter.setFavoriteMovie((ArrayList<Movie>) movies);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);
        ButterKnife.bind(this,view);
        adapter = new FavoriteAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        adapter.setOnItemClickCallback(movie -> {
            Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        });
        return view;
    }

//

}
