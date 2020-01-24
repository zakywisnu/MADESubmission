package com.zeroemotion.madejava5.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zeroemotion.madejava5.BuildConfig;
import com.zeroemotion.madejava5.R;
import com.zeroemotion.madejava5.adapter.SearchAdapter;
import com.zeroemotion.madejava5.model.Movie;
import com.zeroemotion.madejava5.view.activity.DetailMovieActivity;
import com.zeroemotion.madejava5.view.activity.SearchActivity;
import com.zeroemotion.madejava5.viewmodel.MoviesViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zeroemotion.madejava5.view.activity.SearchActivity.SEARCH_TYPE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    @BindView(R.id.rv_search)
    RecyclerView recyclerView;
    @BindView(R.id.pb_loading)
    ProgressBar progressBar;

    private SearchAdapter adapter;
    private MoviesViewModel viewModel;
    private String media_type;
    private String query;

    private Observer<ArrayList<Movie>> getMovieResults = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                adapter.setSearchList(movies);
                loading(false);
            } else loading(true);
        }
    };


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        loading(true);
        assert getArguments() != null;
        query = getArguments().getString(SearchActivity.SEARCH_QUERY);
        media_type = getArguments().getString("search_key");
        Log.d("searchtype", "Search Type: " + media_type);

        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        viewModel.getSearchMovie(media_type, BuildConfig.API_KEY, query, getResources().getString(R.string.language)).observe(this, getMovieResults);

        adapter = new SearchAdapter(getContext());
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickCallback(movie -> {
            Intent searchIntent = new Intent(getActivity(), DetailMovieActivity.class);
            searchIntent.putExtra("movie", movie);
            searchIntent.putExtra(SEARCH_TYPE, media_type);
            startActivity(searchIntent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }

    public void loading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else progressBar.setVisibility(View.GONE);
    }

}
