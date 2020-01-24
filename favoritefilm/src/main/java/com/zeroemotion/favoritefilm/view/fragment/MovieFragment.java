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
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zeroemotion.favoritefilm.R;
import com.zeroemotion.favoritefilm.adapter.FavoriteAdapter;
import com.zeroemotion.favoritefilm.model.Movie;
import com.zeroemotion.favoritefilm.viewmodel.FavoriteViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private static final String PROVIDER_NAME = "com.zeroemotion.madejava5.provider";
    private static final String TABLE_NAME = "movie_table";
    private static final String URL = "content://" + PROVIDER_NAME + "/" + TABLE_NAME;
    private Uri CONTENT_URI = Uri.parse(URL);

    @BindView(R.id.rv_movie)
    RecyclerView recyclerView;
    private FavoriteAdapter adapter;

    private Observer<List<Movie>> getMovie = new Observer<List<Movie>>() {
        @Override
        public void onChanged(List<Movie> movies) {
            if (movies != null){
                adapter.setFavoriteMovie(movies);
            }
        }
    };


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new FavoriteAdapter(getContext());
        recyclerView.setAdapter(adapter);

        FavoriteViewModel viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        viewModel.getMovieList().observe(this,getMovie);
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI,null,null,null);
        if (cursor != null){
            viewModel.setMovieList(cursor);
        }
        adapter.notifyDataSetChanged();
    }
}
