package com.zeroemotion.madejava5.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zeroemotion.madejava5.R;
import com.zeroemotion.madejava5.adapter.FavoriteAdapter;
import com.zeroemotion.madejava5.model.Movie;
import com.zeroemotion.madejava5.view.activity.DetailMovieActivity;
import com.zeroemotion.madejava5.viewmodel.FavoriteViewModel;

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
public class TvShowFavoriteFragment extends Fragment {
    @BindView(R.id.rv_tv_fav)
    RecyclerView recyclerView;
    @BindView(R.id.fav_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_fav_loading)
    ProgressBar progressBar;
    private FavoriteAdapter adapter;
    private FavoriteViewModel viewModel;

    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }

    private Observer<List<Movie>> getTvs = new Observer<List<Movie>>() {
        @Override
        public void onChanged(List<Movie> movies) {
            if (movies != null) {
                adapter.setFavoriteMovie(movies);
                loading(false);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);
        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        ButterKnife.bind(this, view);
        adapter = new FavoriteAdapter(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);
        getTvList();
        adapter.setOnItemClickCallback(movie -> {
            Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTvList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void getTvList() {
        viewModel.getFavoriteType("tv").observe(this, getTvs);
    }

    public void loading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else progressBar.setVisibility(View.GONE);
    }

}
