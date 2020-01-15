package com.example.madejava4.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.madejava4.BuildConfig;
import com.example.madejava4.R;
import com.example.madejava4.model.Movie;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<Movie> movieList = new ArrayList<>();
    private MovieAdapter.OnItemClickCallback onItemClickCallback;

    public void setMovieList(ArrayList<Movie> data) {
        movieList.clear();
        movieList.addAll(data);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        holder.bind(movieList.get(position));

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie movie);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_image)
        ImageView movieImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Movie movie) {
            String imageUrl = BuildConfig.BASE_IMAGE_URL + movie.getPoster_path();
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_filter_tilt_shift_black_24dp)
                    .error(R.drawable.ic_clear_black_24dp)
                    .into(movieImage);

            itemView.setOnClickListener(
                    view -> onItemClickCallback.onItemClicked(movieList.get(getAdapterPosition())));
        }
    }
}
