package com.example.madejava4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.madejava4.BuildConfig;
import com.example.madejava4.R;
import com.example.madejava4.model.Movie;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<Movie> movieList = new ArrayList<>();
    private Context context;
    private FavoriteAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie movie);
    }

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setFavoriteMovie(List<Movie> movie) {
        this.movieList = movie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder holder, int position) {
        holder.bind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_show_image)
        ImageView movieImage;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Movie movie) {
            Glide.with(context)
                    .load(BuildConfig.BASE_IMAGE_URL + movie.getPoster_path())
                    .apply(new RequestOptions())
                    .placeholder(R.drawable.ic_filter_tilt_shift_black_24dp)
                    .error(R.drawable.ic_clear_black_24dp)
                    .into(movieImage);
            itemView.setOnClickListener(
                    view -> onItemClickCallback.onItemClicked(movieList.get(getAdapterPosition())));
        }
    }
}
