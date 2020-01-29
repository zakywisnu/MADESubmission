package com.zeroemotion.madejava5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zeroemotion.madejava5.BuildConfig;
import com.zeroemotion.madejava5.R;
import com.zeroemotion.madejava5.model.Movie;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie = new ArrayList<>();
    private SearchAdapter.OnItemClickCallback onItemClickCallback;


    public void setOnItemClickCallback(SearchAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setSearchList(ArrayList<Movie> movies) {
        this.listMovie = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bind(listMovie.get(position));

    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_search)
        ImageView imgSearch;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_rating)
        TextView tvRating;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Movie movie) {
            String imageUrl = BuildConfig.BASE_IMAGE_URL + movie.getPoster_path();
            Glide.with(context)
                    .load(BuildConfig.BASE_IMAGE_URL + movie.getPoster_path())
                    .apply(new RequestOptions())
                    .placeholder(R.drawable.ic_filter_tilt_shift_black_24dp)
                    .error(R.drawable.ic_clear_black_24dp)
                    .into(imgSearch);
            tvTitle.setText(movie.getTitle());
            tvRating.setText(String.valueOf(movie.getVote_average()));
            itemView.setOnClickListener(view -> onItemClickCallback.onItemClicked(listMovie.get(getAdapterPosition())));
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie movie);
    }
}
