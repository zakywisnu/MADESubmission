package com.zeroemotion.favoritefilm.view.fragment;


import android.database.Cursor;
import android.net.Uri;
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
import android.widget.ProgressBar;

import com.zeroemotion.favoritefilm.R;
import com.zeroemotion.favoritefilm.adapter.FavoriteAdapter;
import com.zeroemotion.favoritefilm.model.Movie;
import com.zeroemotion.favoritefilm.viewmodel.FavoriteViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    @BindView(R.id.tv_loading)
    ProgressBar progressBar;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private static final String PROVIDER_NAME = "com.zeroemotion.madejava5.provider";
    private static final String TABLE_NAME = "movie_table";
    private static final String URL = "content://" + PROVIDER_NAME + "/" + TABLE_NAME;
    private Uri CONTENT_URI = Uri.parse(URL);

    @BindView(R.id.rv_tv_show)
    RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private FavoriteViewModel viewModel;

    private Observer<List<Movie>> getMovie = new Observer<List<Movie>>() {
        @Override
        public void onChanged(List<Movie> movies) {
            if (movies != null){
                adapter.setFavoriteMovie(movies);
                loading(false);
            }
        }
    };
    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    public void getMovieList() {
        viewModel.getMovieList().observe(this,getMovie);
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI,null,null,null);
        if (cursor != null){
            viewModel.setMovieList(cursor);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new FavoriteAdapter(getContext());
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        viewModel.getMovieList().observe(this,getMovie);
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI,null,null,null);
        if (cursor != null){
            viewModel.setMovieList(cursor);
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovieList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void loading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else progressBar.setVisibility(View.GONE);
    }

}
