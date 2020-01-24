package com.zeroemotion.favoritefilm.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.zeroemotion.favoritefilm.BuildConfig;
import com.zeroemotion.favoritefilm.R;
import com.zeroemotion.favoritefilm.model.Movie;

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
        return new FavoriteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder holder, int position) {
        holder.bind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.favorite_image)
        ImageView favImage;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        void bind(final Movie movie) {
            String imageUrl = BuildConfig.BASE_IMAGE_URL + movie.getPoster_path();
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_filter_tilt_shift_black_24dp)
                    .error(R.drawable.ic_clear_black_24dp)
                    .into(favImage);
        }
    }
}
