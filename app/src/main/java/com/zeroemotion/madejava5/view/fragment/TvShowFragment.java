package com.zeroemotion.madejava5.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zeroemotion.madejava5.BuildConfig;
import com.zeroemotion.madejava5.R;
import com.zeroemotion.madejava5.adapter.MovieAdapter;
import com.zeroemotion.madejava5.model.Movie;
import com.zeroemotion.madejava5.view.activity.DetailMovieActivity;
import com.zeroemotion.madejava5.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

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

public class TvShowFragment extends Fragment {
    @BindView(R.id.movie_loading)
    ProgressBar progressBar;
    @BindView(R.id.rv_movie)
    RecyclerView recView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private MovieAdapter adapter;
    private MoviesViewModel viewModel;
    private String media_type;

    Observer<List<Movie>> getTvs = new Observer<List<Movie>>() {
        @Override
        public void onChanged(List<Movie> movies) {
            if (movies != null) {
                adapter.setMovieList((ArrayList<Movie>) movies);
                loading(false);
            }
        }
    };

    public TvShowFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        adapter = new MovieAdapter(getContext());
        recView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recView.setAdapter(adapter);
        getTvList();
        adapter.setOnItemClickCallback(tvShow -> {
            Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
            intent.putExtra("movie", tvShow);
            intent.putExtra("tv", media_type);
            startActivity(intent);
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTvList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        loading(true);
    }

    public void getTvList() {
        viewModel.getListMovie("tv", BuildConfig.API_KEY).observe(this, getTvs);
        showTv();
    }

    public void showTv() {
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
