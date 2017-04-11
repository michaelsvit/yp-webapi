package com.michaelsvit.yesplanet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Michael on 4/10/2017.
 * Adapter for the RecyclerView with movie cards.
 */

public class MovieCardsAdapter extends RecyclerView.Adapter<MovieCardsAdapter.ViewHolder> {

    private List<Movie> movies;
    private Context context;

    public MovieCardsAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Glide.with(context).load(Cinema.getMoviePosterUrl(movie.getId())).into(holder.poster);
        holder.title.setText(movie.getEnglishTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView poster;
        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            poster = (ImageView) itemView.findViewById(R.id.poster);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
